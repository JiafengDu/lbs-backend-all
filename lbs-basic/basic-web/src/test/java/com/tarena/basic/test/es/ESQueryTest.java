package com.tarena.basic.test.es;

import com.tarena.basic.test.es.practice.IndexBuilder;
import org.junit.Test;

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
}
