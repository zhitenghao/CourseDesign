package com.swjt.community.mapper;

import com.swjt.community.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

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
public interface ArticleMapper extends BaseMapper<Article> {
    List<Article> listByDate();
    List<Article> listByCategory(@Param("categoryId") String categoryId);
}
