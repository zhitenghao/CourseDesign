package com.swjt.community.service.impl;

import com.swjt.community.entity.Category;
import com.swjt.community.mapper.CategoryMapper;
import com.swjt.community.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DaiRui
 * @since 2021-11-01
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;
    @Override
    public List<Category> listByOrder() {
        return categoryMapper.listByOrder();
    }
}
