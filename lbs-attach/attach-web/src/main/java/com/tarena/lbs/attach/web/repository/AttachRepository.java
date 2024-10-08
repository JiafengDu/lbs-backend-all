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
package com.tarena.lbs.attach.web.repository;

import com.tarena.lbs.pojo.attach.dto.AttachDTO;
import com.tarena.lbs.pojo.attach.param.AttachParam;
import com.tarena.lbs.pojo.attach.param.AttachUpdateParam;
import com.tarena.lbs.pojo.attach.param.PicUpdateParam;
import com.tarena.lbs.pojo.attach.query.AttachDeprecatedQuery;
import com.tarena.lbs.pojo.attach.query.AttachQuery;
import java.util.List;

public interface AttachRepository {

    AttachDTO insertAttachInfo(AttachParam attachParam);

    List<AttachDTO> getAttachInfo(AttachQuery attachQuery);

    List<AttachDTO> batchGetAttachInfo(List<Integer> ids,Integer type);

    int batchUpdateAttachByIdList(List<AttachUpdateParam> attachUpdateParamList);

    int deleteAttachByBusinessIdAndBusinessType(AttachUpdateParam attachUpdateParam);

    int deleteAttachById(Long id);

    int deleteAttachInfoByParam(AttachQuery attachQuery);

    int deleteAttachDeprecated(AttachDeprecatedQuery query);

    boolean batchUpdateBusiness(List<PicUpdateParam> params);
}
