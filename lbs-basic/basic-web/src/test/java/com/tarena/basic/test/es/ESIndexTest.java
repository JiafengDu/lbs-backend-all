package com.tarena.basic.test.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Before;

/**
 * 专题测试
 * RestHighLevelClient管理操作es的索引数据相关api
 */
public class ESIndexTest {
    //声明了一个连接对象
    private RestHighLevelClient client;
    //在所有test测试方法 管理索引 管理文档 搜索查询之前 对client对象初始化
    @Before
    public void init(){
        //准备一个连接的对象 localhost:9200 http
        HttpHost httpHost=new HttpHost("localhost",9200,"http");
        client=new RestHighLevelClient(RestClient.builder(httpHost));
    }


















}
