/**
 * @Type com.swjt.community.controller
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/10/27  20:47
 * @VERSION Version 1.0
 **/

package com.swjt.community.controller;


import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.google.code.kaptcha.Producer;
import com.swjt.community.common.lang.Const;
import com.swjt.community.common.lang.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class AuthController extends BaseController {

    @Autowired
    Producer producer;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/captcha")
    public Result captcha() throws IOException {
        String key= UUID.randomUUID().toString();
        String code=producer.createText();
//        key="123";
//        code="123";

        BufferedImage bufferedImage=producer.createImage(code);
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        ImageIO.write(bufferedImage,"jpg",outputStream);

        BASE64Encoder encoder=new BASE64Encoder();
        String str="data:image/jpeg;base64,";
        String base64Img=str+encoder.encode(outputStream.toByteArray());

        redisUtil.hset(Const.CAPTCHA_KEY,key,code,120);

        return Result.succ(
                MapUtil.builder()
                .put("token",key)
                .put("captcha",base64Img)
                .build()
        );
    }


//    @GetMapping("/pwd")
//    public Result pwd(){
//        String name=bCryptPasswordEncoder.encode("1111");
//        return Result.succ(name);
//    }

    @GetMapping("/two")
    public Result two()
    {
        return Result.succ("访问成！");
    }

    @PreAuthorize("hasRole('normal')")
    @GetMapping("/one")
    public Result one(){
        return Result.succ("有权限");
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/three")
    public Result three(){
        return Result.succ("有权限");
    }


    @PreAuthorize("hasAuthority('sys:user:test')")
    @GetMapping("/four")
    public Result four(){
        return Result.succ("菜单有权限");
    }



}
