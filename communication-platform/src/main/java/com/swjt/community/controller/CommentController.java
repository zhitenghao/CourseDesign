package com.swjt.community.controller;


import cn.hutool.core.map.MapUtil;
import com.swjt.community.common.Dto.CommentDto;
import com.swjt.community.common.lang.Result;
import com.swjt.community.entity.Comment;
import com.swjt.community.service.CommentService;
import com.swjt.community.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
    public Result add(@RequestBody CommentDto commentDto){
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentDto,comment);
        comment.setAddTime(LocalDateTime.now());
        commentService.save(comment);
        return Result.succ(
                MapUtil.builder()
                .put("id",comment.getId())
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
