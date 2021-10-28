/**
 * @Type com.swjt.community.security
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/10/27  21:49
 * @VERSION Version 1.0
 **/

package com.swjt.community.security;

import com.swjt.community.common.exception.CaptchaException;
import com.swjt.community.common.lang.Const;
import com.swjt.community.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CaptchaFilter extends OncePerRequestFilter{

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    LoginFailureHandler loginFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String Url=httpServletRequest.getRequestURI();
        if("/login".equals(Url)  && httpServletRequest.getMethod().equalsIgnoreCase("POST"))
        {
            try{
                validate(httpServletRequest);
            }
            catch (CaptchaException e){
                loginFailureHandler.onAuthenticationFailure(httpServletRequest,httpServletResponse,e);
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    private void validate(HttpServletRequest httpServletRequest){
        String code=httpServletRequest.getParameter("code");
        String key=httpServletRequest.getParameter("token");

        if(StringUtils.isBlank(code) || StringUtils.isBlank(key)){
            throw new CaptchaException("验证码错误");
        }
        if(!code.equals(redisUtil.hget(Const.CAPTCHA_KEY,key))){
            throw new CaptchaException("验证码错误");
        }

        //一次性使用验证码
        redisUtil.hdel(Const.CAPTCHA_KEY,key);
    }
}
