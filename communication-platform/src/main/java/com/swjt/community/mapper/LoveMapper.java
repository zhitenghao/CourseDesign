package com.swjt.community.mapper;

import com.swjt.community.entity.Love;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author DaiRui
 * @since 2021-11-08
 */
@Repository
public interface LoveMapper extends BaseMapper<Love> {
    Love findOneByArticleAndUser(String articleId,String userId);
}
