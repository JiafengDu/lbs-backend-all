package com.tarena.lbs.basic.web.controller;

import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.basic.web.service.AreaService;
import com.tarena.lbs.pojo.basic.vo.AreaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AreaController {
    @Autowired
    private AreaService areaService;
    @GetMapping("/admin/basic/area/queryList")
    public Result<List<AreaVO>> queryProvinces() {
        Integer parentId = 0;
        List<AreaVO> voList = areaService.getChildren(parentId);
        return new Result<>(voList);
    }

    @GetMapping("/admin/basic/area/{parentId}/children")
    public Result<List<AreaVO>> queryChildren(@PathVariable("parentId") Integer parentId) {
        List<AreaVO> voList = areaService.getChildren(parentId);
        return new Result<>(voList);
    }
}
