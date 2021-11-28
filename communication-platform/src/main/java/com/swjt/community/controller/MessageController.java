package com.swjt.community.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swjt.community.common.Dto.ReMessageArticleDto;
import com.swjt.community.common.Dto.ReMessageDto;
import com.swjt.community.common.Dto.ReMessageReplyDto;
import com.swjt.community.common.Dto.ReUserDto;
import com.swjt.community.common.lang.Result;
import com.swjt.community.entity.*;
import com.swjt.community.service.CommentService;
import com.swjt.community.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.swjt.community.controller.BaseController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DaiRui
 * @since 2021-11-05
 */
@RestController
@RequestMapping("/message")
public class MessageController extends BaseController {


    @PreAuthorize("hasRole('normal')")
    @GetMapping("/loves")
    public Result loveMessage(Principal principal){
        User userByAccount = userService.getUserByAccount(principal.getName());
        List<Message> messages = messageService.list(new QueryWrapper<Message>().eq("object_id", userByAccount.getId()).eq("process_type", 4));
        ArrayList<ReMessageArticleDto> reMessageArticleDtos = new ArrayList<>();
        for(Message message:messages){
            ReMessageArticleDto reMessageArticleDto = new ReMessageArticleDto();
            ReUserDto reUserDto = new ReUserDto();
            reMessageArticleDto.setMessageId(message.getId());
            BeanUtils.copyProperties(message,reMessageArticleDto);
            User user = userService.getById(message.getPrincipleId());
            BeanUtils.copyProperties(user,reUserDto);
            reMessageArticleDto.setReUserDto(reUserDto);
            reMessageArticleDto.setArticleId(messageArticleService.getOne(new QueryWrapper<MessageArticle>().eq("message_id",message.getId())).getArticleId());
            reMessageArticleDtos.add(reMessageArticleDto);
        }
        return Result.succ(reMessageArticleDtos);
    }

    @PreAuthorize("hasRole('normal')")
    @GetMapping("/collections")
    public Result collectionMessage(Principal principal){
        User userByAccount = userService.getUserByAccount(principal.getName());
        List<Message> messages = messageService.list(new QueryWrapper<Message>().eq("object_id", userByAccount.getId()).eq("process_type", 1));
        ArrayList<ReMessageArticleDto> reMessageArticleDtos = new ArrayList<>();
        for(Message message:messages){
            ReMessageArticleDto reMessageArticleDto = new ReMessageArticleDto();
            ReUserDto reUserDto = new ReUserDto();
            reMessageArticleDto.setMessageId(message.getId());
            BeanUtils.copyProperties(message,reMessageArticleDto);
            User user = userService.getById(message.getPrincipleId());
            BeanUtils.copyProperties(user,reUserDto);
            reMessageArticleDto.setReUserDto(reUserDto);
            reMessageArticleDto.setArticleId(messageArticleService.getOne(new QueryWrapper<MessageArticle>().eq("message_id",message.getId())).getArticleId());
            reMessageArticleDtos.add(reMessageArticleDto);
        }
        return Result.succ(reMessageArticleDtos);
    }

    @PreAuthorize("hasRole('normal')")
    @GetMapping("/concerns")
    public Result concernMessage(Principal principal){
        User userByAccount = userService.getUserByAccount(principal.getName());
        List<Message> messages = messageService.list(new QueryWrapper<Message>().eq("object_id", userByAccount.getId()).eq("process_type", 0));
        ArrayList<ReMessageDto> reMessageDtos = new ArrayList<>();
        for(Message message:messages){
            ReMessageDto reMessageDto = new ReMessageDto();
            ReUserDto reUserDto = new ReUserDto();
            reMessageDto.setMessageId(message.getId());
            BeanUtils.copyProperties(message,reMessageDto);
            User user = userService.getById(message.getPrincipleId());
            BeanUtils.copyProperties(user,reUserDto);
            reMessageDto.setReUserDto(reUserDto);
            reMessageDtos.add(reMessageDto);
        }
        return Result.succ(reMessageDtos);
    }
    @PreAuthorize("hasRole('normal')")
    @GetMapping("/replies")
    public Result replyMessage(Principal principal){
        User userByAccount = userService.getUserByAccount(principal.getName());
        List<Message> messages = messageService.list(new QueryWrapper<Message>().eq("object_id", userByAccount.getId()).between("process_type",2 ,3));
        ArrayList<ReMessageReplyDto> reMessageReplyDtos = new ArrayList<>();
        for(Message message:messages){
            ReMessageReplyDto reMessageReplyDto = new ReMessageReplyDto();
            ReUserDto reUserDto = new ReUserDto();
            reMessageReplyDto.setMessageId(message.getId());
            BeanUtils.copyProperties(message,reMessageReplyDto);
            User user = userService.getById(message.getPrincipleId());
            BeanUtils.copyProperties(user,reUserDto);
            reMessageReplyDto.setReUserDto(reUserDto);
            if(message.getProcessType()==2){
                MessageComment messageComment = messageCommentService.getOne(new QueryWrapper<MessageComment>().eq("message_id", message.getId()));
                Comment comment = commentService.getById(messageComment.getCommentId());
                reMessageReplyDto.setContent(comment.getCommentContent());
                reMessageReplyDto.setArticleId(comment.getArticleId());
                reMessageReplyDto.setComReId(comment.getId());
            }
            else if(message.getProcessType()==3){
                MessageReply messageReply = messageReplyService.getOne(new QueryWrapper<MessageReply>().eq("message_id", message.getId()));
                Reply reply = replyService.getById(messageReply.getReplyId());
                Comment comment = commentService.getById(reply.getCommentId());
                reMessageReplyDto.setArticleId(comment.getArticleId());
                reMessageReplyDto.setContent(reply.getReplyContent());
                reMessageReplyDto.setComReId(reply.getId());
            }
            else{
                continue;
            }
                reMessageReplyDtos.add(reMessageReplyDto);
        }
        return Result.succ(reMessageReplyDtos);
    }

}
