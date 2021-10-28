package com.swjt.community.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swjt.community.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DaiRui
 * @since 2021-10-27
 */
public interface UserService extends IService<User> {

    User getUserByAccount(String userAccount);

    String getUserAuthorityInfo(String userId);
}
