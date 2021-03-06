package com.swjt.community.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swjt.community.common.lang.Result;
import com.swjt.community.entity.*;
import com.swjt.community.service.CollectionService;
import com.swjt.community.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

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


    @GetMapping("/add/{articleId}")
    public Result add(@PathVariable String articleId, Principal principal){
        User userByAccount = userService.getUserByAccount(principal.getName());
        Article byId = articleService.getById(articleId);
        User byId1 = userService.getById(byId.getUserId());
        Collection collection = new Collection();
        if(userByAccount==null){
            return Result.fail("收藏失败！");
        }
        byId.setArticleCollection(byId.getArticleCollection()+1);
        collection.setUserId(userByAccount.getId());
        collection.setArticleId(articleId);
        collection.setMessageId("");
        collectionService.save(collection);
        Message message = new Message();
        message.setPrincipleId(userByAccount.getId());
        message.setObjectId(byId1.getId());
        message.setObjectRead(0);
        message.setProcessType(1);
        message.setAddTime(LocalDateTime.now());
        messageService.save(message);
        MessageArticle messageArticle = new MessageArticle();
        messageArticle.setArticleId(articleId);
        messageArticle.setMessageId(message.getId());
        messageArticleService.save(messageArticle);
        collection.setMessageId(message.getId());
        collectionService.updateById(collection);
        articleService.updateById(byId);
        return Result.succ(MapUtil.builder()
                .put("msg","收藏成功！")
                .put("id",collection.getId())
                .map()
        );
    }

    @GetMapping("/delete/{id}")
    public Result delete(@PathVariable String id,Principal principal){
        try{
            User userByAccount = userService.getUserByAccount(principal.getName());
            Article article = articleService.getById(id);
            article.setArticleCollection(article.getArticleCollection()-1);
            articleService.updateById(article);
            Collection collection = collectionService.getOne(new QueryWrapper<Collection>().eq("user_id", userByAccount.getId()).eq("article_id", article.getId()));
            messageService.removeById(collection.getMessageId());
            messageArticleService.remove(new QueryWrapper<MessageArticle>().eq("article_id", id).eq("message_id", collection.getMessageId()));
            collectionService.removeById(collection.getId());
        }catch (NullPointerException nullPointerException){
            log.error("没有找到收藏帖子的id");
            return Result.fail("取消收藏失败");
        }
        return Result.succ("取消收藏成功！");
    }


}
