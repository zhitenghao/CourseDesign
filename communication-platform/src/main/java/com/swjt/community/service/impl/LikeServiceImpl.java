package com.swjt.community.service.impl;

import com.swjt.community.entity.Like;
import com.swjt.community.mapper.LikeMapper;
import com.swjt.community.mapper.UserMapper;
import com.swjt.community.service.LikeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DaiRui
 * @since 2021-10-27
 */
@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements LikeService {
    @Autowired
    LikeMapper likeMapper;

    @Override
    public boolean deleteByArticleAndUser(String articleId, String userId) {
        likeMapper.deleteByArticleAndUser(articleId,userId);
        return true;
    }
}
