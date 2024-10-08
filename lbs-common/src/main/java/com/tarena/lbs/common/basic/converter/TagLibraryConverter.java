package com.tarena.lbs.common.basic.converter;

import com.tarena.lbs.pojo.basic.po.TagLibraryPO;
import com.tarena.lbs.pojo.basic.vo.ArticleTagVO;
import com.tarena.lbs.pojo.basic.vo.TagLibraryVO;
import com.tarena.lbs.pojo.basic.vo.UserTagVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class TagLibraryConverter {
    public Map<String, List<UserTagVO>> assembleUserTagVOMap(List<TagLibraryPO> tagPos){
        if (CollectionUtils.isEmpty(tagPos)){
            return null;
        }
        List<TagLibraryPO> parentTags=tagPos.stream().filter(po -> po.getTagParentId()==0).collect(Collectors.toList());
        Map<String,List<UserTagVO>> tagMapVos=new HashMap<>();
        for (TagLibraryPO parentPo : parentTags) {
            String name = parentPo.getTagName();
            List<UserTagVO> collect =null;
            List<TagLibraryPO> childPos = tagPos.stream().filter(po -> po.getTagParentId().equals(parentPo.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(childPos)){
                collect = childPos.stream().map(po -> {
                    UserTagVO vo = new UserTagVO();
                    TagLibraryVO tagVO = new TagLibraryVO();
                    BeanUtils.copyProperties(po, tagVO);
                    vo.setTagCategoryName(name);
                    vo.setTagLibraryBO(tagVO);
                    return vo;

                }).collect(Collectors.toList());
            }
            tagMapVos.put(name, collect);
        }
        return tagMapVos;
    }

    public Map<String, List<ArticleTagVO>> assembleArticleTagVOMap(List<TagLibraryPO> tagPos){
        if (CollectionUtils.isEmpty(tagPos)){
            return null;
        }
        List<TagLibraryPO> parentTags=tagPos.stream().filter(po -> po.getTagParentId()==0).collect(Collectors.toList());
        Map<String,List<ArticleTagVO>> tagMapVos=new HashMap<>();
        for (TagLibraryPO parentPo : parentTags) {
            String name = parentPo.getTagName();
            List<ArticleTagVO> collect =null;
            List<TagLibraryPO> childPos = tagPos.stream().filter(po -> po.getTagParentId().equals(parentPo.getId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(childPos)){
                collect = childPos.stream().map(po -> {
                    ArticleTagVO vo = new ArticleTagVO();
                    TagLibraryVO tagVO = new TagLibraryVO();
                    BeanUtils.copyProperties(po, tagVO);
                    vo.setTagCategoryName(name);
                    vo.setTagLibraryBO(tagVO);
                    return vo;

                }).collect(Collectors.toList());
            }
            tagMapVos.put(name, collect);
        }
        return tagMapVos;
    }

}
