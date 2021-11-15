package com.swjt.community.service;

import com.swjt.community.common.Dto.ReAritcleDto;
import com.swjt.community.entity.Article;
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
public interface ArticleService extends IService<Article> {

    ReAritcleDto ArticleInfoById(String id);

    List<Article>  listByDate();

    List<Article>  listByCategory(String categoryId);

    List<Article>  listMySelf(String userId);

    List<Article> listConcernByUserId(String userId);

    List<Article> listLikeByUserId(String userId);

    List<Article> listArticleConcernUserByUserId(String userId);
}
