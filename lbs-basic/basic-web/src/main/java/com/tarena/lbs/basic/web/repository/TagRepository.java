package com.tarena.lbs.basic.web.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tarena.lbs.basic.web.mapper.TagMapper;
import com.tarena.lbs.pojo.basic.po.TagLibraryPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagRepository {
    @Autowired
    private TagMapper tagMapper;

    public List<TagLibraryPO> getTagsBytType(Integer tagType) {
        //select * from lbs_tag_library where tag_type=#{}
        QueryWrapper<TagLibraryPO> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("tag_type",tagType);
        return tagMapper.selectList(queryWrapper);
    }
}
