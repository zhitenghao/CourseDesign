package com.swjt.community.service;

import com.swjt.community.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author DaiRui
 * @since 2021-11-01
 */
public interface CategoryService extends IService<Category> {
    List<Category> listByOrder();
}
