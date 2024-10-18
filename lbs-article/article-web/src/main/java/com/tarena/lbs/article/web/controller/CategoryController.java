package com.tarena.lbs.article.web.controller;

import com.tarena.lbs.article.web.service.CategoryService;
import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.pojo.content.vo.ArticleCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理文章分类 业务模块功能
 */
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    //查询分类的 分页列表
    @GetMapping("/admin/content/category/list")
    public Result<PageResult<ArticleCategoryVO>> pageList(){
        return new Result<>(categoryService.pageList());
    }
}
