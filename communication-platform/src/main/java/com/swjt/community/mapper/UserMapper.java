package com.swjt.community.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.swjt.community.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author DaiRui
 * @since 2021-10-27
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    List<String> getNavMenuIds(String userId);

    List<User> listByMenuId(String menuId);

    Page<User> concernUserList(String id, Page page);

    Page<User> fansUserList(String id, Page page);
}
