package com.tarena.basic.test.pagehelper;

import com.github.pagehelper.PageInfo;
import com.tarena.lbs.basic.web.BasicWeb;
import com.tarena.lbs.basic.web.repository.BusinessRepository;
import com.tarena.lbs.pojo.basic.po.BusinessPO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes= BasicWeb.class)
public class PageTest {
    @Autowired
    private BusinessRepository businessRepository;
    @Test
    public void pageTest(){
        PageInfo<BusinessPO> pageInfo =
                businessRepository.pageList();
        //pageInfo已经包含了分页的2个属性 total和list
        long total = pageInfo.getTotal();
        List<BusinessPO> list = pageInfo.getList();
        int pageNum = pageInfo.getPageNum();
        int pageSize = pageInfo.getPageSize();
        System.out.println("总数:"+total);
        System.out.println("分页列表:"+list);
        System.out.println("当前页:"+pageNum);
        System.out.println("每页显示:"+pageSize);

    }
}
