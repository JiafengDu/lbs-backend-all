package com.tarena.basic.test.es;

import com.tarena.basic.test.es.practice.IndexBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class ESQueryTest {
    //首先准备一批测试搜索数据
    private static final String TEST_INDEX="students";
    @Test
    public void initDatas(){
        try {
            IndexBuilder.buildData(TEST_INDEX,200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private RestHighLevelClient client;
    @Before
    public void init(){
        //http://localhost:9200
        HttpHost host = new HttpHost("localhost",9200,"http");
        client=new RestHighLevelClient(RestClient.builder(host));
    }

    //使用matchAll查询条件 编写基本的查询步骤
    @Test
    public void matchAllQuery() throws IOException {
        //1.构造一个对应功能的请求对象
        SearchRequest request=new SearchRequest(TEST_INDEX);
        //2.在请求中封装查询条件 组织请求参数{"query":{"match_all":{}}}
        //查询条件属性数据比较多 复杂,在java客户端代码中 给我们提供了一个构造器
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        //a.设置分页查询条件 from,size
        sourceBuilder.from(0).size(10);
        //b.查询的数据 默认排序是匹配度评分降序 评分越高 匹配度越好 排序越靠前 但是我们可以不计算平分 使用自定义
        sourceBuilder.sort("birthday", SortOrder.DESC);
        //c.去重条件 一般是做统计使用 去重的字段 如果是文本 不能是text(分词 多个词语) 必须keyword date是文本TEXT?还是numbers?
        //sourceBuilder.collapse(new CollapseBuilder("gender"));
        //d.业务查询的 搜索逻辑 数据query 不同的query功能不同
        MatchAllQueryBuilder query = QueryBuilders.matchAllQuery();
        sourceBuilder.query(query);
        request.source(sourceBuilder);
        //3.发送命令 搜索
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        //4.响应解析 如果观察过插件 http协议访问接口返回值 解析响应和返回值对应的
        //4.1 解析一些查询统计 状态数据
        //took 本次查询花费的时长 毫秒数
        TimeValue took = response.getTook();
        log.info("本次请求查询搜索 took:{}",took);
        //time_out 本次查询是否超时 true false
        boolean timedOut = response.isTimedOut();
        log.info("本次请求查询搜索 timedOut:{}",timedOut);
        //统计查询的分片数据信息
        int successfulShards = response.getSuccessfulShards();
        log.info("本次请求查询搜索 successfulShards:{}",successfulShards);
        //解析的命中查询到的文档数据 hits命中
        SearchHits allHits = response.getHits();//包含total 包含max_score 包含查询的文档列表
        //命中 total对象
        TotalHits totalHits = allHits.getTotalHits();//描述命中总数
        log.info("本次查询命中文档总数,total:{}",totalHits.value);
        log.info("本次查询命中文关系,relation:{}",totalHits.relation);
        //命中最高分 如果我们定义了 排序 评分就不在计算了
        float maxScore = allHits.getMaxScore();
        log.info("本次查询命中文档最高分,maxScore:{}",maxScore);
        //从查询结果中解析 文档列表
        SearchHit[] docHits = allHits.getHits();//每个元素 表示当初使用id查询的doc封装
        Arrays.stream(docHits).forEach(docHit->{
            log.info("本次命中查询结果 doc:{}",docHit.getSourceAsString());
        });
    }
    //term词项查询
    @Test
    public void termQuery(){
        SearchRequest request=new SearchRequest(TEST_INDEX);
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        //徐丽丽字符串 存储 创建词项的时候 分词逻辑 徐丽丽--> 徐 丽 徐丽  徐丽丽
        TermQueryBuilder query = QueryBuilders.termQuery("name", "丽");//where age=18
        sourceBuilder.query(query).from(0).size(200);
        log.info("最终查询参数:{}",sourceBuilder);
        request.source(sourceBuilder);
        SearchResponse response =null;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
            log.info("本次查询命中文档总数,total:{}",response.getHits().getTotalHits().value);
        } catch (IOException e) {
            e.printStackTrace();
        }
        TotalHits totalHits = response.getHits().getTotalHits();
        log.info("查询总数:{}",totalHits.value);
        float maxScore = response.getHits().getMaxScore();
        log.info("最高分:{}",maxScore);
        SearchHit[] hits = response.getHits().getHits();
        Arrays.stream(hits).forEach(hit->{
            log.info("命中结果:{}",hit.getSourceAsString());
        });
    }

}
