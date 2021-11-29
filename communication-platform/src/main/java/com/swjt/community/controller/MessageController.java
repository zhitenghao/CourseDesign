package com.swjt.community.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swjt.community.common.Dto.*;
import com.swjt.community.common.lang.Result;
import com.swjt.community.entity.*;
import com.swjt.community.service.CommentService;
import com.swjt.community.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.swjt.community.controller.BaseController;

import java.security.Principal;
import java.time.LocalDateTime;
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
            if(reUserDto!=null){
                if (reUserDto.getId().equals(userByAccount.getId())){
                    reMessageArticleDto.setMyself(true);
                }
                else
                    reMessageArticleDto.setMyself(false);
            }
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
            if(reUserDto!=null){
                if (reUserDto.getId().equals(userByAccount.getId())){
                    reMessageArticleDto.setMyself(true);
                }
                else
                    reMessageArticleDto.setMyself(false);
            }
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
            if(reUserDto!=null){
                if (reUserDto.getId().equals(userByAccount.getId())){
                    reMessageDto.setMyself(true);
                }
                else
                    reMessageDto.setMyself(false);
            }
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
            if(reUserDto!=null){
                if (reUserDto.getId().equals(userByAccount.getId())){
                    reMessageReplyDto.setMyself(true);
                }
                else
                    reMessageReplyDto.setMyself(false);
            }
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

    @PreAuthorize("hasRole('normal')")
    @PostMapping("/addReply")
    public Result addReply(@RequestBody MessageReplyDto messageReplyDto, Principal principal){
        User user = userService.getById(messageReplyDto.getRepliedUser());
        User userByAccount = userService.getUserByAccount(principal.getName());

        Reply reply = new Reply();
        BeanUtils.copyProperties(messageReplyDto,reply);
        reply.setReplyUser(userByAccount.getId());
        reply.setAddTime(LocalDateTime.now());
        if(messageReplyDto.getProcessType()==2){
            reply.setCommentId(messageReplyDto.getComReId());
        }
        else if(messageReplyDto.getProcessType()==3){
            Reply reply1 = replyService.getById(messageReplyDto.getComReId());
            reply.setCommentId(reply1.getCommentId());
        }
        else{
            return Result.fail("回复失败");
        }
        replyService.save(reply);


        Message message = new Message();
        message.setPrincipleId(userByAccount.getId());
        message.setObjectId(user.getId());
        message.setObjectRead(0);
        message.setAddTime(LocalDateTime.now());
        message.setProcessType(3);
        messageService.save(message);
        reply.setMessageId(message.getId());
        replyService.updateById(reply);

        MessageReply messageReply = new MessageReply();
        messageReply.setMessageId(message.getId());
        messageReply.setReplyId(reply.getId());
        messageReplyService.save(messageReply);

        return Result.succ("回复成功！");
    }

}
