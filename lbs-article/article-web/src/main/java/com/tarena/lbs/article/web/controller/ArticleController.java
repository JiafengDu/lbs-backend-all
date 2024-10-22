package com.tarena.lbs.article.web.controller;

import com.tarena.lbs.article.web.service.ArticleService;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.base.protocol.pager.PageResult;
import com.tarena.lbs.pojo.content.param.ArticleContentParam;
import com.tarena.lbs.pojo.content.query.ArticleQuery;
import com.tarena.lbs.pojo.content.vo.ArticleVO;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 文章业务模块接口
 */
@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    //文章分页列表查询
    //如果是后台查询 source=2
    //如果是前台查询 source=1
    @GetMapping("/admin/content/article/list")
    public Result<PageResult<ArticleVO>> pageList(ArticleQuery articleQuery)
        throws BusinessException{
        return new Result<>(articleService.pageList(articleQuery));
    }
    //提交推文创作 包含前台 和后台
    @PostMapping("/admin/content/article/insert")
    public Result<Void> addArticle(ArticleContentParam param)
        throws BusinessException{
        articleService.addArticle(param);
        return Result.success();
    }
    //手机端查询范围内的文章标签集合
    @GetMapping("/admin/content/article/getArticleLabel")
    public Result<Set<String>> articleLabels(ArticleQuery articleQuery) throws BusinessException {
        Set<String> labels=articleService.articleLabels(articleQuery);
        return new Result<>(labels);
    }
}
