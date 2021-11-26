package com.swjt.community.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swjt.community.common.lang.Result;
import com.swjt.community.entity.Concern;
import com.swjt.community.entity.Message;
import com.swjt.community.entity.User;
import com.swjt.community.service.ConcernService;
import com.swjt.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.swjt.community.controller.BaseController;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DaiRui
 * @since 2021-11-05
 */
@RestController
@RequestMapping("/concern")
public class ConcernController extends BaseController {

    @Autowired
    UserService userService;
    @Autowired
    ConcernService concernService;

    @GetMapping("/add/{id}")
    public Result add(@PathVariable String id, Principal principal){
        User userByAccount = userService.getUserByAccount(principal.getName());
        Concern concern = new Concern();
        concern.setUserId(userByAccount.getId());
        concern.setUseredId(id);
        concernService.save(concern);
        int flag=1;
        if(concernService.getOne(new QueryWrapper<Concern>().eq("user_id",id).eq("usered_id",userByAccount.getId()))!=null){
            flag=2;
        }
        User byId = userService.getById(id);
        byId.setUserConcerned(byId.getUserConcerned()+1);
        userByAccount.setUserConcern(userByAccount.getUserConcern()+1);
        userService.updateById(userByAccount);
        userService.updateById(byId);
        Message message = new Message();
        message.setPrincipleId(userByAccount.getId());
        message.setObjectId(id);
        message.setObjectRead(0);
        message.setAddTime(LocalDateTime.now());
        message.setProcessType(0);
        messageService.save(message);
        return Result.succ(
                MapUtil.builder()
                .put("id",concern.getId())
                .put("isConcerned",flag)
                .map()
        );
    }

    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable String id, Principal principal){
        User userByAccount = userService.getUserByAccount(principal.getName());
        User byId = userService.getById(id);
        Concern concern = concernService.getOne(new QueryWrapper<Concern>().eq("user_id", userByAccount.getId()).eq("usered_id", byId.getId()));
        byId.setUserConcerned(byId.getUserConcerned()-1);
        userByAccount.setUserConcern(userByAccount.getUserConcern()-1);
        userService.updateById(userByAccount);
        userService.updateById(byId);
        messageService.remove(new QueryWrapper<Message>().eq("principle_id",userByAccount.getId()).eq("object_id",byId.getId()));
        concernService.removeById(concern.getId());
        return Result.succ("取消关注成功！");
    }

}
