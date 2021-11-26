package com.swjt.community.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swjt.community.common.lang.Result;
import com.swjt.community.entity.*;
import com.swjt.community.service.LoveService;
import com.swjt.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.swjt.community.controller.BaseController;

import java.security.Principal;
import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DaiRui
 * @since 2021-11-08
 */
@RestController
@RequestMapping("/love")
public class LoveController extends BaseController {
    @Autowired
    LoveService likeService;

    @Autowired
    UserService userService;


    @PreAuthorize("hasRole('normal')")
    @GetMapping("/add/{articleId}")
    public Result add(@PathVariable String articleId, Principal principal){
        User userByAccount = userService.getUserByAccount(principal.getName());
        Love like = new Love();
        if(userByAccount==null){
            return Result.fail("点赞失败!");
        }
        Article article = articleService.getById(articleId);
        article.setArticleLike(article.getArticleLike()+1);
        articleService.updateById(article);
        like.setUserId(userByAccount.getId());
        like.setArticleId(articleId);
        like.setMessageId("");
        likeService.save(like);
        Message message = new Message();
        message.setPrincipleId(userByAccount.getId());
        message.setObjectId(article.getUserId());
        message.setObjectRead(0);
        message.setAddTime(LocalDateTime.now());
        //主体点赞了客体
        message.setProcessType(4);
        messageService.save(message);
        MessageArticle messageArticle = new MessageArticle();
        messageArticle.setArticleId(articleId);
        messageArticle.setMessageId(message.getId());
        messageArticleService.save(messageArticle);
        like.setMessageId(message.getId());
        likeService.updateById(like);
        return Result.succ(
                MapUtil.builder()
                        .put("id",like.getId())
                .map()
        );
    }

    @PreAuthorize("hasRole('normal')")
    @GetMapping("/delete/{articleId}")
    public Result delete(@PathVariable String articleId, Principal principal){
        User user = userService.getUserByAccount(principal.getName());
        if(user==null){
            return Result.fail("取消点赞失败！");
        }
        Article article = articleService.getById(articleId);
        article.setArticleLike(article.getArticleLike()-1);
        articleService.updateById(article);
        Love love = likeService.findOneByArticleAndUser(articleId, user.getId());
        messageService.removeById(love.getMessageId());
        messageArticleService.remove(new QueryWrapper<MessageArticle>().eq("article_id", articleId).eq("message_id", love.getMessageId()));
        likeService.removeById(love.getId());
        return Result.succ("成功取消点赞！");
    }

}
