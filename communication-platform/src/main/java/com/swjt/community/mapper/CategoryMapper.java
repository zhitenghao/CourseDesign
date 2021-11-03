package com.swjt.community.mapper;

import com.swjt.community.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author DaiRui
 * @since 2021-11-01
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {
    List<Category> listByOrder();
}
