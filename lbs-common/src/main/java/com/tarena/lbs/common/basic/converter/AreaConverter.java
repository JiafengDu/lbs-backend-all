package com.tarena.lbs.common.basic.converter;

import com.tarena.lbs.pojo.basic.po.AreaPO;
import com.tarena.lbs.pojo.basic.vo.AreaVO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

@Configuration
public class AreaConverter {
    public List<AreaVO> assembleAreaTree(List<AreaPO> pos) {
        List<AreaVO> provinceVos = null;
        if (!CollectionUtils.isEmpty(pos)){
            List<AreaPO> provincePos=pos.stream().filter(po -> po.getParentId() == 0).collect(Collectors.toList());
            provinceVos=pos2vos(provincePos);
            for (AreaVO provinceVo : provinceVos) {
                Long provinceId = provinceVo.getId();
                List<AreaPO> cityPos=pos.stream().filter(po -> po.getParentId().equals(provinceId)).collect(Collectors.toList());
                List<AreaVO> cityVos=pos2vos(cityPos);
                provinceVo.setChildren(cityVos);
                for (AreaVO cityVo : cityVos) {
                    Long cityId = cityVo.getId();
                    List<AreaPO> areaPos=pos.stream().filter(po -> po.getParentId().equals(cityId)).collect(Collectors.toList());
                    List<AreaVO> areaVos=pos2vos(areaPos);
                    cityVo.setChildren(areaVos);
                }
            }
        }
        return provinceVos;
    }

    private List<AreaVO> pos2vos(List<AreaPO> pos) {
        List<AreaVO> vos=new ArrayList<>();
        if (!CollectionUtils.isEmpty(pos)){
            vos=pos.stream().map(po -> {
                AreaVO areaVO = new AreaVO();
                BeanUtils.copyProperties(po,areaVO);
                return areaVO;
            }).collect(Collectors.toList());
        }
        return vos;
    }

}
