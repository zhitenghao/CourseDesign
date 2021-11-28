package com.swjt.community.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.swjt.community.common.Dto.ReUserDto;
import com.swjt.community.common.Dto.ReplyDto;
import com.swjt.community.common.lang.Result;
import com.swjt.community.entity.Message;
import com.swjt.community.entity.MessageReply;
import com.swjt.community.entity.Reply;
import com.swjt.community.entity.User;
import com.swjt.community.service.MessageReplyService;
import com.swjt.community.service.ReplyService;
import com.swjt.community.utils.BeanUtils;
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
@RequestMapping("/reply")
public class ReplyController extends BaseController {

    @Autowired
    ReplyService replyService;

    @PostMapping("/add")
    public Result add(@RequestBody ReplyDto replyDto, Principal principal){
        ReUserDto reUserDto = new ReUserDto();
        ReUserDto reUserDto1 = new ReUserDto();
        User byId = userService.getById(replyDto.getReplyedUser());
        BeanUtils.copyPropertiesIgnoreNullValue(byId,reUserDto1);
        User userByAccount = userService.getUserByAccount(principal.getName());
        BeanUtils.copyPropertiesIgnoreNullValue(userByAccount,reUserDto);
        Reply reply = new Reply();
        BeanUtils.copyProperties(replyDto,reply);
        reply.setReplyUser(userByAccount.getId());
        reply.setAddTime(LocalDateTime.now());
        replyService.save(reply);

        Message message = new Message();
        message.setPrincipleId(userByAccount.getId());
        message.setObjectId(byId.getId());
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

        return Result.succ(
                MapUtil.builder()
                .put("id",reply.getId())
                .put("replyContent",reply.getReplyContent())
                .put("replyUser",reUserDto)
                .put("repliedUser",reUserDto1)
                .put("replyTime",reply.getAddTime())
                .map()
        );
    }

    @GetMapping("/list/{commentId}")
    public Result listByCommentId(@PathVariable String commentId){
        return Result.succ(replyService.list(new QueryWrapper<Reply>().eq("comment_id",commentId)));
    }

}
