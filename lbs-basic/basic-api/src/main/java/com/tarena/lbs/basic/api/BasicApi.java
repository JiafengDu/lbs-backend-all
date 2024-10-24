package com.tarena.lbs.basic.api;

import com.tarena.lbs.pojo.basic.dto.AdminDTO;

import java.util.List;

/**
 * basic服务对外暴露的dubbo调用接口
 */
public interface BasicApi {
    AdminDTO getAdminDetail(Integer id);
    List<Integer> getUserGroupIds(Integer userId,Integer businessId);
}
