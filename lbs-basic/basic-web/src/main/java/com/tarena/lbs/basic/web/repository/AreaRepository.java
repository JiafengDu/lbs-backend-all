package com.tarena.lbs.basic.web.repository;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tarena.lbs.basic.web.mapper.AreaMapper;
import com.tarena.lbs.pojo.basic.po.AreaPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AreaRepository {
    @Autowired
    private AreaMapper areaMapper;

    public List<AreaPO> getChildren(Integer parentId) {
        //select * from lbs_area where parent_id=?
        QueryWrapper<AreaPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        return areaMapper.selectList(queryWrapper);
    }
}
