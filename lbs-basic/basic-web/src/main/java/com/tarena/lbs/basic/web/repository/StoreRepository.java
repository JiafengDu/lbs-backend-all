package com.tarena.lbs.basic.web.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tarena.lbs.basic.web.mapper.StoreMapper;
import com.tarena.lbs.pojo.basic.entity.StoreSearchEntity;
import com.tarena.lbs.pojo.basic.po.StorePO;
import com.tarena.lbs.pojo.basic.query.StoreQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StoreRepository {
    //读写数据 对应mysql
    @Autowired
    private StoreMapper storeMapper;
    //读写数据 对应es
    @Autowired
    private StoreESRepository storeESRepository;

    public PageInfo<StorePO> getPages(StoreQuery query) {
        //如果query 查询条件属性非空 只有一个businessId
        QueryWrapper<StorePO> queryWrapper = new QueryWrapper<>();
        if (query.getBusinessId()!=null){
            queryWrapper.eq("business_id",query.getBusinessId());
        }
        //开启分页
        PageHelper.startPage(query.getPageNo(), query.getPageSize());
        List<StorePO> pos = storeMapper.selectList(queryWrapper);
        return new PageInfo<>(pos);
    }
    @Transactional(rollbackFor = Exception.class)
    public void save(StorePO poParam) {
        //1.使用mapper写入 数据库表
        storeMapper.insert(poParam);
        //2.将地理位置绑定storeId写入到es
        StoreSearchEntity entity=new StoreSearchEntity();
        BeanUtils.copyProperties(poParam,entity);
        //手动处理location拼接 latitude,longitude
        entity.setLocation(poParam.getStoreLatitude()+","+poParam.getStoreLongitude());
        storeESRepository.save(entity);
    }
}
