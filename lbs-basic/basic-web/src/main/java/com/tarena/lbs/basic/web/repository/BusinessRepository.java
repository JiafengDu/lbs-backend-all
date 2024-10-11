package com.tarena.lbs.basic.web.repository;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tarena.lbs.basic.web.mapper.BusinessMapper;
import com.tarena.lbs.pojo.basic.po.BusinessPO;
import com.tarena.lbs.pojo.basic.query.BusinessQuery;
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







}
