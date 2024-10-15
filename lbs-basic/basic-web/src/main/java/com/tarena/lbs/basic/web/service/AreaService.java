package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.tarena.lbs.basic.web.repository.AreaRepository;
import com.tarena.lbs.pojo.basic.po.AreaPO;
import com.tarena.lbs.pojo.basic.vo.AreaVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AreaService {
    @Autowired
    private AreaRepository areaRepository;

    public List<AreaVO> getChildren(Integer parentId) {
        List<AreaVO> vos=null;
        //1.先调用仓储层 查询pos
        List<AreaPO> pos=areaRepository.getChildren(parentId);
        //2.pos非空时 转换成vos
        if (CollectionUtils.isNotEmpty(pos)){
            vos=pos.stream().map(po->{
                AreaVO vo=new AreaVO();
                BeanUtils.copyProperties(po,vo);
                return vo;
            }).collect(Collectors.toList());
        }
        return vos;
    }





}
