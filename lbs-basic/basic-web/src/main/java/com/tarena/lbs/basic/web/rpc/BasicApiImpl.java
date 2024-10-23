package com.tarena.lbs.basic.web.rpc;

import com.tarena.lbs.basic.api.BasicApi;
import com.tarena.lbs.basic.web.service.AdminService;
import com.tarena.lbs.pojo.basic.dto.AdminDTO;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@DubboService
public class BasicApiImpl implements BasicApi {
    //rpc入口 可以调用业务层 查询对应详情
    @Autowired
    private AdminService adminService;
    @Override
    public AdminDTO getAdminDetail(Integer id) {
        return adminService.detail(id);
    }
}
