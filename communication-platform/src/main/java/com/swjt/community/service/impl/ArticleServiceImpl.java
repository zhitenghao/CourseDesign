package com.swjt.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swjt.community.common.Dto.ReAritcleDto;
import com.swjt.community.common.Dto.ReCommentDto;
import com.swjt.community.common.Dto.ReUserDto;
import com.swjt.community.entity.*;
import com.swjt.community.mapper.ArticleMapper;
import com.swjt.community.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.swjt.community.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author DaiRui
 * @since 2021-10-27
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Autowired
    UserService userService;

    @Autowired
    PhotoService photoService;

    @Autowired
    VideoService videoService;

    @Autowired
    CommentService commentService;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    LoveService likeService;

    @Autowired
    ConcernService concernService;

    @Autowired
    CollectionService collectionService;

    @Autowired
    ArticleCategoryService articleCategoryService;

    @Autowired
    CategoryService categoryService;

    @Override
    public ReAritcleDto ArticleInfoById(String id) {
        ReAritcleDto reAritcleDto = new ReAritcleDto();
        Article article = getById(id);
        User user = userService.getById(article.getUserId());
        ArrayList<String> urls = new ArrayList<>();
        if(article.getIsVideo()==0){
            List<Photo> photos = photoService.list(new QueryWrapper<Photo>().eq("article_id", article.getId()));
            for (Photo photo:photos) {
                urls.add(photo.getPhotoUrl());
            }
        }
        else if(article.getIsVideo()==1){
            Video video = videoService.getOne(new QueryWrapper<Video>().eq("article_id", article.getId()));
            urls.add(video.getVideoUrl());
        }
        reAritcleDto.setUrls(urls);
        ReUserDto reUserDto = new ReUserDto();
        BeanUtils.copyPropertiesIgnoreNullValue(user,reUserDto);
        reAritcleDto.setReUserDto(reUserDto);
        ArrayList<ReCommentDto> reCommentDtos = new ArrayList<>();

        List<Comment> comments = commentService.list(new QueryWrapper<Comment>().eq("article_id", article.getId()));
        for (Comment comment:comments) {
            reCommentDtos.add(commentService.commentInfoById(comment.getId()));
        }
        reAritcleDto.setReCommentDtos(reCommentDtos);
        reAritcleDto.setArticleContent(article.getArticleContent());
        reAritcleDto.setBNum(article.getArticleBnum());
        reAritcleDto.setCollectionNum(article.getArticleCollection());
        reAritcleDto.setLikeNum(article.getArticleLike());
        reAritcleDto.setAddTime(article.getAddTime());
        reAritcleDto.setCommentNum(article.getArticleComment());
        reAritcleDto.setIsVideo(article.getIsVideo());
        article.setArticleBnum(article.getArticleBnum()+1);
        reAritcleDto.setId(id);
        ArticleCategory articleCategory = articleCategoryService.getOne(new QueryWrapper<ArticleCategory>().eq("article_id", id));
        Category category = categoryService.getById(articleCategory.getCategoryId());
        reAritcleDto.setCategory(category);
        //浏览次数加一
//        updateById(article);
        return  reAritcleDto;
    }

    @Override
    public List<Article> listByDate() {
        return articleMapper.listByDate();
    }

    @Override
    public List<Article> listByCategory(String categoryId) {
        return articleMapper.listByCategory(categoryId);
    }

    @Override
    public List<Article> listMySelf(String userId) {
        return articleMapper.selectList(new QueryWrapper<Article>().eq("user_id",userId));
    }

    @Override
    public List<Article> listConcernByUserId(String userId) {
        ArrayList<Article> articles = new ArrayList<>();
        List<Collection> collections = collectionService.list(new QueryWrapper<Collection>().eq("user_id", userId));
        for(Collection collection:collections){
            Article article = getById(collection.getArticleId());
            articles.add(article);
        }
        return articles;
    }

    @Override
    public List<Article> listLikeByUserId(String userId) {
        ArrayList<Article> articles = new ArrayList<>();
        List<Love> loves = likeService.list(new QueryWrapper<Love>().eq("user_id", userId));
        for(Love love:loves){
            Article article = getById(love.getArticleId());
            articles.add(article);
        }
        return articles;
    }

    @Override
    public List<Article> listArticleConcernUserByUserId(String userId) {
        ArrayList<Article> articles = new ArrayList<>();
        List<Concern> concerns = concernService.list(new QueryWrapper<Concern>().eq("user_id", userId));
        for(Concern concern:concerns){
            List<Article> articles1 = listMySelf(concern.getUseredId());
            articles.addAll(articles1);
        }
        return articles;
    }
}
