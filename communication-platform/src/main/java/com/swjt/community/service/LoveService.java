package com.swjt.community.service;

import com.swjt.community.entity.Love;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DaiRui
 * @since 2021-11-08
 */
public interface LoveService extends IService<Love> {
    Love findOneByArticleAndUser(String articleId,String userId);
}
