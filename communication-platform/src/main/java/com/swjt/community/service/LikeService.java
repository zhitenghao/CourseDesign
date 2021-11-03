package com.swjt.community.service;

import com.swjt.community.entity.Like;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DaiRui
 * @since 2021-10-27
 */
public interface LikeService extends IService<Like> {

    boolean deleteByArticleAndUser(String articleId,String userId);

}
