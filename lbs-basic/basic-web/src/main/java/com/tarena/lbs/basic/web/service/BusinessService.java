package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.basic.web.repository.BusinessRepository;
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
        // total总条数 select count()
        // objects分页核心业务数据list select * limit from,size
        // pageNo当前页 就是查询入参 pageNo
        // pageSize当前页条数 查询入参 pageSize
        // totalPage 总页数 需要计算 =total%pageSize==0?total/pageSize:total/pageSize+1
        voPages.setPageNo(query.getPageNo());
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
        voPages.setObjects(vos);
        return voPages;
    }
}
