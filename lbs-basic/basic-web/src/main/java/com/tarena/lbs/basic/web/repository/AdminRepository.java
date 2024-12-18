package com.tarena.lbs.basic.web.repository;

import com.tarena.lbs.basic.web.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AdminRepository {
    @Autowired
    private AdminMapper adminMapper;
}
