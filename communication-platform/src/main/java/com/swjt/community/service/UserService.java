package com.swjt.community.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swjt.community.common.Dto.ExtendReUserDto;
import com.swjt.community.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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

    void clearUserAuthorityInfo(String userAccount);

    void clearUserAuthorityInfoByRoleId(String roleid);

    void clearUserAuthorityInfoByMenuId(String menuid);

    List<User> userList();

    Page<ExtendReUserDto> concernUserList(String id,String myId, Page page);

    Page<ExtendReUserDto> fansUserList(String id, String myId,Page page);

}
