package com.tarena.lbs.article.web.repository;

import com.tarena.lbs.article.web.mapper.CategoryMapper;
import com.tarena.lbs.pojo.basic.po.ArticleCategoryPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryRepository {
    @Autowired
    private CategoryMapper categoryMapper;

    public List<ArticleCategoryPO> getCategories() {
        return categoryMapper.selectList(null);
    }
}
