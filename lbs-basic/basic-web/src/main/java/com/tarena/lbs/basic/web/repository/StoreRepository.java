package com.tarena.lbs.basic.web.repository;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tarena.lbs.basic.web.mapper.StoreMapper;
import com.tarena.lbs.pojo.basic.entity.StoreSearchEntity;
import com.tarena.lbs.pojo.basic.po.StorePO;
import com.tarena.lbs.pojo.basic.query.StoreQuery;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.geo.GeoPoint;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Slf4j
public class StoreRepository {
    //读写数据 对应mysql
    @Autowired
    private StoreMapper storeMapper;
    //读写数据 对应es
    @Autowired
    private StoreESRepository storeESRepository;
    @Autowired
    private RestHighLevelClient client;

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

    public List<StorePO> getAreaStores(List<Long> cityIdList, Integer businessId) {
        //调用mybatis-plus的queryWrapper封装 查询条件
        QueryWrapper<StorePO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_id", businessId)
                .and(
                    qw->
                    qw.in("province_id", cityIdList).
                            or().
                            in("city_id", cityIdList).
                            or().
                            in("area_id",cityIdList));
        return storeMapper.selectList(queryWrapper);
        /*queryWrapper.in("city_id", cityIdList).or()
                .in("province_id", cityIdList).or()
                .in("area_id", cityIdList);*/

    }

    public List<StorePO> getStoresByBusinessId(Integer businessId) {
        QueryWrapper<StorePO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("business_id", businessId);
        return storeMapper.selectList(queryWrapper);
    }

    public List<StoreSearchEntity> getNearStores(String latitude, String longitude, double radius) {
        //1.搜索功能 创建查询条件
        SearchRequest searchRequest=new SearchRequest("lbs_store");
        //2.给查询条件组织查询请求参数 query类型地GEODISTANCE
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        //构造一个地理距离的查询条件 中心点 和radius范围
        GeoPoint center=new GeoPoint(Double.valueOf(latitude),Double.valueOf(longitude));
        GeoDistanceQueryBuilder query = QueryBuilders.geoDistanceQuery("location");
        query.point(center).distance(radius, DistanceUnit.KILOMETERS);
        sourceBuilder.query(query);
        searchRequest.source(sourceBuilder);
        //3.发起请求 解析响应 封装数据
        SearchResponse response=null;
        try{
            response=client.search(searchRequest, RequestOptions.DEFAULT);
        }catch (Exception e){
            log.error("查询附近店铺 出现异常",e);
        }
        if (response!=null){
            //拿到文档 转化storeSearchEntity
            SearchHit[] hits = response.getHits().getHits();
            if (hits!=null&&hits.length>0){
                return Arrays.stream(hits).map(hit->{
                    String json = hit.getSourceAsString();
                    log.info("命中文档 店铺:{}",json);
                    return JSON.parseObject(json, StoreSearchEntity.class);
                }).collect(Collectors.toList());
            }
        }
        return null;
    }
}
