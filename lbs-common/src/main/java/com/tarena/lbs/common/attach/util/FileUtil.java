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
package com.tarena.lbs.common.attach.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

    public static String makeTempFile(MultipartFile file, String path, String fileUuid) throws Exception {
        File dir = new File(path);
        if (!dir.getParentFile().exists()) {
            dir.getParentFile().mkdirs();
        }
        //此处是将文本+图片原文件名作为图片的新文件名
        String fileSuffix = file.getOriginalFilename().substring(0, file.getOriginalFilename().indexOf("."));
        String fileName = fileUuid + "" + file.getOriginalFilename().substring(fileSuffix.length());
        File filePath = new File(path, fileName);
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }
        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(filePath));
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (Exception e) {

        } finally {
            if (out != null) {
                out.close();
            }
        }
        return fileName;
    }

    public static void deleteFile(List<String> filePathList) {
        for (String filePath : filePathList) {
            File file = new File(filePath);
            file.delete();
        }
    }
}
