package com.tarena.lbs.basic.web.controller;

import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.basic.web.service.AreaService;
import com.tarena.lbs.pojo.basic.vo.AreaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 处理区域 地市数据业务的接口类
 */
@RestController
public class AreaController {
    @Autowired
    private AreaService areaService;
    //前端查询 省级列表的请求接口
    @GetMapping("/admin/basic/area/queryList")
    public Result<List<AreaVO>> queryProvinces(){
        Integer parentId=0;//所有省级地区 parentId都是固定值0
        //业务层 总是接收parentId 查询下级城市列表
        List<AreaVO> vos=areaService.getChildren(parentId);
        return new Result<>(vos);
    }





















}
