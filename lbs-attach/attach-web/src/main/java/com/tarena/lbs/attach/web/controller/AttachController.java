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
package com.tarena.lbs.attach.web.controller;

import com.tarena.lbs.attach.web.service.AttachService;
import com.tarena.lbs.base.protocol.exception.BusinessException;
import com.tarena.lbs.base.protocol.model.Result;
import com.tarena.lbs.common.attach.converter.AttachConverter;
import com.tarena.lbs.common.attach.enums.AttachResultEnum;
import com.tarena.lbs.common.attach.util.FileUtil;
import com.tarena.lbs.pojo.attach.dto.AttachDTO;
import com.tarena.lbs.pojo.attach.vo.FileVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Locale;
import java.util.UUID;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(value = "attach", tags = "附件中心管理")
@Slf4j
@Validated
@RestController
@RequestMapping("attach")
public class AttachController {

    @Resource
    private AttachConverter attachAssemble;

    @Resource
    private AttachService attachService;

    @Value("${image_path}")
    private String imagePath;

    @Value("${url}")
    private String url;

    @PostMapping("upload")
    @ApiOperation(value = "上传附件")
    public Result<FileVO> uploadAttachInfo(@RequestPart("file") MultipartFile file) throws Exception {
        String urlPath = "";
        String fileUuid = UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.ROOT);
        try {
            fileUuid = FileUtil.makeTempFile(file, imagePath,fileUuid);
            urlPath = url + fileUuid;
        } catch (Exception e) {
            throw new BusinessException(AttachResultEnum.FILE_FAIL);
        }
        AttachDTO attachDTO = attachService.insertAttachInfo(file,fileUuid);
        FileVO fileVO = attachAssemble.assembleDtoToFileVo(attachDTO);
        fileVO.setUrl(urlPath);
        return new Result<>(fileVO);
    }

}
