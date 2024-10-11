package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.github.pagehelper.PageInfo;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.repository.BusinessRepository;
import com.tarena.lbs.pojo.basic.param.BusinessParam;
import com.tarena.lbs.pojo.basic.po.BusinessPO;
import com.tarena.lbs.pojo.basic.query.BusinessQuery;
import com.tarena.lbs.pojo.basic.vo.BusinessVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessService {
    @Autowired
    private BusinessRepository businessRepository;
    public PageResult<BusinessVO> pageList(BusinessQuery query) {
        //1.封装分页对象返回
        PageResult<BusinessVO> voPages= new PageResult<>();
        //2.调用仓储层 使用pageHelper查询 获取返回结果
        PageInfo<BusinessPO> pageInfo=businessRepository.getPages(query);
        //3.pageNum pageSize total可以直接封装
        voPages.setTotal(pageInfo.getTotal());
        voPages.setPageNo(pageInfo.getPageNum());
        voPages.setPageSize(pageInfo.getPageSize());
        //4.vos需要判断非空pos 转化
        List<BusinessVO> vos=null;
        if (CollectionUtils.isNotEmpty(pageInfo.getList())){
            vos=pageInfo.getList().stream().map(po->{
                BusinessVO vo=new BusinessVO();
                BeanUtils.copyProperties(po,vo);
                return vo;
            }).collect(Collectors.toList());
        }
        voPages.setObjects(vos);
        return voPages;
        /* voPages.setPageNo(query.getPageNo());
        voPages.setPageSize(query.getPageSize());
        //2.voPages需要封装5个属性才算完毕
        //2.1 查询总条数 以当前条件 查所有 条件先忽略 select count(*) from lbs_business
        Long total=businessRepository.count(query);
        //2.2 查询分页数据 返回List<PO> select * from lbs_business limit from,size
        List<BusinessPO> pos= businessRepository.getBusinessByPage(query);
        //3. 将po的分页列表转化成vo
        List<BusinessVO> vos=null;
        if (CollectionUtils.isNotEmpty(pos)){
            // 将pos转化成vo 使用 list的stream()的api
            vos=pos.stream().map(po->{
                BusinessVO vo=new BusinessVO();
                BeanUtils.copyProperties(po,vo);
                return vo;
            }).collect(Collectors.toList());
        }
        //剩余属性封装完毕
        voPages.setTotal(total);
        voPages.setObjects(vos);*/

    }

    public void save(BusinessParam param) {
        //1.检查当前登录用户的角色是否符合我当前业务的角色要求  ADMIN
        //2.验证检查幂等 是否存在相同名称的商家数据
        //3.幂等验证正常 可以新增 封装 数据对象PO 执行save新增
        //TODO 4.新增之后的商家 有了id 定义 type 100营业执照 200logo调用图片绑定
    }
}
