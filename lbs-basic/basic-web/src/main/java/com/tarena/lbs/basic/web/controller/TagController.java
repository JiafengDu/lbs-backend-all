package com.tarena.lbs.basic.web.controller;

import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.basic.web.service.TagService;
import com.tarena.lbs.pojo.basic.vo.TagVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 与标签有关的业务接口
 */
@RestController
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/basic/tagLibrary/info/article/tags")
    public Result<Map<String, List<TagVO>>> getArticleTags(){
        //考虑 既然标签结构 文章和用户相同 后续还会查询用户标签 tagType=0|1
        Map<String,List<TagVO>> tags=tagService.getTagsByType(0);
        return new Result<>(tags);
    }
}
