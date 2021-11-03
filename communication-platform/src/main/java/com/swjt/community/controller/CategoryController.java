package com.swjt.community.controller;


import cn.hutool.core.map.MapUtil;
import com.swjt.community.common.Dto.CategoryDto;
import com.swjt.community.common.lang.Result;
import com.swjt.community.entity.Category;
import com.swjt.community.service.CategoryService;
import com.swjt.community.utils.BeanUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.swjt.community.controller.BaseController;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DaiRui
 * @since 2021-11-01
 */
@Api("分类————————接口")
@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController {


    @Autowired
    CategoryService categoryService;

    @PreAuthorize("hasRole('normal')")
    @GetMapping("/list")
    public Result list(){
        return Result.succ(categoryService.listByOrder());
    }

    @PreAuthorize("hasRole('admin')")
    @PostMapping("/addCategory")
    public Result addCategory(@RequestBody CategoryDto categoryDto){
        Category category=new Category();
        category.setAddTime(LocalDateTime.now());
        BeanUtils.copyProperties(categoryDto,category);
        categoryService.save(category);
        return Result.succ(
                MapUtil.builder()
                .put("msg","添加分类成功")
                .put("id",category.getId())
                .map()
        );
    }
    @PreAuthorize("hasRole('admin')")
    @GetMapping("/deleteCategory/{id}")
    public Result deleteCategory(@PathVariable String id){
        categoryService.removeById(id);
        return Result.succ(
                MapUtil.builder()
                .put("id",id)
                .map()
        );
    }

    @PostMapping("/updateCategory")
    public Result updateUser(@RequestBody Category category){
        category.setUpdateTime(LocalDateTime.now());
        categoryService.updateById(category);
        return Result.succ("修改信息成功！");
    }

    @GetMapping("/categoryInfo/{id}")
    public Result categoryInfo(@PathVariable String id){
        return Result.succ(categoryService.getById(id));
    }

}
