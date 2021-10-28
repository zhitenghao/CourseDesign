/**
 * @Type com.swjt.community.security
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/10/27  21:26
 * @VERSION Version 1.0
 **/

package com.swjt.community.security;

import cn.hutool.json.JSONUtil;
import com.swjt.community.common.lang.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override

    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        ServletOutputStream outputStream=httpServletResponse.getOutputStream();
        Result result= Result.fail("用户名或密码错误！");

        outputStream.write(JSONUtil.toJsonStr(result).getBytes("utf-8"));

        outputStream.flush();
        outputStream.close();
    }
}
