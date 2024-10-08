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
package com.tarena.lbs.attach.web.repository.impl;


import com.tarena.lbs.attach.web.mapper.AttachMapper;
import com.tarena.lbs.attach.web.repository.AttachRepository;
import com.tarena.lbs.common.attach.converter.AttachConverter;
import com.tarena.lbs.common.attach.enums.FileStatusEnum;
import com.tarena.lbs.common.attach.util.FileUtil;
import com.tarena.lbs.pojo.attach.dos.Attach;
import com.tarena.lbs.pojo.attach.dto.AttachDTO;
import com.tarena.lbs.pojo.attach.param.AttachParam;
import com.tarena.lbs.pojo.attach.param.AttachUpdateParam;
import com.tarena.lbs.pojo.attach.param.PicUpdateParam;
import com.tarena.lbs.pojo.attach.query.AttachDeprecatedQuery;
import com.tarena.lbs.pojo.attach.query.AttachQuery;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class AttachRepositoryImpl implements AttachRepository {

    @Value("${image_path}")
    private String imagePath;

    @Resource
    private AttachMapper attachMapper;

    @Resource
    private AttachConverter attachConverter;

    @Override
    public AttachDTO insertAttachInfo(AttachParam attachParam) {
        Attach attach = attachConverter.convertParamToModel(attachParam);
        attachMapper.insertSelective(attach);
        return attachConverter.convertModelToDto(attach);
    }

    @Override
    public List<AttachDTO> getAttachInfo(AttachQuery attachQuery) {
        List<Attach> attachList = attachMapper.getAttachInfo(attachQuery);
        return attachConverter.assembleModelListToDtoList(attachList);
    }

    @Override
    public List<AttachDTO> batchGetAttachInfo(List<Integer> ids,Integer type) {
        List<Attach> attachList = attachMapper.batchGetAttachInfo(ids,type);
        return attachConverter.assembleModelListToDtoList(attachList);
    }

    @Override
    public int batchUpdateAttachByIdList(List<AttachUpdateParam> attachUpdateParamList) {
        return attachMapper.batchUpdateAttachByIdList(attachUpdateParamList);
    }

    @Override
    public int deleteAttachByBusinessIdAndBusinessType(AttachUpdateParam attachUpdateParam) {
        return attachMapper.deleteAttachByBusinessIdAndBusinessType(attachUpdateParam);
    }

    @Override
    public int deleteAttachById(Long id) {
        return attachMapper.deleteAttachById(id);
    }
    @Override
    public int deleteAttachInfoByParam(AttachQuery attachQuery) {
        List<Attach> attachList = attachMapper.getAttachInfo(attachQuery);
        List<Integer> idList = attachList.stream().map(e -> e.getId()).collect(Collectors.toList());
        List<String> filePathList = attachList.stream().map(e -> imagePath + e.getFileUuid()).collect(Collectors.toList());
        FileUtil.deleteFile(filePathList);
        return attachMapper.batchUpdateAttachStatus(FileStatusEnum.UNENABLE.getType(),idList);
    }

    @Override public int deleteAttachDeprecated(AttachDeprecatedQuery query) {
        List<Attach> attachList = attachMapper.getDeprecatedAttachInfo(query);
        if (CollectionUtils.isEmpty(attachList)) {
            return 0;
        }
        List<Integer> idList = attachList.stream().map(e -> e.getId()).collect(Collectors.toList());
        List<String> filePathList = attachList.stream().map(e -> imagePath + e.getFileUuid()).collect(Collectors.toList());
        FileUtil.deleteFile(filePathList);
        return attachMapper.deleteDeprecatedAttaches(idList);
    }

    @Override
    public boolean batchUpdateBusiness(List<PicUpdateParam> params) {
        log.info("绑定图片 执行sql之前:{}",params);
        int rows=attachMapper.batchUpdateAttachByIdOrFileUUID(params);
        log.info("绑定图片 执行sql之后:{}",rows);
        return rows>0;
    }
}
