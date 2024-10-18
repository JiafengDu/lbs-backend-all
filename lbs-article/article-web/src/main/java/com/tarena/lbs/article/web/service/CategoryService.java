package com.tarena.lbs.article.web.service;

import com.tarena.lbs.article.web.repository.CategoryRepository;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.pojo.basic.po.ArticleCategoryPO;
import com.tarena.lbs.pojo.content.vo.ArticleCategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public PageResult<ArticleCategoryVO> pageList() {
        PageResult<ArticleCategoryVO> voPage=new PageResult<>();
        voPage.setPageNo(1);
        voPage.setPageSize(10);
        voPage.setTotal(100L);
        List<ArticleCategoryVO> vos=null;
        List<ArticleCategoryPO> pos=categoryRepository.getCategories();
        log.info("查询所有文章分类:{}",pos);
        vos=pos.stream().map(po->{
            ArticleCategoryVO vo=new ArticleCategoryVO();
            BeanUtils.copyProperties(po,vo);
            return vo;
        }).collect(Collectors.toList());
        voPage.setObjects(vos);
        return voPage;
    }
}
