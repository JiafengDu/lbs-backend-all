package com.tarena.basic.test.es.practice;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;

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
        //TODO 初始化连接
    }

    private static String MAPPING="{\"properties\":{\"name\":{\"type\":\"text\",\"analyzer\":\"ik_max_word\",\"search_analyzer\":\"ik_smart\"},\"student_no\":{\"type\":\"text\"},\"birthday\":{\"type\":\"date\"},\"location\":{\"type\":\"geo_point\"},\"gender\":{\"type\":\"keyword\"}}}";
    private static void createIndexIfNotExists(String indexName) {
        try{
            //TODO 如果索引不存在 则创建索引 存在则先删除在创建
            //TODO 创建的索引需要指定分片1 副本0 使用书名MAPPING设置映射
        }catch (Exception e){
            log.error("测试环境创建索引:{},出现问题",indexName);
            log.error("具体问题:",e);
        }
    }
    private static void indexDocuments(List<Student> students,String indexName) throws Exception {
        //TODO 使用批量 构造索引文档
    }
    private static final String[] GENDERS={"男","女"};
    private static final String[] FAMALY_NAMES={"李","王","张","刘","陈","杨","赵","黄","周","吴"};
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
