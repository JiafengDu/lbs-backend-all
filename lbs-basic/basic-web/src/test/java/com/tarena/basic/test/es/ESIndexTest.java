package com.tarena.basic.test.es;

import org.apache.http.HttpHost;
import org.checkerframework.checker.units.qual.C;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    //创建索引
    //了解 client操作 读写es的 api的逻辑
    //es 一个web应用 提供了http协议 准备了很多功能的不同接口
    //接口功能不一样 请求方式 请求path路径 请求携带参数不一样
    //客户端 将不同的接口功能 单独封装意义明确的request类型和response类型,
    //客户端逻辑 就是封装request 发起请求 解析response 而不用关系http细节
    @Test
    public void createIndexDefault() throws IOException {
        //http 协议 使用请求方法 访问请求地址 携带请求头 参数 获取服务器响应
        //虽然底层是http协议 由于client封装我们无需关系协议的细节
        //1. 任何功能 和命令的发送 总是需要们创建一个对应功能请求对象 其中就包含了请求方法 请求地址 请求参数
        CreateIndexRequest request=new CreateIndexRequest("test01");
        //2. 调用客户端 发送请求 发送命令 需要先获取一个发送命令 索引管理对象
        IndicesClient indices = client.indices();
        //3. 发送命令 获取响应 es请求中 有时候需要一些请求头信息
        CreateIndexResponse response = indices.create(request, RequestOptions.DEFAULT);
        //4. 如果我们需要 可以从响应中 解析一些数据 拿到结果使用
        //4.1 可以从响应中拿到 当前索引创建的名称
        String indexName = response.index();
        System.out.println(indexName);
        //4.2 创建 操作 的结果 true创建成功 false创建失败
        boolean acknowledged = response.isAcknowledged();
        System.out.println(acknowledged);
        //4.3 拿到 创建索引的时候 es 中分片处理结果
        boolean shardsAcknowledged = response.isShardsAcknowledged();
        System.out.println(shardsAcknowledged);
    }

    //2 创建索引 但是携带自定义的分片和副本配置 分片5个 副本给1个
    @Test
    public void createIndexWithSettings() throws IOException {
        CreateIndexRequest request=new CreateIndexRequest("test02");
        //对这个请求 添加自定义配置 请求参数携带
        //1.1使用构造器 创建一个 5分片 1副本的setting设置
        /*
        {
           "number_of_shards":5,
           "number_of_replicas":1
        }
         */
        Settings.Builder setting = Settings.builder()
                .put("number_of_shards", 5)
                .put("number_of_replicas", 1);
        request.settings(setting);
        IndicesClient indices = client.indices();
        CreateIndexResponse response = indices.create(request, RequestOptions.DEFAULT);
        String indexName = response.index();
        System.out.println(indexName);
        boolean acknowledged = response.isAcknowledged();
        System.out.println(acknowledged);
        boolean shardsAcknowledged = response.isShardsAcknowledged();
        System.out.println(shardsAcknowledged);
    }
    //创建索引 带有mapping映射的数据
    @Test
    public void createIndexWithMapping() throws IOException {
        CreateIndexRequest request=new CreateIndexRequest("test03");
        //添加setting 1个分片 0个副本
        Settings.Builder setting = Settings.builder()
                .put("number_of_shards", 1)
                .put("number_of_replicas", 0);
        request.settings(setting);
        //同时 携带数据 mapping映射 定义 id 类型integer location 类型geo_point name 类型 text
        String mappingJson="{\"properties\":{\"id\":{\"type\":\"integer\"},\"location\":{\"type\":\"geo_point\"},\"name\":{\"type\":\"text\",\"analyzer\":\"ik_max_word\",\"search_analyzer\":\"ik_max_word\"}}}";
        //请求携带这个数据
        request.mapping(mappingJson, XContentType.JSON);
        IndicesClient indices = client.indices();
        CreateIndexResponse response = indices.create(request, RequestOptions.DEFAULT);
        String indexName = response.index();
        System.out.println(indexName);
        boolean acknowledged = response.isAcknowledged();
        System.out.println(acknowledged);
        boolean shardsAcknowledged = response.isShardsAcknowledged();
        System.out.println(shardsAcknowledged);
    }

    //删除索引
    @Test
    public void deleteIndex() throws IOException {
        //1.准备一个 专门做删除索引的 接口的请求对象
        DeleteIndexRequest request=new DeleteIndexRequest("test02");
        //2.使用索引管理对象发起命令请求
        IndicesClient indices = client.indices();
        AcknowledgedResponse response = indices.delete(request, RequestOptions.DEFAULT);
        //3.解析响应
        boolean acknowledged = response.isAcknowledged();
        System.out.println(acknowledged);//true 删除成功 false删除失败
    }
    //判断索引是否存在
    @Test
    public void exists() throws IOException {
        //判断存在 是一个客户端业务逻辑 底层就是查询索引 获取索引
        GetIndexRequest request=new GetIndexRequest("test03");
        //发送命令
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        //true是存在 不存在就是false
        System.out.println(exists);
    }
    //查询索引详情  属于读的操作 重点在于response的解析
    //搜索也是读操作 重点在于response解析
    @Test
    public void getIndexDetail() throws IOException {
        //构造查询索引请求 get
        GetIndexRequest request=new GetIndexRequest("test03");
        //发起请求 获取响应
        GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
        //解析response可以获取拿到  settings mapping 其他详情
        Map<String, List<AliasMetaData>> aliases = response.getAliases();//索引别名 方便理解
        System.out.println(aliases);
        Map<String, Settings> settings = response.getSettings();
        System.out.println(settings);
        Map<String, MappingMetaData> mappings = response.getMappings();
        System.out.println(mappings);
    }



















}
