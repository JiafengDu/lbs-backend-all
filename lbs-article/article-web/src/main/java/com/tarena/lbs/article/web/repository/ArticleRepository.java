package com.tarena.lbs.article.web.repository;

import com.alibaba.fastjson2.JSON;
import com.tarena.lbs.article.web.service.ArticleService;
import com.tarena.lbs.pojo.content.entity.ArticleSearchEntity;
import com.tarena.lbs.pojo.content.query.ArticleQuery;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
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

    }
}
