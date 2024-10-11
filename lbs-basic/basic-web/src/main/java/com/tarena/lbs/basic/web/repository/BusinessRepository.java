package com.tarena.lbs.basic.web.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tarena.lbs.basic.web.mapper.BusinessMapper;
import com.tarena.lbs.pojo.basic.po.BusinessPO;
import com.tarena.lbs.pojo.basic.query.BusinessQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BusinessRepository {
    @Autowired
    private BusinessMapper businessMapper;

    public Long count(BusinessQuery query) {
        //queryWrapper是null值 表示where条件不拼接任何逻辑
        return businessMapper.selectCount(null);
    }


    public List<BusinessPO> getBusinessByPage(BusinessQuery query) {
        //query pageNo pageSize
        int from=(query.getPageNo()-1)*query.getPageSize();
        int size=query.getPageSize();
        //select * from lbs_business limit #{from},#{size}
        return businessMapper.selectPages(from,size);
    }
    //Test 利用pageHelper查询 business分页数据 没有业务查询条件 查所有
    public PageInfo<BusinessPO> pageList(){
        boolean run=false;
        //1.在业务list查询之前 开启插件分页
        PageHelper.startPage(1,10);
        if (run){
            //2.使用持久层 查询所有的列表 不关心分页 select * from lbs_business
            List<BusinessPO> pos = businessMapper.selectList(null);
            //3.使用业务pos 封装pageInfo对象 这个对象 由于开启了分页 将包含total总数 和分页结果
            PageInfo<BusinessPO> pageInfo=new PageInfo<>(pos);
            return pageInfo;
        }else{
            //手动清空
            PageHelper.clearPage();
        }
        return null;
    }


    public PageInfo<BusinessPO> getPages(BusinessQuery query) {
        //1.先利用query中非空属性 凭借查询sql的where条件
        QueryWrapper<BusinessPO> queryWrapper=assembleBusinessQuery(query);
        //2.开启分页插件
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        //3.查询业务列表 被mybatis拦截 查询分页
        List<BusinessPO> pos = businessMapper.selectList(queryWrapper);
        return new PageInfo<>(pos);
    }

    private QueryWrapper<BusinessPO> assembleBusinessQuery(BusinessQuery query) {
        QueryWrapper<BusinessPO> queryWrapper=new QueryWrapper<>();
        //1.businessName 商家名称 模糊查询 非空 where business_name like(%#{}%)
        if (StringUtils.isNotBlank(query.getBusinessName())){
            queryWrapper.like("business_name",query.getBusinessName());
        }
        //2.businessHeadPhone 商家负责人电话 相等查询 非空 where business_head_phone=#{}
        if (StringUtils.isNotBlank(query.getBusinessHeadPhone())){
            queryWrapper.eq("business_head_phone",query.getBusinessHeadPhone());
        }
        //3.businessStatus 商家状态 0 禁用 1正常 2待审核 3未审核 4 5 相等查询 非空 business_status=#{}
        if (query.getBusinessStatus()!=null){
            queryWrapper.eq("business_status",query.getBusinessStatus());
        }
        //4.startTime 商家入住开始时间 非空 entry_time>#{}
        if (query.getStartingTime()!=null){
            //gt方法表示> greater than gte greater than and equal
            //lt方法表示< less than lte less than and equal
            queryWrapper.gt("entry_time",query.getStartingTime());
        }
        //5.endTime 商家入住结束时间 非空 entry_time<#{}
        if (query.getEndTime()!=null){
            queryWrapper.lt("entry_time",query.getEndTime());
        }
        //6.businessId 商家id 相等查询 非空 id=#{}
        if (query.getBusinessId()!=null){
            queryWrapper.eq("id",query.getBusinessId());
        }
        return queryWrapper;
    }

    public Long countBusinessName(String businessName) {
        //select count(0) from lbs_business where business_name=#{}
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("business_name",businessName);
        return businessMapper.selectCount(queryWrapper);
    }

    public void save(BusinessPO po) {
        businessMapper.insert(po);
    }
}
