/**
 * @Type com.swjt.community.controller
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/10/27  19:18
 * @VERSION Version 1.0
 **/

package com.swjt.community.controller;


import com.swjt.community.common.lang.Result;
import com.swjt.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/test")
    public Result test(){
        return Result.succ(userService.list());
    }
}
