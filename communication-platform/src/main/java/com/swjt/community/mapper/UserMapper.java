package com.swjt.community.mapper;

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
}
