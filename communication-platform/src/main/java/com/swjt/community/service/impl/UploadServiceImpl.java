/**
 * @Type com.swjt.community.service.impl
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/11/1  16:41
 * @VERSION Version 1.0
 **/

package com.swjt.community.service.impl;

import cn.hutool.core.lang.UUID;
import com.sun.xml.internal.bind.v2.util.ByteArrayOutputStreamEx;
import com.swjt.community.service.UploadService;
import com.swjt.community.utils.FileUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;


@Component
public class UploadServiceImpl implements UploadService {

    private static String FILE_DIR_LINUX_PHOTO = "/home/community/photo";

    //照片存储位置
    private static String FILE_DIR_WINDOWS_PHOTO = "d:/community/photo";

    //视频存储位置
    private static String FILE_DIR_WINDOWS_VIDEO = "d:/community/video";




    @Override
    public String upload(MultipartFile file, HttpServletRequest request) {
        try {
            String originalFilename = file.getOriginalFilename();
            String fileStoreAddr = "";
            if(FileUtil.isPhotoJudgement(originalFilename)){
                 fileStoreAddr = FILE_DIR_WINDOWS_PHOTO;
            }
            else if(FileUtil.isVideoJudgement(originalFilename)){
                 fileStoreAddr = FILE_DIR_WINDOWS_VIDEO;
            }
            else{
                throw new RuntimeException("上传文件格式错误!");
            }
            String id = UUID.randomUUID().toString();

            String fileName = id + FileUtil.getExtensionWithDot(originalFilename);

            FileUtil.uploadFile(file.getBytes(), fileStoreAddr, fileName);
            //返回重命名后的文件名
            return fileName;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("上传文件失败！");

        }
    }

    @Override
    public byte[] downloadPhoto(String id, HttpServletRequest request) {
        return getBytes(id, FILE_DIR_WINDOWS_PHOTO);
    }

    @Override
    public byte[] downloadVideo(String id, HttpServletRequest request) {
        return getBytes(id, FILE_DIR_WINDOWS_VIDEO);
    }

    private byte[] getBytes(String id, String fileDirWindowsVideo) {
        String fileStoreAddr = fileDirWindowsVideo;

        String filePath  = fileStoreAddr + "/" + id;
        File f  = new File(filePath);

        try {
            if (!f.exists()) {
                // tileset.json未找到
                throw new RuntimeException("没有找到该文件:"+filePath);
            } else {
                FileInputStream inputStream=new FileInputStream(f);
                byte[] b=new byte[inputStream.available()];
                inputStream.read(b);
                return b;
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("读取文件出错");
        }
    }
}
