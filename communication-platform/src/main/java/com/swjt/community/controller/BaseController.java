/**
 * @Type com.swjt.community.controller
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/10/27  19:15
 * @VERSION Version 1.0
 **/

package com.swjt.community.controller;


import com.swjt.community.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Autowired
    HttpServletRequest req;

    @Autowired
    RedisUtil redisUtil;
}
