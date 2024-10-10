package com.tarena.lbs.basic.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tarena.lbs.pojo.basic.po.BusinessPO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BusinessMapper extends BaseMapper<BusinessPO> {
    //mybatis的入参 #{}占位符 如果方法入参多个 必须指定参数变量名称 否则sql语句无法识别参数占位符
    //指定变量名称 指定对应关系 使用注解@Param
    @Select("select * from lbs_business limit #{from},#{size}")
    List<BusinessPO> selectPages(@Param("from") int from, @Param("size") int size);
}
