package com.swjt.community.mapper;

import com.swjt.community.entity.Like;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author DaiRui
 * @since 2021-10-27
 */

@Repository
public interface LikeMapper extends BaseMapper<Like> {
    String deleteByArticleAndUser(String articleId,String userId);
}
