package com.tarena.lbs.marketing.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.tarena.lbs.attach.api.AttachApi;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.api.BasicApi;
import com.tarena.lbs.common.passport.enums.Roles;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.marketing.web.repository.ActivityRepository;
import com.tarena.lbs.marketing.web.repository.CouponCodeRepository;
import com.tarena.lbs.marketing.web.repository.CouponRepository;
import com.tarena.lbs.marketing.web.repository.UserCouponsRepository;
import com.tarena.lbs.marketing.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.pojo.attach.dto.AttachQrDTO;
import com.tarena.lbs.pojo.attach.param.AttachQRParam;
import com.tarena.lbs.pojo.basic.dto.AdminDTO;
import com.tarena.lbs.pojo.marketing.param.CouponParam;
import com.tarena.lbs.pojo.marketing.param.UserCouponsParam;
import com.tarena.lbs.pojo.marketing.po.ActivityPO;
import com.tarena.lbs.pojo.marketing.po.CouponCodePO;
import com.tarena.lbs.pojo.marketing.po.CouponPO;
import com.tarena.lbs.pojo.marketing.po.UserCouponsPO;
import com.tarena.lbs.pojo.marketing.query.UserCouponCodeQuery;
import com.tarena.lbs.pojo.marketing.query.UserCouponQuery;
import com.tarena.lbs.pojo.marketing.vo.CouponVO;
import com.tarena.lbs.pojo.marketing.vo.UserCouponsVO;
import com.tarena.lbs.pojo.stock.param.CouponStockParam;
import com.tarena.lbs.pojo.stock.po.CouponStockPO;
import com.tarena.lbs.stock.api.StockApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CouponService {
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private CouponCodeRepository couponCodeRepository;
    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private UserCouponsRepository userCouponsRepository;
    @DubboReference
    private BasicApi basicApi;
    @DubboReference
    private StockApi stockApi;
    @Resource
    @Qualifier("myExecutor")
    private ThreadPoolTaskExecutor executor;
    @DubboReference
    private AttachApi attachApi;
    //注入一个可以使用的redis客户端
    @Autowired
    private RedisTemplate redisTemplate;
    public PageResult<CouponVO> pageList() throws BusinessException {
        //1.解析认证对象 拿到UserPrinciple
        UserPrinciple userPrinciple=parseUserPrinciple();
        //2.判断一下权限
        Asserts.isTrue(userPrinciple.getRole()== Roles.USER,
                new BusinessException("-2","用户权限不足"));
        Integer adminId = userPrinciple.getId();
        //3 使用adminId 查询admin详情才能拿到businessId
        Integer businessId=getAdminBusinessId(adminId);
        log.info("当前登录用户:{},查询商家id:{}",userPrinciple.getNickname(),businessId);
        //4.当前业务封装分页数据
        PageResult<CouponVO> voPage=new PageResult<>();
        voPage.setPageSize(10);
        voPage.setPageNo(1);
        voPage.setTotal(100l);
        List<CouponVO> vos=null;
        List<CouponPO> pos= couponRepository.getCouponsByBizId(businessId);
        if (CollectionUtils.isNotEmpty(pos)){
            vos=pos.stream().map(po->{
                CouponVO vo=new CouponVO();
                BeanUtils.copyProperties(po,vo);
                return vo;
            }).collect(Collectors.toList());
        }
        voPage.setObjects(vos);
        return voPage;
    }

    private Integer getAdminBusinessId(Integer adminId) throws BusinessException {
        AdminDTO dto = basicApi.getAdminDetail(adminId);
        Asserts.isTrue(dto==null,
                new BusinessException("-2","管理员信息不存在"));
        return dto.getBusinessId();
    }

    private UserPrinciple parseUserPrinciple() throws BusinessException {
        //1.解析
        UserPrinciple userPrinciple = AuthenticationContextUtils.get();
        Asserts.isTrue(userPrinciple==null,new BusinessException("-2","用户认证解析失败"));
        return userPrinciple;
    }
    @Transactional(rollbackFor = Exception.class)
    public void save(CouponParam couponParam) throws BusinessException {
        //1.拿到认证对象
        UserPrinciple userPrinciple=parseUserPrinciple();
        //2.判断权限 业务权限
        Asserts.isTrue(userPrinciple.getRole()!=Roles.SHOP,
                new BusinessException("-2","用户权限不足"));
        //3.利用商家账号id 读取商家详情获取businessId
        Integer adminId = userPrinciple.getId();
        Integer businessId = getAdminBusinessId(adminId);
        //4.新增 优惠券 封装优惠券po 交给优惠券仓储层新增
        CouponPO poParam=assembleCouponPO(couponParam,businessId);
        couponRepository.save(poParam);
        //5.同步生成当前优惠券的券码数据
        //List<CouponCodePO> codePoParams=assembleCouponCodePos(poParam);
        //couponCodeRepository.saveBatch(codePoParams,100);
        //6.同步库存
        initCouponStock(poParam);
    }

    private void initCouponStock(CouponPO poParam) throws BusinessException {
        //使用优惠券 组织一个dubbo调用的入参
        CouponStockParam param=new CouponStockParam();
        param.setCouponId(poParam.getId());
        param.setBusinessId(poParam.getBusinessId());
        param.setNum(poParam.getMaxUsageLimit());
        Boolean result = stockApi.initCouponStock(param);
        //如果调用结果是false 初始化库存没有成功 库存和优惠券数据一致性问题
        Asserts.isTrue(!result,new BusinessException("-2","库存初始化失败"));
    }

    private List<CouponCodePO> assembleCouponCodePos(CouponPO poParam) {
        //根据poParam 的最大发行量 生成同样个数的list元素数组 1000
        Integer couponId = poParam.getId();
        Integer businessId = poParam.getBusinessId();
        Integer limit = poParam.getMaxUsageLimit();
        //按照limit数量去循环生成 list对象
        List<CouponCodePO> poParams=new ArrayList<>();
        for (int i=0;i<limit;i++){
            CouponCodePO po=new CouponCodePO();
            po.setCouponId(couponId);
            po.setBusinessId(businessId);
            //0未分配 1已分配 2已超时
            po.setStatus(0);
            //时间 和优惠券业务代码同步创建 时间值保持和优惠券一致
            po.setCreateAt(poParam.getCreateAt());
            po.setUpdateAt(poParam.getUpdateAt());
            //为每一个券码创建一个唯一的编码字符串 uuid
            String code = UUID.randomUUID().toString().replaceAll("-", "");
            po.setCouponCode(code);
            poParams.add(po);
        }
        log.info("当前list元素总量:{}",poParams.size());
        return poParams;
    }

    private CouponPO assembleCouponPO(CouponParam couponParam, Integer businessId) throws BusinessException {
        CouponPO po=new CouponPO();
        //1.能考背的先拷贝
        BeanUtils.copyProperties(couponParam,po);
        //2.设置商家id
        po.setBusinessId(businessId);
        //3.设置优惠券状态 0 待激活 1待使用 2过期作废
        po.setStatus(1);
        //4.优惠券管理状态 0 启用 1禁用
        po.setEnableStatus(0);
        //5.创建和更新时间
        Date now=new Date();
        po.setCreateAt(now);
        po.setUpdateAt(now);
        //6.入参携带的startDate和endDate 是字符串"2024-10-10 12:00:00"
        try{
            Date start = DateUtils.parseDate(couponParam.getStartDate(),
                    "yyyy-MM-dd HH:mm:ss");
            Date end = DateUtils.parseDate(couponParam.getEndDate(),
                    "yyyy-MM-dd HH:mm:ss");
            po.setEndDate(end);
            po.setStartDate(start);
        }catch (ParseException e){
            throw new BusinessException("-2","入参字符串日期格式不正确");
        }
        return po;
    }

    public CouponVO detail(Integer id) {
        //仓储层查询 对应的po
        CouponPO po=couponRepository.getCouponById(id);
        return assembleCouponVO(po);
    }

    private CouponVO assembleCouponVO(CouponPO po) {
        CouponVO vo=null;
        if (po!=null){
            vo=new CouponVO();
            BeanUtils.copyProperties(po,vo);
        }
        return vo;
    }
    @Transactional(rollbackFor = Exception.class)
    public void receiveCoupon(UserCouponsParam param) throws BusinessException{
        //1.拿到认证的userId 也是解析 认证检验
        Integer userId=getUserId();
        //2.校验活动和用户 是否合法,如果合法 返回活动对象 以活动对象是否为空 判断是否合法
        CompletableFuture<ActivityPO> activityFuture = CompletableFuture.supplyAsync(() -> {
            return checkUserAndActivity(param.getActivityId(), userId);
        }, executor);

        //3.校验优惠券和用户是否合法,如果合法 返回优惠券对象
        CompletableFuture<CouponPO> couponFuture = CompletableFuture.supplyAsync(() -> {
            return checkUserAndCoupon(param.getCouponId(), userId);
        }, executor);
        CompletableFuture<Void> allFuture = CompletableFuture.allOf(activityFuture, couponFuture);
        ActivityPO activity=null;
        CouponPO coupon=null;
        try{
            activity=activityFuture.get();
            coupon=couponFuture.get();
        }catch (Exception e){
            log.error("并发失败,异常",e);
        }
        Asserts.isTrue(activity==null,new BusinessException("-2","活动校验失败"));
        Asserts.isTrue(coupon==null,new BusinessException("-2","优惠券校验失败"));
        //4.开始领取优惠券
        this.doReceiveCoupon(param,userId,activity,coupon);
    }
    private void doReceiveCoupon(UserCouponsParam param, Integer userId, ActivityPO activity, CouponPO coupon) throws BusinessException {
        //T1 T2 T3 T4 T5
        //T1 1 查询充足 足
        //T2 1 查询充足 足
        //T1 2 写数据
        //T2 2 写数据
        //T1 3 减库存(-1) 剩余0
        //T2 3 也减库存(-1) 剩余-1
        //1.读取当前优惠券库存数量 判断是否充足 每次只需要领取一张
        Boolean tryLock=false;
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        String lockKey = "receive:coupon:" + coupon.getId();
        String code = UUID.randomUUID().toString();
        //第一次进来抢锁 不需要等待 第二次重抢 第三次重抢需要等待1秒钟
        int count=0;
        do {
            //给线程一个重抢上3次 重抢抢不到就别抢了
            Asserts.isTrue(count>3,new BusinessException("-2","优惠券领取失败"));
            if (count>0){
                //当前线程 已经不是第一次抢锁了 等1秒
                try{
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    log.error("线程等待异常",e);
                }
            }
            Boolean enough = stockIsEnough(coupon.getId());
            //如果库存已经没有了 ==0 方法到这就结束了
            Asserts.isTrue(!enough, new BusinessException("-2", "优惠券库存不足"));
            //1.抢锁 有key value 操作redis setnx是string类型
            //SETNX key value EX 5 原子级
            log.info("库存充足开始抢锁,key:{},value:{}",lockKey,code);
            tryLock = opsForValue.setIfAbsent(lockKey, code, 5, TimeUnit.SECONDS);
            count++;
        }while (!tryLock);
        //释放锁一定要在finally
        try {
            log.info("抢锁成功,开始领取优惠券");
            //2.创建一个可领取的优惠券码 返回券码code编码
            String couponCode = createCouponCode(coupon);
            //3.记录一下领取信息
            createUserReceiveCouponInfo(param, userId, activity, coupon, couponCode);
            //4.扣减库存 扣减那个优惠券库存
            reduceStock(coupon.getId());
        }catch (Exception e){
            throw e;
        }finally {
            log.info("释放锁");
            //封装使用lua脚本
            String value = opsForValue.get(lockKey);
            if (value!=null&&value.equals(code)){
                log.info("锁标识正确 可以释放");
                redisTemplate.delete(lockKey);
            }
        }
    }

    private void reduceStock(Integer couponId) throws BusinessException {
        Boolean result = stockApi.reduceStock(couponId, 1);
        //保持强一致性 2个服务调用如果库存扩建失败 需要将上述的写操作都回滚
        Asserts.isTrue(!result,new BusinessException("-2","库存扣减失败"));
    }

    private void createUserReceiveCouponInfo(UserCouponsParam param, Integer userId, ActivityPO activity, CouponPO coupon, String couponCode) {
        //1.创建一个领取记录的po user_coupons
        UserCouponsPO userCouponsPO=new UserCouponsPO();
        //2.封装好属性
        userCouponsPO.setCouponValue(coupon.getDiscountValue());//优惠券面额
        userCouponsPO.setCreateAt(new Date());
        userCouponsPO.setUpdateAt(userCouponsPO.getCreateAt());
        userCouponsPO.setUserId(userId);
        userCouponsPO.setCouponId(coupon.getId());
        userCouponsPO.setCouponCode(couponCode);
        userCouponsPO.setStatus(0);//没有用到 用户领取记录的状态 0表示不空
        userCouponsPO.setShopId(param.getShopId()==null?0:param.getShopId());
        userCouponsPO.setActivityId(activity.getId());
        userCouponsPO.setReceiveChannel(param.getReceiveChannel()==null?0:param.getReceiveChannel());
        userCouponsPO.setCouponType(coupon.getCouponType());
        userCouponsPO.setOrderNo("");//领取记录 没有订单编号
        userCouponsPO.setUsedTime(null);//还没有使用 没有使用时间
        //3.保存到数据库 新增
        userCouponsRepository.save(userCouponsPO);
    }

    private String createCouponCode(CouponPO coupon) {
        //1.组织一个CouponCodePO
        CouponCodePO couponCodePO=new CouponCodePO();
        couponCodePO.setCreateAt(new Date());
        couponCodePO.setUpdateAt(couponCodePO.getCreateAt());
        couponCodePO.setStatus(1);//0未领取 1已领取 2已过期
        couponCodePO.setCouponId(coupon.getId());
        couponCodePO.setBusinessId(coupon.getBusinessId());
        //手动创建code编码
        String code=UUID.randomUUID().toString().replaceAll("-", "");
        couponCodePO.setCouponCode(code);
        //2.写入数据库
        couponCodeRepository.save(couponCodePO);
        //3.返回code
        return code;
    }

    private Boolean stockIsEnough(Integer couponId) {
        //1.使用库存接口类 查询剩余库存数量
        Integer num = stockApi.getCouponStock(couponId);
        //2.判断是否>0
        return num>0;
    }

    private CouponPO checkUserAndCoupon(Integer couponId, Integer userId) {
        //1.先检查一下coupon
        CouponPO po=checkCoupon(couponId);
        //2.检查用户是否已经领取coupon到上限了
        if (po!=null){
            try{
                checkUserLimit(po,userId);
            }catch (Exception e){
                log.error("用户领取上限检查失败");
                return null;
            }
        }
        return po;
    }

    private void checkUserLimit(CouponPO po, Integer userId) throws BusinessException {
        //检查当前userId 领取这个coupon的次数是否达到coupon上限
        Integer couponId = po.getId();
        Integer usageLimit = po.getUsageLimit();//领取上限
        //1.查询该用户 领取该优惠券的次数 user_coupons领取记录表 count
        Long count=userCouponsRepository.countReceiveCoupons(po.getId(),userId);
        //2.判断是否已经等于usageLimit 如果已经等于 不能再领了 如果小于可以在领
        if (usageLimit.equals(count.intValue())){
            log.info("当前用户:{},领取优惠券:{},已达:{}次,不可在领取",userId,po.getId(),count);
            throw new BusinessException("-2","优惠券领取上限");
        }
    }

    private CouponPO checkCoupon(Integer couponId) {
        //混淆误区 优惠券startDate和endDate不是在领取的时候检查的 这是一个核销属性 消费的时候检查
        //1.查询是否可以查询到对象
        CouponPO po = couponRepository.getCouponById(couponId);
        if (po==null){
            return null;
        }
        //2.状态
        Integer status = po.getStatus();
        Integer enableStatus = po.getEnableStatus();
        if (status==null||enableStatus==null){
            log.error("优惠券状态不正常 检查失败,status:{},enableStatus:{}",status,enableStatus);
            return null;
        }
        if (status!=1||enableStatus!=0){
            log.error("优惠券状态不正常 检查失败,status:{},enableStatus:{}",status,enableStatus);
            return null;
        }
        return po;
    }

    private ActivityPO checkUserAndActivity(Integer activityId, Integer userId) {
        //先检查活动
        ActivityPO activity=checkActivity(activityId);
        //检查互动目标人群
        if (activity!=null){
            //说明活动检查是成功的
            try {
                checkTargetCustomer(activity.getTargetCustomer(),userId,activity.getBusinessId());
            } catch (BusinessException e) {
                log.error("用户不属于目标人群");
                return null;
            }
        }
        return activity;
    }
    private void checkTargetCustomer(String targetCustomer, Integer userId,Integer businessId)throws BusinessException {
        //1.使用userId查询 所属的人群
        List<Integer> userGroupIds = basicApi.getUserGroupIds(userId, businessId);
        //2.判断目标人群是否在范围内
        if (CollectionUtils.isNotEmpty(userGroupIds)){
            log.info("用户所属人群不为空,groupIds:{}",userGroupIds);
            Integer targetGroupId=Integer.valueOf(targetCustomer);
            boolean contains = userGroupIds.contains(targetGroupId);
            if (!contains){
                log.info("用户所属人群:{},不包含活动目标人群:{}",userGroupIds,targetGroupId);
                throw new BusinessException("-2","用户不属于活动目标人群");
            }
        }else{
            log.info("用户所属人群为空");
            throw new BusinessException("-2","用户不属于活动目标人群");
        }
    }

    private ActivityPO checkActivity(Integer activityId) {
        //1.活动得存在 非空 读取查询活动数据
        ActivityPO po = activityRepository.getActivityById(activityId);
        if (po==null){
            log.error("活动数据为空,校验活动失败");
            return null;
        }
        //2.活动时间 和当前系统时间 对得上 开始时间< 系统时间 <结束时间
        Date startDate = po.getStartDate();
        Date endDate = po.getEndDate();
        Date now=new Date();
        if (startDate==null||endDate==null){
            log.error("活动开始时间:{},结束时间:{},有一个为空",startDate,endDate);
            return null;
        }
        if (now.before(startDate)||now.after(endDate)){
            log.error("活动时间不正常,开始时间:{},结束时间:{},系统时间:{}",startDate,endDate,now);
            return null;
        }
        //3.活动状态 满足要求 待激活 待推广 启用状态 status==0 enableStatus==0
        Integer status = po.getStatus();
        Integer enableStatus = po.getEnableStatus();
        if (status==null||enableStatus==null){
            log.error("活动状态为空,status:{},enableStatus:{}",status,enableStatus);
            return null;
        }
        if (status!=0||enableStatus!=0){
            log.error("活动状态值错误,status:{},enableStatus:{}",status,enableStatus);
            return null;
        }
        //4.检查一下 后续业务要用到的目标人群是否存在数据
        String targetCustomer = po.getTargetCustomer();
        if (StringUtils.isBlank(targetCustomer)){
            log.error("活动目标人群为空");
            return null;
        }
        return po;
    }

    /**
     * 拿到登录的userId 同时校验认证是否通过
     * @return
     */
    private Integer getUserId() throws BusinessException {
        UserPrinciple userPrinciple = AuthenticationContextUtils.get();
        Asserts.isTrue(userPrinciple==null,new BusinessException("-2","用户认证解析失败"));
        return userPrinciple.getId();
    }

    public PageResult<UserCouponsVO> receiveList(UserCouponQuery query) throws BusinessException {
        //select * from user_coupons where user_id=#{} and status=0|1|2
        PageResult<UserCouponsVO> voPage=new PageResult<>(query);
        voPage.setTotal(100L);
        //查询所有满足条件的记录 user
        Integer userId = getUserId();
        query.setUserId(userId);
        List<UserCouponsPO> pos=userCouponsRepository.getUserCoupons(query);
        List<UserCouponsVO> vos=assembleUserCouponsVOs(pos);
        voPage.setObjects(vos);
        return voPage;
    }

    private List<UserCouponsVO> assembleUserCouponsVOs(List<UserCouponsPO> pos) {
        if (CollectionUtils.isNotEmpty(pos)){
            return pos.stream().map(po->{
                UserCouponsVO vo=new UserCouponsVO();
                BeanUtils.copyProperties(po,vo);
                return vo;
            }).collect(Collectors.toList());
        }else{
            return null;
        }
    }

    public UserCouponsVO receiveDetail(UserCouponCodeQuery query) {
        //2.使用code编码到数据库 查询CouponCode详情 po
        UserCouponsPO po=userCouponsRepository.getUserCouponsByCode(query.getCouponCode());
        UserCouponsVO vo=null;
        //3.转化封装
        if (po!=null){
            vo=new UserCouponsVO();
            BeanUtils.copyProperties(po,vo);
            //二维码的访问查看的url 需要生成
            String qrUrl=generateCouponQr(po);
            vo.setCouponUrl(qrUrl);
        }
        return vo;
    }

    private String generateCouponQr(UserCouponsPO po) {
        //1.读写缓存的客户端
        ValueOperations<String,String> opsForValue = redisTemplate.opsForValue();
        //2.二维码绑定的 数据可以使用code编码
        String codeKey="coupon:code:url:"+po.getCouponCode();
        //3.判断命中
        Boolean exists = redisTemplate.hasKey(codeKey);
        if (exists){
            return opsForValue.get(codeKey);
        }else{
            //调用api拿到二维码的返回对象 解析 url属性
            AttachQRParam attachQRParam=new AttachQRParam();
            attachQRParam.setContent("https://www.baidu.com?code="+po.getCouponCode());
            attachQRParam.setBusinessType(800);
            attachQRParam.setBusinessId(po.getId());
            AttachQrDTO attachQrDTO = attachApi.generateQrCode(attachQRParam);
            String url=attachQrDTO.getUrl();
            //存储缓存
            opsForValue.set(codeKey,url);
            return url;
        }
    }
}
