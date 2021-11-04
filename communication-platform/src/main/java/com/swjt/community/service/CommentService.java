package com.swjt.community.service;

import com.swjt.community.common.Dto.ReCommentDto;
import com.swjt.community.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DaiRui
 * @since 2021-10-27
 */
public interface CommentService extends IService<Comment> {
    ReCommentDto commentInfoById(String id);
}
