package com.swjt.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swjt.community.entity.User;
import com.swjt.community.entity.Video;
import com.swjt.community.mapper.VideoMapper;
import com.swjt.community.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {
    @Override
    public Video findOneVideo(String userId) {
        return getOne(new QueryWrapper<Video>().eq("user_id", userId));
    }
}
