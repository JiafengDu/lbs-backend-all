package com.tarena.lbs.basic.web.repository;

import com.tarena.lbs.basic.web.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
//从数据层开始,我们要准备mybatis-plus的框架
//减少单表的 读写sql编写成本
//你曾经用过的 BaseMapper<PO>
@Repository
public class AdminRepository {
    @Autowired
    private AdminMapper adminMapper;
}
