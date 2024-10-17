package com.tarena.basic.test.es;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专门用来测试文档功能的测试
 */
@Slf4j
public class ESDocTest {
    private RestHighLevelClient client;
    @Before
    public void init(){
        //http://localhost:9200
        HttpHost host = new HttpHost("localhost",9200,"http");
        client=new RestHighLevelClient(RestClient.builder(host));
    }
    //测试新增文档(1一个)
    @Test
    public void testAddDoc() throws IOException {
        //准备一个新增doc文档对象.
        DocEntity docEntity = new DocEntity(2,"40.998,118.189","王有才");
        //1.无论做什么操作 先要准备一个匹配当前功能的请求对象
        IndexRequest request = new IndexRequest("test01");//分别在一个没有mapping的索引 和有自定义mapping的索引写入
        //request 写入索引的时候 要指定请求参数
        //1.1 文档的id 如果不设置 es会随机生成一个唯一值
        request.id(docEntity.getId()+"");
        //1.2 添加文档对象数据 可以使用json字符串传参
        //docEntity-->json {"id":1,"location":"39,116","name":"王翠花"}
        //很多工具可以完成java对象和字符串json的转化 比如 jackson ObjectMapper 使用alibaba的Json工具fastjson
        String dataJson= JSON.toJSONString(docEntity);
        log.info("当前数据文档的json:{}",dataJson);
        request.source(dataJson, XContentType.JSON);
        //2.客户端发起请求 写入文档
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        //3.解析响应 response 肯定会告知索引这条文档的结果 成功 还是 失败
        DocWriteResponse.Result result = response.getResult();
        log.info("解析响应 result:{}",result);
        ReplicationResponse.ShardInfo shardInfo = response.getShardInfo();//文档存储所在的分片信息
        log.info("分片信息 shardInfo:{}",shardInfo);
    }
    //查询文档对象   不是搜索是查询
    //写数据 重点是组织request 查询数据 重点解析 response
    //ex 查询一个文档 使用文档Id
    //GET /{indexName}/_doc/{docId} 从哪个索引利用 docId查询文档
    /* 这个json返回值会包含在response里
        {
            "_index": "test01",
            "_type": "_doc",
            "_id": "1",
            "_version": 1,
            "_seq_no": 0,
            "_primary_term": 2,
            "found": true,
            "_source": {
                "id": 1,
                "location": "39.909,116.378",
                "name": "王翠花"
            }
        }
     */
    @Test
    public void testGetDoc() throws IOException {
        //1. 构造一个查询的request
        GetRequest request=new GetRequest("test03","2");
        //2. 发送请求
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        //3. 解析这个文档对象 转化成 entity
        //获取文档所在的索引
        response.getIndex();
        //获取文档的id
        response.getId();
        //获取文档version
        response.getVersion();
        //获取文档数据 source 多种结构 map
        Map<String, Object> sourceMap = response.getSource();
        //获取文档数据 source 多种结构 json
        String sourceJson = response.getSourceAsString();
        log.info("获取文档字符串 :{}",sourceJson);
        //可以将json 转化回entity
        DocEntity docEntity = JSON.parseObject(sourceJson, DocEntity.class);
        log.info("获取到文档对象:{}",docEntity);
    }
    //删除
    @Test
    public void deleteDoc(){
        DeleteRequest request=new DeleteRequest("test01","2");
        try {
            client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //更新
    //如果更新指的是完整的覆盖 就可以直接使用新增
    //如果是部分更新就用不到 覆盖新增了可以执行update
    @Test
    public void updateDoc(){
        //1. 创建一个更新请求
        UpdateRequest request=new UpdateRequest("test01","2");
        //2. 只更新其中 一个name 请求中携带部分更新的数据 只更新name:王翠花
        Map<String,Object> params=new HashMap<>();
        params.put("name","王晓华");
        request.doc(params);
        //3. 发起请求解析响应
        try {
            client.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //批量
    //加入做批量数据功能 一次性从数据库将50万分数据写入es
    //查询 总数 发现50万条
    //将50万条 分批 一批1000条 分500次 每次批量写入es 1000条每次 就可以使用 批量操作
    //ES bulk
    @Test
    public void testBulk() throws IOException {
        //list模拟批量
        List<DocEntity> docs=new ArrayList<>();
        docs.add(new DocEntity(101,"40,120","mike"));
        docs.add(new DocEntity(100,"39,119","mary"));
        //2构造一个 bulk批量请求 封装 所有要做的request对象
        BulkRequest request=new BulkRequest();
        //对于bulk对象 就是将每次index创建索引对象请求 做包装 携带给es执行
        //3循环文档封装批量请求
        for (DocEntity doc : docs) {
            //每一个创建文档的请求
            IndexRequest indexRequest=new IndexRequest("test01");
            String jsonString = JSON.toJSONString(doc);
            indexRequest.id(doc.getId()+"");
            indexRequest.source(jsonString, XContentType.JSON);
            //交给批量请求处理
            request.add(indexRequest);
        }
        //调用发送批量命令
        client.bulk(request, RequestOptions.DEFAULT);
    }
}
