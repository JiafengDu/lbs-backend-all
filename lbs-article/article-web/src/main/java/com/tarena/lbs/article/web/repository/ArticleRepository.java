package com.tarena.lbs.article.web.repository;

import com.alibaba.fastjson2.JSON;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.tarena.lbs.article.web.service.ArticleService;
import com.tarena.lbs.pojo.content.entity.ArticleSearchEntity;
import com.tarena.lbs.pojo.content.query.ArticleQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class ArticleRepository {
    //使用连接es的客户端
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    //使用client搜索 入参条件 可能是多个非空属性
    //每个属性都可以单独封装一个查询条件 所有非空属性 分装一个复合条件bool
    public List<ArticleSearchEntity> searchArticles(ArticleQuery repositoryQuery) {
        //1.搜索的请求request 索引名字 定了lbs_article
        SearchRequest request=new SearchRequest("lbs_article");
        //2.封装请求参数 构造器
        SearchSourceBuilder builder=new SearchSourceBuilder();
        //利用入参的query 非空属性拼接所有的子条件 组合bool交给请求对象
        generateSource(builder,repositoryQuery);
        //请求配置一下source参数 发送请求命令
        request.source(builder);
        //3.发送命令
        SearchResponse response=null;
        try {
            response=restHighLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("查询文章列表 异常,入参query:{},查询条件:{}",repositoryQuery,builder,e);
        }
        //4.解析返回值 转化entitis数组
        List<ArticleSearchEntity> entities=null;
        if (response!=null){
            //连接es没问题 业务层是模拟的分页 无需关心total等数据 只需要解析数组列表
            SearchHit[] hits = response.getHits().getHits();
            if (hits!=null&&hits.length>0){
                entities= Arrays.stream(hits).map(hit->{
                    //hit就是每一个文档对象封装 包括元数据 index version id type 包括业务source json
                    return JSON.parseObject(hit.getSourceAsString(),ArticleSearchEntity.class);
                }).collect(Collectors.toList());
            }
        }
        return entities;
    }

    private void generateSource(SearchSourceBuilder builder, ArticleQuery repositoryQuery) {
        //select * from user where userName=#{} if userName!=null
        //构造一个bool 所有的子条件都是must 子条件类型 和参数 来自于query非空属性
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        //1.文章标题 全文检索 matchQuery
        if (StringUtils.isNotBlank(repositoryQuery.getArticleTitle())){
            query.must(QueryBuilders.matchQuery("articleTitle",repositoryQuery.getArticleTitle()));
        }
        //2.如果分类id 非空 termquery
        if (repositoryQuery.getArticleCategoryId()!=null){
            query.must(QueryBuilders.termQuery("articleCategoryId",repositoryQuery.getArticleCategoryId()));
        }
        //3.文章标签非空 termquery
        if (StringUtils.isNotBlank(repositoryQuery.getArticleLabel())){
            query.must(QueryBuilders.termQuery("articleLabel",repositoryQuery.getArticleLabel()));
        }
        //4.文章状态 入参是list 实际只有一个元素 0 | 1 termQuery
        if (CollectionUtils.isNotEmpty(repositoryQuery.getArticleStatus())){
            query.must(QueryBuilders.termQuery("articleStatus",repositoryQuery.getArticleStatus().get(0)));
        }
        //5.source表示来源 后台新增的source==2 前台新增source=1 但是如果是查询前台应该可以查看所有 后台只看后台
        //只在source=2的时候做term查询
        if (repositoryQuery.getSource()==2){
            query.must(QueryBuilders.termQuery("source",repositoryQuery.getSource()));
        }
        //6.活动id 非空 termQuery
        if (repositoryQuery.getActivityId()!=null){
            query.must(QueryBuilders.termQuery("activityId",repositoryQuery.getActivityId()));
        }
        //7.地理位置 lat long 封装到一个location使用 GeoDistanceQuery 100公里
        if (StringUtils.isNotBlank(repositoryQuery.getLatitude())&&StringUtils.isNotBlank(repositoryQuery.getLongitude())){
            GeoDistanceQueryBuilder geoQuery = QueryBuilders.geoDistanceQuery("location");
            geoQuery.distance(100D, DistanceUnit.KILOMETERS);
            geoQuery.point(Double.valueOf(repositoryQuery.getLatitude()),Double.valueOf(repositoryQuery.getLongitude()));
            query.must(geoQuery);
        }
        //8.起始时间不空 rangeQuery gte
        if (repositoryQuery.getUpdateStartTime()!=null){
            query.must(QueryBuilders.rangeQuery("updateTime").gte(repositoryQuery.getUpdateStartTime()));
        }
        //9.结束时间不空 rangeQuery lte
        if (repositoryQuery.getUpdateEndTime()!=null){
            query.must(QueryBuilders.rangeQuery("updateTime").lte(repositoryQuery.getUpdateEndTime()));
        }
        //10 userId 所属文章作者 termQuery
        if (repositoryQuery.getUserId()!=null){
            query.must(QueryBuilders.termQuery("userId",repositoryQuery.getUserId()));
        }
        //将query交给builder
        builder.query(query);
        log.info("构造的查询条件:{}",query);
    }
}
