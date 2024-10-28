package com.tarena.lbs.article.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.tarena.lbs.article.web.repository.ActionESRepository;
import com.tarena.lbs.article.web.repository.ArticleRepository;
import com.tarena.lbs.article.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.common.content.utils.SequenceGenerator;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.marketing.api.MarketingApi;
import com.tarena.lbs.pojo.content.entity.ActionSearchEntity;
import com.tarena.lbs.pojo.content.entity.ArticleSearchEntity;
import com.tarena.lbs.pojo.content.param.ArticleActionParam;
import com.tarena.lbs.pojo.content.param.ArticleContentParam;
import com.tarena.lbs.pojo.content.query.ArticleQuery;
import com.tarena.lbs.pojo.content.vo.ArticleVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleService {
    @Autowired
    private ActionESRepository actionESRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @DubboReference
    private MarketingApi marketingApi;
    //redis客户端 计步器客户端 value序列化 必须使用String不能是json java
    //redisTemplate 可以存储缓存 用object做key和value
    //stringRedisTemplate 如果做缓存 转化对象到字符串
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    public PageResult<ArticleVO> pageList(ArticleQuery articleQuery) throws BusinessException {
        //1.准备返回值
        PageResult<ArticleVO> voPage=new PageResult<>();
        //简化分页处理 避免仓储层查询结果 返回二次封装 pageNo=1 pageSize=10 total=100
        voPage.setPageNo(1);
        voPage.setPageSize(10);
        voPage.setTotal(100L);
        //2.把查询条件 在业务层做一次 拆分 source=1 和source=2 查询条件不同
        //复用同一个仓储层查询
        ArticleQuery repositoryQuery=new ArticleQuery();
        //3.根据source写分支
        Integer source = articleQuery.getSource();
        if (source==1){
            log.info("文章列表查询 source来源是 前台 query:{}",articleQuery);
            //入参的标签
            repositoryQuery.setArticleLabel(articleQuery.getArticleLabel());
            //地理位置
            repositoryQuery.setLongitude(articleQuery.getLongitude());
            repositoryQuery.setLatitude(articleQuery.getLatitude());
            //前端要查询的是 status=1
            repositoryQuery.setArticleStatus(CollectionUtils.list(1));
        }else{
            log.info("文章列表查询 source来源是 后台 query:{}",articleQuery);
            //后台管理1 文章标题 2 文章状态 3 文章分类id 4 文章创建 start和end 5 文章所属的userId;
            //后台数据拷贝
            BeanUtils.copyProperties(articleQuery,repositoryQuery);
            //条件多封装一个 userId 认证对象
            UserPrinciple userPrinciple = AuthenticationContextUtils.get();
            Asserts.isTrue(userPrinciple==null,new BusinessException("-2","用户认证解析失败"));
            repositoryQuery.setUserId(userPrinciple.getId());
        }
        //4.调用仓储层 仓储层里面使用RestHighLevelClient 返回满足条件的所有文章
        //没有连接数据库 连接的是 es 返回的结果 定义为entities
        List<ArticleSearchEntity> entities=articleRepository.searchArticles(repositoryQuery);
        //5.使用entities封装vos
        List<ArticleVO> vos=null;
        if (CollectionUtils.isNotEmpty(entities)){
            vos=entities.stream().map(entity->{
                ArticleVO vo=new ArticleVO();
                BeanUtils.copyProperties(entity,vo);
                return vo;
            }).collect(Collectors.toList());
        }
        voPage.setObjects(vos);
        return voPage;
    }

    public void addArticle(ArticleContentParam param) throws BusinessException {
        //1.根据param 封装 ArticleSearchEntity(对应es的持久化对象)
        ArticleSearchEntity entity=assembleArticleEntity(param);
        //2.调用仓储层 写入数据 写入到ES中
        articleRepository.save(entity);
        //3.使用文章的id 绑定文章上传的封面和内容图片 文章封面是500 内容600
        bindPictures(entity.getId(),param);
    }

    private ArticleSearchEntity assembleArticleEntity(ArticleContentParam param) throws BusinessException {
        //能拷贝的全拷贝 然后在考虑其他没封装的属性
        ArticleSearchEntity entity=new ArticleSearchEntity();
        BeanUtils.copyProperties(param,entity);
        //1.userId 当前文章的创建人 需要记录
        UserPrinciple userPrinciple = AuthenticationContextUtils.get();
        Asserts.isTrue(userPrinciple==null,new BusinessException("-2","用户认证解析失败"));
        entity.setUserId(userPrinciple.getId());
        //2.文章id是Integer(可以String 可以Long 之所以是integer是为了绑定图片使用)
        entity.setId(SequenceGenerator.sequence());
        //3.s时间
        Date now=new Date();
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        //4.location latitude longitude 合并成一个geo_point字符串location
        entity.setLocation(param.getLatitude()+","+param.getLongitude());
        //5.状态 前端提交 status=1上线 保存 status=0 如果status==null 默认1
        if (param.getArticleStatus()==null){
            entity.setArticleStatus(1);
        }
        //6.文章埋点数据 默认都是0 但是后续将这个数据写入到 redis中
        entity.setAccessCount(0);
        entity.setLikeCount(0);
        entity.setFavoriteCount(0);
        log.info("创建文章入参:{},封装持久化对象:{}",param,entity);
        return entity;
    }

    private void bindPictures(Integer id, ArticleContentParam param) {
        //UNDO 图片的常规业务绑定
    }

    public Set<String> articleLabels(ArticleQuery articleQuery) throws BusinessException {
        //没有复杂业务逻辑,直接调用仓储层
        return articleRepository.getArticleLabels(articleQuery);
    }

    public ArticleVO detail(String id) throws BusinessException {
        //1.拿到登录用户的id
        Integer userId=getUserId();
        //2.调用仓储层拿到es的实体对象
        ArticleSearchEntity articleEntity=articleRepository.getArticleById(id);
        Integer activityId = articleEntity.getActivityId();
        //3.判断 当前文章 有没有携带活动
        if (activityId!=null){
            log.info("用户:{},文章:{},携带了活动:{}",userId,id,activityId);
            //4.远程调用 活动 根据返回结果 决定是否将activityId返回给客户端
            Boolean result = marketingApi.activityVisible(userId, activityId);
            if (!result){
                log.info("用户:{},不符合活动目标人群",userId);
                articleEntity.setActivityId(null);
            }
        }else{
            log.info("用户:{},文章:{},没有携带活动",userId,id);
        }
        ArticleVO vo=assembleArticleVO(articleEntity);
        return vo;
    }

    private ArticleVO assembleArticleVO(ArticleSearchEntity articleEntity) {
        ArticleVO vo=null;
        if (articleEntity!=null){
            vo=new ArticleVO();
            BeanUtils.copyProperties(articleEntity,vo);
        }
        return vo;
    }

    private Integer getUserId() throws BusinessException {
        //1.解析
        //2.断言
        UserPrinciple userPrinciple = AuthenticationContextUtils.get();
        Asserts.isTrue(userPrinciple==null,new BusinessException("-2","用户认证解析失败"));
        //3.返回
        return userPrinciple.getId();
    }

    public void articleBehavior(ArticleActionParam param) throws BusinessException {
        //1.封装ActionSearchEntity 记录到lbs_action索引
        ActionSearchEntity actionEntity=new ActionSearchEntity();
        //1.1 缺少发起行为的用户id
        Integer userId = getUserId();
        //1.2 缺少文章冗余信息 比如 文章所属作者 文章分类 文章标题 文章类型文章标签
        ArticleSearchEntity article = articleRepository.getArticleById(param.getId() + "");
        //装配一下 行为发起人id
        actionEntity.setBehaviorUserId(userId);
        //文章冗余字段属性
        actionEntity.setArticleId(param.getId());
        actionEntity.setArticleUserId(article.getUserId());
        actionEntity.setArticleTitle(article.getArticleTitle());
        actionEntity.setArticleType(article.getArticleCategoryId()+"");
        actionEntity.setArticleLabel(article.getArticleLabel());
        actionEntity.setCreateTime(new Date());
        actionEntity.setComment(param.getComment());//只有在befavior的值是3的时候才有评论
        actionEntity.setBehavior(param.getBehavior());
        log.info("写入到用户行为记录的数据:{}",actionEntity);
        actionESRepository.save(actionEntity);
        //2.这次操作 记录一次 行为 并且修改一次文章属性 如果点赞 修改likeCount++ 如果收藏 修改favoriteCount++
        //根据本次行为的id 判断更新哪个属性 1点赞likeCount 2收藏favoriteCount 3评论 accessCount 4访问 5转发...
        Integer behavior = param.getBehavior();//行为 分类
        //使用redis存储这篇文章相关连的数据计步器
        String articleNumKey="article:count:"+article.getId();
        //hash的属性值 使用behavior的数字 1点赞 2收藏 3评论 4转发 5访问...
        BoundHashOperations<String, String, String> opsForHash =
                stringRedisTemplate.boundHashOps(articleNumKey);
        //调用hash的命令increby
        Long increment = opsForHash.increment(param.getBehavior() + "", 1);
        log.info("当前文章:{},用户行为:{},记录后的数字:{}",article.getId(),param.getBehavior(),increment);
        /*if (behavior!=null&&behavior.equals(1)){
            //点赞操作
            Integer likeCount = article.getLikeCount();
            article.setLikeCount(likeCount+1);
            updateArticleCount(article);
        }else if (behavior!=null&&behavior.equals(2)){
            //收藏
            Integer favoriteCount = article.getFavoriteCount();
            article.setFavoriteCount(favoriteCount+1);
            updateArticleCount(article);
        }else if (behavior!=null&&behavior.equals(3)){
            //评论
            Integer accessCount = article.getAccessCount();
            article.setAccessCount(accessCount+1);
            updateArticleCount(article);
        }*/
        //2.1读取文章数据 拿到当前likeCount或者favoriteCount
        //2.2 给数据+1更新 5-> 7->8 安全隐患 线程肯定不安全.
    }
}
