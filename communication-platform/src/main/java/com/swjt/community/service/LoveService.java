package com.swjt.community.service;

import com.swjt.community.entity.Love;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DaiRui
 * @since 2021-11-08
 */
public interface LoveService extends IService<Love> {
    boolean deleteByArticleAndUser(String articleId,String userId);
}
