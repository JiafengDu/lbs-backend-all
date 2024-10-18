package com.tarena.lbs.basic.web.service;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.tarena.lbs.basic.web.repository.AreaRepository;
import com.tarena.lbs.pojo.basic.po.AreaPO;
import com.tarena.lbs.pojo.basic.vo.AreaVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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


    public List<AreaVO> tree() {
        //1.查询所有的区域数据 包括 所有省 所有市 所有区(千级)
        List<AreaPO> pos=areaRepository.getAll();
        //2.单独封装
        return assembleAreaTree(pos);
    }

    private List<AreaVO> assembleAreaTree(List<AreaPO> pos) {
        //1.pos包含所有级别的区域数据 省 市 区 只要将关系找到就可以封装返回结果
        //2.拿到所有的省 筛选 parentId=0所有po对象
        List<AreaPO> provincePos = pos.stream().filter(po -> po.getParentId().equals(0l)).collect(Collectors.toList());
        log.info("拿到了所有的省:{}",provincePos);
        //3.将省数据 转化成vo
        List<AreaVO> provinceVos=pos2vos(provincePos);
        //4.AreaVO对象表示省的数据 需要给省封装市list
        provinceVos.stream().forEach(provinceVo->{
            //给当前这个省的children 添加一个list<AreaVO> 是当前省的下一级市列表 筛选pos 转化vo
            Long provinceId=provinceVo.getId();
            //从pos筛选出来 parentId==provinceId的所有数据  就是下一级市
            List<AreaPO> cityPos=pos.stream().filter(po->po.getParentId().equals(provinceId)).collect(Collectors.toList());
            log.info("拿到当前省:{}的所有市:{}",provinceVo.getName(),cityPos);
            //转化vos
            List<AreaVO> cityVos=pos2vos(cityPos);
            //对城市的vo列表循环
            cityVos.stream().forEach(cityVo->{
                //从市数据拿到市id 筛选当前市下的所有区 转化成vos封装给这个是的children属性
                Long cityId=cityVo.getId();
                List<AreaPO> areaPos = pos.stream().filter(po -> po.getParentId().equals(cityId)).collect(Collectors.toList());
                log.info("拿到当前市:{}的所有区:{}",cityVo.getName(),areaPos);
                List<AreaVO> areaVos = pos2vos(areaPos);
                cityVo.setChildren(areaVos);
            });
            //将市的vo列表交给省的children
            provinceVo.setChildren(cityVos);
        });
        return provinceVos;
    }

    private List<AreaVO> pos2vos(List<AreaPO> pos) {
        return pos.stream().map(po->{
            AreaVO vo=new AreaVO();
            BeanUtils.copyProperties(po,vo);
            return vo;
        }).collect(Collectors.toList());
    }
}
