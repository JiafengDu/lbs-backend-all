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
package com.tarena.lbs.attach.web.rpc;

import com.tarena.lbs.attach.api.AttachApi;
import com.tarena.lbs.attach.web.service.AttachService;
import com.tarena.lbs.pojo.attach.dto.AttachDTO;
import com.tarena.lbs.pojo.attach.dto.AttachQrDTO;
import com.tarena.lbs.pojo.attach.param.AttachQRParam;
import com.tarena.lbs.pojo.attach.param.AttachUpdateParam;
import com.tarena.lbs.pojo.attach.param.PicUpdateParam;
import com.tarena.lbs.pojo.attach.query.AttachQuery;
import java.util.List;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

@DubboService
@Slf4j
public class AttachRpcService implements AttachApi {
    @Resource
    private AttachService attachService;

    @Override
    public boolean batchUpdateBusiness(List<PicUpdateParam> params) {
        return attachService.batchUpdateBusiness(params);
    }

    /**
     * 功能描述: 获取文件地址
     */
    @Override
    public List<AttachDTO> getAttachInfoByParam(AttachQuery attachQuery) {
        return attachService.getAttachInfo(attachQuery);
    }
    /**
     * 功能描述: 批量获取获取文件地址
     */
    @Override
    public List<AttachDTO> batchGetAttachInfo(List<Integer> idList,Integer type) {
        return attachService.batchGetAttachInfo(idList,type);
    }

    /**
     * 功能描述: 批量更新文件封面
     */
    @Override
    public int batchUpdateAttachByIdList(List<AttachUpdateParam> attachUpdateParamList) {
        if (CollectionUtils.isEmpty(attachUpdateParamList)) {
            return 0;
        }
        return attachService.batchUpdateAttachByIdList(attachUpdateParamList);
    }

    @Override
    public int deleteAttachByBusinessIdAndBusinessType(AttachUpdateParam attachUpdateParam) {
        return attachService.deleteAttachByBusinessIdAndBusinessType(attachUpdateParam);
    }

    @Override
    public int deleteAttachById(Long id) {
        return attachService.deleteAttachById(id);
    }
    /**
     * 功能描述: 删除附件
     *
     * @param attachQuery
     */
    @Override
    public int deleteAttachInfoByParam(AttachQuery attachQuery) {
        return attachService.deleteAttachInfoByParam(attachQuery);
    }

    @Value("${url}")
    private String url;
    @Override public AttachQrDTO generateQrCode(AttachQRParam attachQRParam) {
        AttachDTO dto = attachService.generateQrCode(attachQRParam);
        AttachQrDTO attachQrDTO=new AttachQrDTO();
        attachQrDTO.setFileUuid(dto.getFileUuid());
        attachQrDTO.setId(dto.getId());
        attachQrDTO.setUrl(url+dto.getFileUuid());
        return attachQrDTO;
    }
}
