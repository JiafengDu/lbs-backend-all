package com.tarena.lbs.article.web.repository;

import com.alibaba.fastjson2.JSON;
import com.tarena.lbs.pojo.content.entity.ActionSearchEntity;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ActionRepository {
    @Autowired
    private RestHighLevelClient client;

    public List<ActionSearchEntity> searchActionArticles(Integer userId, Integer behavior) {
        //相当于这个仓储层 需要搜索查询一批数据 某个用户 点赞的 收藏 访问的文章列表
        //数据列表肯定是重复,需要去重
        //1.先创建查询
        SearchRequest searchRequest = new SearchRequest("lbs_action");
        //2.封装查询条件 userId=behaviorUserId and behavior=behavior bool查询
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //2.1定义去重 使用文章id
        sourceBuilder.collapse(new CollapseBuilder("articleId"));
        //2.2构造布尔条件
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        //2.3 must 子条件 userId使用Term查询 behavior使用Term查询
        query.must(QueryBuilders.termQuery("behaviorUserId",userId));
        query.must(QueryBuilders.termQuery("behavior",behavior));
        sourceBuilder.query(query);
        searchRequest.source(sourceBuilder);
        //3.发起查询
        SearchResponse response = null;
        try {
            response = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //4.解析结果
        SearchHit[] hits = response.getHits().getHits();
        List<ActionSearchEntity> entities=null;
        if (hits!=null&&hits.length>0){
            entities= Arrays.stream(hits).map(hit->{
                return JSON.parseObject(hit.getSourceAsString(),ActionSearchEntity.class);
            }).collect(Collectors.toList());
        }
        return entities;
    }
}
