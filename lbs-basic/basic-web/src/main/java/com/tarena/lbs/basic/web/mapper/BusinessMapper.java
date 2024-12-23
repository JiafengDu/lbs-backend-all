package com.tarena.lbs.basic.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tarena.lbs.pojo.basic.po.BusinessPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BusinessMapper extends BaseMapper<BusinessPO> {
    @Select("select * from lbs_business limit #{from}, #{size}")
    List<BusinessPO> selectPages(@Param("from")int from, @Param("size") int size);
}
