/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tarena.lbs.attach.api;

import com.tarena.lbs.pojo.attach.dto.AttachDTO;
import com.tarena.lbs.pojo.attach.dto.AttachQrDTO;
import com.tarena.lbs.pojo.attach.param.AttachQRParam;
import com.tarena.lbs.pojo.attach.param.AttachUpdateParam;
import com.tarena.lbs.pojo.attach.param.PicUpdateParam;
import com.tarena.lbs.pojo.attach.query.AttachQuery;
import java.util.List;

public interface AttachApi {
    boolean batchUpdateBusiness(List<PicUpdateParam> params);
    /**
     * 功能描述: 获取文件地址
     */
    List<AttachDTO> getAttachInfoByParam(AttachQuery attachQuery);

    /**
     * 功能描述: 批量获取文件地址
     */
    @Deprecated
    List<AttachDTO> batchGetAttachInfo(List<Integer> ids,Integer type);

    /**
     * 功能描述: 批量更新文件封面
     */
    @Deprecated
    int batchUpdateAttachByIdList(List<AttachUpdateParam> attachUpdateParamList);

    @Deprecated
    int deleteAttachByBusinessIdAndBusinessType(AttachUpdateParam attachUpdateParam);
    @Deprecated
    int deleteAttachById(Long id);

    /**
     * 功能描述: 删除附件
     */
    @Deprecated
    int deleteAttachInfoByParam(AttachQuery attachQuery);

    /**
     * 生成二维码
     */
    @Deprecated
    AttachQrDTO generateQrCode(AttachQRParam attachQRParam);
}
