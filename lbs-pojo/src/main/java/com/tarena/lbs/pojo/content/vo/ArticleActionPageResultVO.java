package com.tarena.lbs.pojo.content.vo;

import com.tarena.lbs.base.protocol.pager.PageResult;
import lombok.Data;

@Data
public class ArticleActionPageResultVO {
    private PageResult<ArticleActionVO> likeFuturePageResult;
    private PageResult<ArticleActionVO> collectFutureOPageResult;
    private PageResult<ArticleActionVO> commentFuturePageResult;
}
