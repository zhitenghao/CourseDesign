/**
 * @Type com.swjt.community.controller
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/10/27  19:15
 * @VERSION Version 1.0
 **/

package com.swjt.community.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swjt.community.service.ArticleService;
import com.swjt.community.service.MessageService;
import com.swjt.community.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Autowired
    HttpServletRequest req;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    MessageService messageService;

    @Autowired
    ArticleService articleService;


    /**
     * 获取页面
     * @return
     */
    public Page getPage() {
        int current = ServletRequestUtils.getIntParameter(req, "current", 1);
        int size = ServletRequestUtils.getIntParameter(req, "size", 10);

        return new Page(current, size);
    }

}
