package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.github.pagehelper.PageInfo;
import com.tarena.lbs.base.common.utils.Asserts;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.repository.AdminRepository;
import com.tarena.lbs.basic.web.repository.BusinessRepository;
import com.tarena.lbs.basic.web.utils.AuthenticationContextUtils;
import com.tarena.lbs.common.passport.enums.Roles;
import com.tarena.lbs.common.passport.principle.UserPrinciple;
import com.tarena.lbs.pojo.basic.dto.AdminDTO;
import com.tarena.lbs.pojo.basic.param.AdminParam;
import com.tarena.lbs.pojo.basic.po.AdminPO;
import com.tarena.lbs.pojo.basic.po.BusinessPO;
import com.tarena.lbs.pojo.basic.query.AdminQuery;
import com.tarena.lbs.pojo.basic.vo.AdminVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private BusinessRepository businessRepository;
    public AdminDTO detail(Integer id){
        AdminPO adminPO = adminRepository.getAdminById(id);
        AdminDTO dto=null;
        if (adminPO!=null){
            dto=new AdminDTO();
            BeanUtils.copyProperties(adminPO,dto);
        }
        return dto;

    }
    public AdminVO detail() throws BusinessException {
        //1.作为过滤器的下游代码 可以直接获取threadLocal里的认证对象
        UserPrinciple userPrinciple = AuthenticationContextUtils.get();
        //断言异常
        Asserts.isTrue(userPrinciple==null,new BusinessException("-2","认证解析错误"));
        Integer id=userPrinciple.getId();
        //2.读取数据库po
        AdminPO po=adminRepository.getAdminById(id);
        //3.转化成vo VO就是PO的复制改名 属性几乎一模一样的
        AdminVO vo=null;
        if (po!=null){
            vo=new AdminVO();
            //密码最好设置成空
            BeanUtils.copyProperties(po,vo);
        }
        return vo;
    }

    public PageResult<AdminVO> pageList(AdminQuery query) {
        PageResult<AdminVO> voPage=new PageResult<>();
        //total objects pageNo pageSize totalPage
        PageInfo<AdminPO> pageInfo= adminRepository.gePages(query);
        //pageNo pageSize total
        voPage.setPageNo(pageInfo.getPageNum());
        voPage.setPageSize(pageInfo.getPageSize());
        voPage.setTotal(pageInfo.getTotal());
        List<AdminVO> vos=null;
        List<AdminPO> pos = pageInfo.getList();
        if (CollectionUtils.isNotEmpty(pos)){
            vos=pos.stream().map(po->{
                AdminVO vo=new AdminVO();
                BeanUtils.copyProperties(po,vo);
                return vo;
            }).collect(Collectors.toList());
        }
        voPage.setObjects(vos);
        return voPage;
    }

    public void save(AdminParam param) throws BusinessException {
        //1.验证角色 是否满足 匹配ADMIN
        checkRole(Roles.ADMIN);
        //2.验证商家 businessId未必是合法数据
        checkBusinessExist(param);
        //3 我们不允许电话 重复
        checkPhoneReuse(param);
        //4.分装数据新增写入
        doSave(param);
    }

    private void doSave(AdminParam param) {
        //前端表单 一般不会和数据库表格 完全对应
        AdminPO poParam=new AdminPO();
        BeanUtils.copyProperties(param,poParam);
        //param转化po的时候 我们要关注 accountStatus 0启用 1禁用 createTime businessId(保证 平台用户 值0)
        poParam.setAccountStatus(0);
        poParam.setCreateTime(new Date());
        poParam.setBusinessId(param.getAccountType()==0?0:param.getBusinessId());
        adminRepository.save(poParam);
    }

    private void checkPhoneReuse(AdminParam param) throws BusinessException {
        //1 使用电话号码 读取查询count
        Long count=adminRepository.countByPhone(param.getAccountPhone());
        //2 count>0 抛异常
        Asserts.isTrue(count>0,new BusinessException("-2","电话号码重复"));
    }

    private void checkBusinessExist(AdminParam param) throws BusinessException {
        //新增的账号类型accountType=0 没有商家绑定的 肯定查不到
        //只有账号类型accountType=1 才读取 验证
        if (param.getAccountType()==1){
            //读取商家数据 用id 判断非空
            BusinessPO bizPO=businessRepository.getBusinessById(param.getBusinessId());
            Asserts.isTrue(bizPO==null,new BusinessException("-2","商家不存在"));
        }
    }

    private void checkRole(Roles role) throws BusinessException {
        //和当前登录的用户做对比
        //1.先拿到登录认证对象
        UserPrinciple userPrinciple = AuthenticationContextUtils.get();
        //断言==null 抛异常
        Asserts.isTrue(userPrinciple==null,new BusinessException("-2","用户认证解析失败"));
        //2.拿到认证登录角色 和 业务需要的角色对比
        Roles loginRole = userPrinciple.getRole();
        //断言不相等 抛异常
        Asserts.isTrue(loginRole!=role,new BusinessException("-2","用户角色权限不足"));
    }
}



















