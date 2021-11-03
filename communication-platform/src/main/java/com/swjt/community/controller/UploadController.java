/**
 * @Type com.swjt.community.controller
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/11/1  17:44
 * @VERSION Version 1.0
 **/

package com.swjt.community.controller;

import cn.hutool.core.map.MapUtil;
import com.swjt.community.common.lang.Result;
import com.swjt.community.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;


@RestController
@Api("文件传输")
public class UploadController {

    @Autowired
    private UploadService uploadServiceImpl;

    @ApiOperation(value = "文件上传,返回的id是存储成功后图片/视频的id")
    @CrossOrigin
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Result upload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        String id=uploadServiceImpl.upload(file,request);
        return Result.succ(
                MapUtil.builder()
                .put("msg","存储成功")
                .put("id",id)
                .map()
        );
    }

    @ApiOperation(value = "图片文件下载")
    @ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "图片id", required = true, paramType = "path") })
    @CrossOrigin
    @RequestMapping(value = "/downloadPhoto/{id}", method = RequestMethod.GET)
    public Result downloadPhoto(@PathVariable(value = "id") String id, HttpServletRequest request) {
        byte[] bytes=uploadServiceImpl.downloadPhoto(id, request);
        BASE64Encoder encoder=new BASE64Encoder();
        String str="data:image/jpeg;base64,";
        String base64Img=str+encoder.encode(bytes);
        return Result.succ(
                MapUtil.builder()
                .put("imgSrc",base64Img)
                .map()
        );
    }

    @ApiOperation(value = "视频文件下载")
    @ApiImplicitParams({ @ApiImplicitParam(name = "id", value = "视频id", required = true, paramType = "path") })
    @CrossOrigin
    @RequestMapping(value = "/downloadVideo/{id}", method = RequestMethod.GET)
    public Result downloadVideo(@PathVariable(value = "id") String id, HttpServletRequest request) {
        byte[] bytes=uploadServiceImpl.downloadVideo(id, request);
        BASE64Encoder encoder=new BASE64Encoder();
        String str="data:image/jpeg;base64,";
        String base64Img=str+encoder.encode(bytes);
        return Result.succ(
                MapUtil.builder()
                .put("imgSrc",base64Img)
                .map()
        );
    }

}
