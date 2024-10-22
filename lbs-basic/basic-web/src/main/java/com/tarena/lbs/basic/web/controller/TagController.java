package com.tarena.lbs.basic.web.controller;

import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.basic.web.service.TagService;
import com.tarena.lbs.pojo.basic.param.UserTagsParam;
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
    //文章标签 查询 一级标签名字是key 二级标签 列表对象是value
    @GetMapping("/basic/tagLibrary/info/article/tags")
    public Result<Map<String, List<TagVO>>> getArticleTags(){
        //考虑 既然标签结构 文章和用户相同 后续还会查询用户标签 tagType=0|1
        Map<String,List<TagVO>> tags=tagService.getTagsByType(0);
        return new Result<>(tags);
    }
    //用户标签 和文章标签数据结构 查询逻辑完全相同 只是入参传递的是tag_type=1
    @GetMapping("/basic/tagLibrary/info/userList")
    public Result<Map<String, List<TagVO>>> getUserTags(){
        //考虑 既然标签结构 文章和用户相同 后续还会查询用户标签 tagType=0|1
        Map<String,List<TagVO>> tags=tagService.getTagsByType(1);
        return new Result<>(tags);
    }

    //用户提交绑定关联所选标签
    @GetMapping("/basic/user/tag")
    public Result<Void> bindUserTags(UserTagsParam param)
        throws BusinessException{
        tagService.bindUserTags(param);
        return Result.success();
    }
}
