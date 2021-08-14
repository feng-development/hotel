package com.feng.hotel.controller;

import com.feng.hotel.base.exception.BizException;
import com.feng.hotel.base.exception.BizInfo;
import com.feng.hotel.common.enums.HotelEnum;
import com.feng.hotel.utils.GlobalUtils;
import com.feng.hotel.utils.IdWorkerUtils;

import java.io.File;
import java.io.IOException;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Administrator
 * @since 2021/8/13
 */
@RestController()
@RequestMapping(value = "/file")
@Valid
public class FileController {


    @PostMapping(value = "/upload")
    public String upload(@RequestParam(value = "file") MultipartFile multipartFile) throws IOException {
        String fileName = multipartFile.getOriginalFilename();

        File file = new File(System.getProperty("user.dir").concat("/image/").concat(IdWorkerUtils.generateStringId())
            .concat(GlobalUtils.getFileExt(fileName)));
        if (!file.exists()) {
            if (!file.mkdirs()) {
                throw new BizException(BizInfo.FILE_UPLOAD_ERROR);
            }
            ;
        }

        multipartFile.transferTo(file);
        return file.getCanonicalPath();
    }
}
