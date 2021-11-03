package com.swjt.community.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import com.swjt.community.common.Dto.ReplyDto;
import com.swjt.community.common.lang.Result;
import com.swjt.community.entity.Reply;
import com.swjt.community.service.ReplyService;
import com.swjt.community.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result add(@RequestBody ReplyDto replyDto){
        Reply reply = new Reply();
        BeanUtils.copyProperties(replyDto,reply);
        reply.setAddTime(LocalDateTime.now());
        replyService.save(reply);
        return Result.succ(
                MapUtil.builder()
                .put("id",reply.getId())
        );
    }

    @GetMapping("/list/{commentId}")
    public Result listByCommentId(@PathVariable String commentId){
        return Result.succ(replyService.list(new QueryWrapper<Reply>().eq("comment_id",commentId)));
    }

}
