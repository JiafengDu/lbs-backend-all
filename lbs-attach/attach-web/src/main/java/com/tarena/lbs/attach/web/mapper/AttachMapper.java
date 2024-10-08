package com.tarena.lbs.attach.web.mapper;

import com.tarena.lbs.pojo.attach.dos.Attach;
import com.tarena.lbs.pojo.attach.param.AttachUpdateParam;
import com.tarena.lbs.pojo.attach.param.PicUpdateParam;
import com.tarena.lbs.pojo.attach.query.AttachDeprecatedQuery;
import com.tarena.lbs.pojo.attach.query.AttachQuery;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AttachMapper {
    int insert(Attach record);

    int insertSelective(Attach record);

    Attach selectByPrimaryKey(Integer id);

    List<Attach> getAttachInfo(AttachQuery attachQuery);

    List<Attach> batchGetAttachInfo(@Param("ids") List<Integer> ids,@Param("businessType") Integer businessType);

    int updateByPrimaryKeySelective(Attach record);

    int updateByPrimaryKey(Attach record);

    int batchUpdateAttachByIdList(@Param("params") List<AttachUpdateParam> attachUpdateParamList);

    int deleteAttachByBusinessIdAndBusinessType(AttachUpdateParam param);

    int deleteAttachById(Long id);

    int batchUpdateAttachStatus(@Param("status") Integer status, @Param("ids") List<Integer> ids);

    List<Attach> getDeprecatedAttachInfo(AttachDeprecatedQuery query);

    int deleteDeprecatedAttaches(List<Integer> list);

    int batchUpdateAttachByIdOrFileUUID(List<PicUpdateParam> params);
}