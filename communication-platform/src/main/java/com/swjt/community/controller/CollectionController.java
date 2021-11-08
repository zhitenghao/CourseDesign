package com.swjt.community.controller;


import cn.hutool.core.map.MapUtil;
import com.swjt.community.common.lang.Result;
import com.swjt.community.entity.Collection;
import com.swjt.community.entity.User;
import com.swjt.community.service.CollectionService;
import com.swjt.community.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DaiRui
 * @since 2021-10-27
 */
@RestController
@RequestMapping("/collection")
@Slf4j
@ApiOperation("收藏帖子接口")
public class CollectionController extends BaseController {

    @Autowired
    CollectionService collectionService;

    @Autowired
    UserService userService;


    @PostMapping("/add")
    public Result add(@RequestBody String articleId, Principal principal){
        User userByAccount = userService.getUserByAccount(principal.getName());
        Collection collection = new Collection();
        if(userByAccount==null){
            return Result.fail("收藏失败！");
        }
        collection.setUserId(userByAccount.getId());
        collection.setArticleId(articleId);
        collectionService.save(collection);
        return Result.succ(MapUtil.builder()
                .put("msg","收藏成功！")
                .put("id",collection.getId())
                .map()
        );
    }

    @PostMapping("/delete")
    public Result delete(@RequestBody String id){
        try{
            collectionService.removeById(id);
        }catch (NullPointerException nullPointerException){
            log.error("没有找到收藏帖子的id");
            return Result.fail("取消收藏失败");
        }
        return Result.succ("取消收藏成功！");
    }



}
