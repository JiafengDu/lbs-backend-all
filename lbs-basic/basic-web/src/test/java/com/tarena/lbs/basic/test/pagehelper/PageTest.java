package com.tarena.lbs.basic.test.pagehelper;

import com.github.pagehelper.PageInfo;
import com.tarena.lbs.basic.web.BasicWeb;
import com.tarena.lbs.basic.web.repository.BusinessRepository;
import com.tarena.lbs.pojo.basic.po.BusinessPO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes= BasicWeb.class)
public class PageTest {
    @Autowired
    private BusinessRepository businessRepository;
    @Test
    public void pageTest() {
        PageInfo<BusinessPO> pageInfo = businessRepository.pageList();
        long total = pageInfo.getTotal();
        List<BusinessPO> pos = pageInfo.getList();
        int pageNo = pageInfo.getPageNum();
        int pageSize = pageInfo.getPageSize();
        System.out.println("total=" + total);
        System.out.println("pageNo=" + pageNo);
        System.out.println("pageSize=" + pageSize);
    }
}
