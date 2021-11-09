package com.swjt.community.controller;


import cn.hutool.core.map.MapUtil;
import com.swjt.community.common.Dto.CommentDto;
import com.swjt.community.common.Dto.ReUserDto;
import com.swjt.community.common.lang.Const;
import com.swjt.community.common.lang.Result;
import com.swjt.community.entity.Article;
import com.swjt.community.entity.Comment;
import com.swjt.community.entity.Message;
import com.swjt.community.entity.User;
import com.swjt.community.service.CommentService;
import com.swjt.community.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/comment")
public class CommentController extends BaseController {
    @Autowired
    CommentService commentService;

    @PreAuthorize("hasRole('normal')")
    @PostMapping("/add")
    public Result add(@RequestBody CommentDto commentDto, Principal principal){
        User userByAccount = userService.getUserByAccount(principal.getName());
        if(userByAccount==null){
            return Result.fail("评论失败！");
        }
        Comment comment = new Comment();
        ReUserDto reUserDto = new ReUserDto();
        BeanUtils.copyPropertiesIgnoreNullValue(userByAccount,reUserDto);
        BeanUtils.copyProperties(commentDto,comment);
        comment.setUserId(userByAccount.getId());
        comment.setAddTime(LocalDateTime.now());
        comment.setCommentStatus(Const.STATUS_ON);
        Article article = articleService.getById(comment.getArticleId());
        article.setArticleComment(article.getArticleComment()+1);
        articleService.updateById(article);
        commentService.save(comment);
        Message message = new Message();
        message.setPrincipleId(userByAccount.getId());
        message.setObjectId(article.getUserId());
        message.setObjectRead(0);
        message.setAddTime(LocalDateTime.now());
        message.setPrincipleRead(0);
        message.setProcessType(2);
        messageService.save(message);
        return Result.succ(
                MapUtil.builder()
                .put("id",comment.getId())
                        .put("commentContent",comment.getCommentContent())
                        .put("commentTime",comment.getAddTime())
                        .put("commentUser",reUserDto)
                        .put("replyList",null)
                        .map()
        );
    }
    @PreAuthorize("hasRole('normal')")
    @PostMapping("/update")
    public Result update(@RequestBody CommentDto commentDto,@RequestBody String commentId){
        Comment comment = commentService.getById(commentId);
        BeanUtils.copyProperties(commentDto,comment);
        comment.setUpdateTime(LocalDateTime.now());
        commentService.updateById(comment);
        return Result.succ(
                MapUtil.builder()
                        .put("id",comment.getId())
        );
    }
}
