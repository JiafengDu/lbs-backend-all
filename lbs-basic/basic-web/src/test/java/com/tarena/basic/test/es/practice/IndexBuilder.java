package com.tarena.basic.test.es.practice;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
public class IndexBuilder {
    public static void buildData(String indexName,int docCount) throws Exception {
        createIndexIfNotExists(indexName);
        List<Student> students = generateStudents(docCount); // 生成100个学生文档
        indexDocuments(students,indexName); // 将学生文档索引到Elasticsearch中
    }
    private static RestHighLevelClient client;// 初始化你的Elasticsearch客户端
    static {
        HttpHost host = new HttpHost("localhost", 9200, "http");
        client=new RestHighLevelClient(RestClient.builder(host));
    }

    private static String MAPPING="{\"properties\":{\"name\":{\"type\":\"text\",\"analyzer\":\"ik_max_word\",\"search_analyzer\":\"ik_smart\"},\"studentNo\":{\"type\":\"keyword\"},\"birthday\":{\"type\":\"date\"},\"location\":{\"type\":\"geo_point\"},\"gender\":{\"type\":\"keyword\"}}}";
    private static void createIndexIfNotExists(String indexName) {
        try{
            //1 判断存在
            GetIndexRequest existsRequest=new GetIndexRequest(indexName);
            Boolean exists=client.indices().exists(existsRequest, RequestOptions.DEFAULT);
            if (exists){
                //2.删除 索引 保证每次创建都是 能够新建一个新索引
                DeleteIndexRequest  deleteRequest=new DeleteIndexRequest(indexName);
                client.indices().delete(deleteRequest, RequestOptions.DEFAULT);
            }
            //3 当前一定不存在所需创建的索引 创建索引
            CreateIndexRequest createRequest=new CreateIndexRequest(indexName);
            //创建的索引需要指定分片1 副本0 使用上述MAPPING设置映射
            //3.1 设置settings
            Settings.Builder settingBuilder = Settings.builder()
                    .put("number_of_shards", 1)
                    .put("number_of_replicas", 0);
            createRequest.settings(settingBuilder);
            //3.2 设置mapping json
            createRequest.mapping(MAPPING, XContentType.JSON);
            client.indices().create(createRequest, RequestOptions.DEFAULT);
        }catch (Exception e){
            log.error("测试环境创建索引:{},出现问题",indexName);
            log.error("具体问题:",e);
        }
    }
    private static void indexDocuments(List<Student> students,String indexName) throws Exception {
        //使用批量 构造索引文档
        //1.准备一个批量操作的请求对象
        BulkRequest bulkRequest=new BulkRequest();
        //2.循环添加文档对象请求 交给批量处理
        students.stream().forEach(student->{
            //将学生 转化json 创建 索引文档的请求 交给批量
            IndexRequest request=new IndexRequest(indexName);
            request.source(JSON.toJSONString(student),XContentType.JSON);
            bulkRequest.add(request);
        });
        //3.发起批量命令
        client.bulk(bulkRequest, RequestOptions.DEFAULT);
        //4.可以解析响应 每一个request bulk都会记录响应
    }
    private static final String[] GENDERS={"男","女"};
    private static final String[] FAMALY_NAMES = {
            "李", "王", "张", "刘", "陈", "杨", "赵", "黄", "周", "吴",
            "徐", "孙", "马", "朱", "胡", "郭", "林", "何", "高", "罗"
    };
    private static final String[] NAMES = {
            "小明", "亮亮", "小红", "芳芳", "小刚", "强强",
            "小李", "丽丽", "小王", "巍巍", "小赵", "昭昭",
            "小周", "舟舟", "小吴", "薇薇", "小郑", "珍珍",
            "小张", "章章", "小黄", "煌煌"
    };

    private static List<Student> generateStudents(int docCount) {
        List<Student> students = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < docCount; i++){
            String name=FAMALY_NAMES[(int)(Math.random()*FAMALY_NAMES.length)]+NAMES[(int)(Math.random()*NAMES.length)];
            String gender=GENDERS[(int)(Math.random()*GENDERS.length)];
            Student student = new Student(
                    name,
                    "2021000" + i,
                    LocalDate.of(2000 + random.nextInt(20), 1, 1),
                    (39.9042 + random.nextDouble() * 0.1)+","+(116.4074 + random.nextDouble() * 0.1)
                    ,gender);
            students.add(student);
        }
        return students;
    }

}
