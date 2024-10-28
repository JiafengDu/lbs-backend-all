package com.tarena.lbs.marketing.web.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tarena.lbs.pojo.marketing.po.ActivityPO;
import java.util.List;
import org.apache.ibatis.annotations.Select;

public interface ActivityMapper extends BaseMapper<ActivityPO> {
    @Select("select * from activity where FIND_IN_SET(#{shopId},shop_ids)")
    List<ActivityPO> selectActivitiesByShopId(Integer id);
}
