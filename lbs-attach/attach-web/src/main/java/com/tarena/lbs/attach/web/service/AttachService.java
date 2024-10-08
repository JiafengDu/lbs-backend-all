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
package com.tarena.lbs.attach.web.service;

import com.tarena.lbs.attach.web.repository.AttachRepository;
import com.tarena.lbs.common.attach.enums.FileTypeEnum;
import com.tarena.lbs.common.attach.util.QRCodeUtil;
import com.tarena.lbs.pojo.attach.dto.AttachDTO;
import com.tarena.lbs.pojo.attach.param.AttachParam;
import com.tarena.lbs.pojo.attach.param.AttachQRParam;
import com.tarena.lbs.pojo.attach.param.AttachUpdateParam;
import com.tarena.lbs.pojo.attach.param.PicUpdateParam;
import com.tarena.lbs.pojo.attach.query.AttachDeprecatedQuery;
import com.tarena.lbs.pojo.attach.query.AttachQuery;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class AttachService {

    @Resource
    private AttachRepository attachRepository;

    private Integer getFileType(MultipartFile file) {
        Integer fileType = FileTypeEnum.UNKNOWN.getType();
        if (file.getContentType().contains("video")) {
            fileType = FileTypeEnum.VIDEO.getType();
        } else if (file.getContentType().contains("image")) {
            fileType = FileTypeEnum.PICTURE.getType();
        }
        return fileType;
    }

    /**
     * 功能描述: 上传附件
     */
    public AttachDTO insertAttachInfo(MultipartFile file, String fileUuid) throws Exception {
        AttachParam attachParam = new AttachParam();
        attachParam.setClientFileName(file.getOriginalFilename());
        attachParam.setFileUuid(fileUuid);
        attachParam.setContentType(getFileType(file));
        attachParam.setContentLength(file.getSize() / 1024);
        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        attachParam.setWidth(bufferedImage.getWidth());
        attachParam.setHeight(bufferedImage.getHeight());
        return attachRepository.insertAttachInfo(attachParam);
    }

    /**
     * 功能描述: 获取附件URL
     */
    public List<AttachDTO> getAttachInfo(AttachQuery attachQuery) {
        return attachRepository.getAttachInfo(attachQuery);
    }

    /**
     * 功能描述: RPC接口更新businessId和封面图
     */
    public int batchUpdateAttachByIdList(List<AttachUpdateParam> attachUpdateParamList) {
        return attachRepository.batchUpdateAttachByIdList(attachUpdateParamList);
    }

    /**
     * 删除
     * @param attachUpdateParam
     * @return
     * todo 物理文件没删
     */
    public int deleteAttachByBusinessIdAndBusinessType(AttachUpdateParam attachUpdateParam) {
        return attachRepository.deleteAttachByBusinessIdAndBusinessType(attachUpdateParam);
    }

    public int deleteAttachById(Long id) {
        return attachRepository.deleteAttachById(id);
    }

    /**
    * 功能描述: 删除附件URL
     */
    public int deleteAttachInfoByParam(AttachQuery attachQuery) {
        return attachRepository.deleteAttachInfoByParam(attachQuery);
    }

    @Value("${image_path}")
    private String imagePath;
    public AttachDTO generateQrCode(AttachQRParam param) {
        //生成二维码
        UUID uuid = UUID.randomUUID();
        String qrFileName = uuid.toString().replace("-", "")+".png";
        QRCodeUtil.createCodeToFile(param.getContent(), new File(imagePath), qrFileName);
        //存储图片信息
        AttachParam attachParam=new AttachParam();
        attachParam.setClientFileName(qrFileName);
        attachParam.setFileUuid(qrFileName);
        attachParam.setBusinessId(param.getBusinessId());
        attachParam.setBusinessType(param.getBusinessType());
        attachParam.setContentType(FileTypeEnum.PICTURE.getType());
        attachParam.setIsCover(0);
        attachParam.setContentLength(new File(imagePath+qrFileName).length()/1024);
        attachParam.setWidth(400);
        attachParam.setHeight(400);
        return attachRepository.insertAttachInfo(attachParam);
    }

    public int deleteAttachDeprecated(AttachDeprecatedQuery attachDeprecatedQuery){
        return attachRepository.deleteAttachDeprecated(attachDeprecatedQuery);
    }

    /**
     * 功能描述: 批量获取某种类型的附件URL
     */
    public List<AttachDTO> batchGetAttachInfo(List<Integer> ids,Integer type) {
        return attachRepository.batchGetAttachInfo(ids,type);
    }

    public boolean batchUpdateBusiness(List<PicUpdateParam> params) {
        if (CollectionUtils.isEmpty(params)){
            return false;
        }else{
            return attachRepository.batchUpdateBusiness(params);
        }
    }
}
