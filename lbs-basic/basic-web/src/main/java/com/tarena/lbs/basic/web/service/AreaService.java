package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.tarena.lbs.basic.web.repository.AreaRepository;
import com.tarena.lbs.pojo.basic.po.AreaPO;
import com.tarena.lbs.pojo.basic.vo.AreaVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AreaService {
    @Autowired
    private AreaRepository areaRepository;

    public List<AreaVO> getChildren(Integer parentId) {
        List<AreaVO> vos = null;
        List<AreaPO> pos = areaRepository.getChildren(parentId);
        if (CollectionUtils.isNotEmpty(pos)) {
            vos = pos.stream().map(po->{
                AreaVO vo = new AreaVO();
                BeanUtils.copyProperties(po, vo);
                return vo;
            }).collect(Collectors.toList());
        }
        return vos;
    }
}
