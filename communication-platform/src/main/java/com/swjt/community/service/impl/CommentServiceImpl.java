package com.swjt.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swjt.community.common.Dto.ReCommentDto;
import com.swjt.community.common.Dto.ReReplyDto;
import com.swjt.community.common.Dto.ReUserDto;
import com.swjt.community.entity.Comment;
import com.swjt.community.entity.Reply;
import com.swjt.community.mapper.CommentMapper;
import com.swjt.community.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swjt.community.service.ReplyService;
import com.swjt.community.service.UserService;
import com.swjt.community.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DaiRui
 * @since 2021-10-27
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    ReplyService replyService;
    @Autowired
    UserService userService;
    @Override
    public ReCommentDto commentInfoById(String id) {
        Comment comment = getById(id);
        ArrayList<ReReplyDto> reReplyDtos = new ArrayList<>();
        ReCommentDto reCommentDto = new ReCommentDto();
        List<Reply> replies = replyService.list(new QueryWrapper<Reply>().eq("comment_id", comment.getId()));
        for (Reply reply:replies) {
            ReReplyDto reReplyDto = new ReReplyDto();
            ReUserDto reUserDto1 = new ReUserDto();
            ReUserDto reUserDto = new ReUserDto();
            if(reply.getUpdateTime()==null){
                reReplyDto.setReplyTime(reply.getAddTime());
            }
            else if(reply.getUpdateTime()!=null){
                reReplyDto.setReplyTime(reply.getUpdateTime());
            }
            BeanUtils.copyPropertiesIgnoreNullValue(userService.getById(reply.getReplyedUser()),reUserDto);
            reReplyDto.setReplyedUser(reUserDto);
            BeanUtils.copyPropertiesIgnoreNullValue(userService.getById(reply.getReplyUser()),reUserDto1);
            reReplyDto.setReplyUser(reUserDto1);
            reReplyDto.setReplyContent(reply.getReplyContent());
            reReplyDtos.add(reReplyDto);
        }
        reCommentDto.setReplyList(reReplyDtos);
        reCommentDto.setCommentContent(comment.getCommentContent());
        ReUserDto reUserDto2=new ReUserDto();
        BeanUtils.copyPropertiesIgnoreNullValue(userService.getById(comment.getUserId()),reUserDto2);
        reCommentDto.setCommentUser(reUserDto2);
        if(comment.getUpdateTime()==null){
            reCommentDto.setCommentTime(comment.getAddTime());
        }
        else if(comment.getUpdateTime()!=null){
            reCommentDto.setCommentTime(comment.getUpdateTime());
        }
        return reCommentDto;
    }
}
