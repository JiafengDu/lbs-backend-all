package com.tarena.lbs.basic.web.rpc;

import com.tarena.lbs.basic.api.BasicApi;
import com.tarena.lbs.basic.web.service.AdminService;
import com.tarena.lbs.basic.web.service.GroupService;
import com.tarena.lbs.pojo.basic.dto.AdminDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@DubboService
@Slf4j
public class BasicApiImpl implements BasicApi {
    //rpc入口 可以调用业务层 查询对应详情
    @Autowired
    private AdminService adminService;
    @Autowired
    private GroupService groupService;
    @Override
    public AdminDTO getAdminDetail(Integer id) {
        return adminService.detail(id);
    }

    @Override
    public List<Integer> getUserGroupIds(Integer userId, Integer businessId) {
        List<Integer> userGroupIds=null;
        try{
            return groupService.getUserGroups(userId,businessId);
        }catch (Exception e){
            log.error("查询用户:{},商家:{}的用户人群失败",userId,businessId,e);
        }
        return null;
    }
}
