package com.swjt.community.service.impl;

import com.swjt.community.entity.Love;
import com.swjt.community.mapper.LoveMapper;
import com.swjt.community.service.LoveService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DaiRui
 * @since 2021-11-08
 */
@Service
public class LoveServiceImpl extends ServiceImpl<LoveMapper, Love> implements LoveService {
    @Autowired
    LoveMapper likeMapper;

    @Override
    public Love findOneByArticleAndUser(String articleId, String userId) {
        return likeMapper.findOneByArticleAndUser(articleId,userId);
    }
}
