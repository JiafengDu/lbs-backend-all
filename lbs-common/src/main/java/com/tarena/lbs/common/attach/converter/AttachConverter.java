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
package com.tarena.lbs.common.attach.converter;

import com.tarena.lbs.common.attach.enums.FileStatusEnum;
import com.tarena.lbs.pojo.attach.dos.Attach;
import com.tarena.lbs.pojo.attach.dto.AttachDTO;
import com.tarena.lbs.pojo.attach.param.AttachParam;
import com.tarena.lbs.pojo.attach.vo.AttachVO;
import com.tarena.lbs.pojo.attach.vo.FileVO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AttachConverter {

    public AttachVO assembleDtoToVo(AttachDTO attachDTO) {
        if (attachDTO == null) {
            return null;
        }
        AttachVO attachVO = new AttachVO();
        BeanUtils.copyProperties(attachDTO,attachVO);
        return attachVO;
    }

    public List<AttachVO> assembleDtoListToVoList(List<AttachDTO> sources) {
        if (sources == null) {
            return null;
        }
        List<AttachVO> objects = new ArrayList<>();
        for (AttachDTO source : sources) {
            AttachVO object = new AttachVO();
            BeanUtils.copyProperties(source,object);
            objects.add(object);
        }
        return objects;
    }

    public FileVO assembleDtoToFileVo(AttachDTO attachDTO) {
        if (attachDTO == null) {
            return null;
        }
        FileVO fileVO = new FileVO();
        BeanUtils.copyProperties(attachDTO,fileVO);
        return fileVO;
    }

    public Attach convertParamToModel(AttachParam attachParam) {
        if (attachParam == null) {
            return null;
        }
        Attach attach = new Attach();
        BeanUtils.copyProperties(attachParam,attach);
        attach.setDownloadTimes(0);
        attach.setRemark("");
        attach.setStatus(FileStatusEnum.ENABLE.getType());
        attach.setIsCover(0);
        attach.setBusinessId(attachParam.getBusinessId() == null ? 0 : attachParam.getBusinessId());
        attach.setBusinessType(attachParam.getBusinessType() == null ? 0 : attachParam.getBusinessType());
        attach.setGmtCreate(System.currentTimeMillis());
        attach.setGmtModified(System.currentTimeMillis());
        return attach;
    }

    public AttachDTO convertModelToDto(Attach source) {
        if (source == null) {
            return null;
        }
        AttachDTO object = new AttachDTO();
        BeanUtils.copyProperties(source,object);
        return object;
    }

    public List<AttachDTO> assembleModelListToDtoList(List<Attach> sources) {
        if (sources == null) {
            return null;
        }
        List<AttachDTO> objects = new ArrayList<>();
        for (Attach source : sources) {
            AttachDTO object = new AttachDTO();
            BeanUtils.copyProperties(source,object);
            objects.add(object);
        }
        return objects;
    }
}
