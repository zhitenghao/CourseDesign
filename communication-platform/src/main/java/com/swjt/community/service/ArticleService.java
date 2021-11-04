package com.swjt.community.service;

import com.swjt.community.common.Dto.ReAritcleDto;
import com.swjt.community.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
