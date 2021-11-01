/**
 * @Type com.swjt.community.service
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/11/1  16:34
 * @VERSION Version 1.0
 **/


package com.swjt.community.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface UploadService {


    /**
     *
     * @param file
     * @param request
     * @return 上传文件ID
     */
    String upload(MultipartFile file, HttpServletRequest request);

    /**
     *
     * @param id
     * @param request
     * @return
     */
    byte[] downloadPhoto(String id, HttpServletRequest request);

    byte[] downloadVideo(String id, HttpServletRequest request);
}
