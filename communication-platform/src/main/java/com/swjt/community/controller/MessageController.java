package com.swjt.community.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swjt.community.common.Dto.ReMessageArticleDto;
import com.swjt.community.common.Dto.ReMessageDto;
import com.swjt.community.common.Dto.ReUserDto;
import com.swjt.community.common.lang.Result;
import com.swjt.community.entity.Message;
import com.swjt.community.entity.MessageArticle;
import com.swjt.community.entity.User;
import com.swjt.community.utils.BeanUtils;
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
}
