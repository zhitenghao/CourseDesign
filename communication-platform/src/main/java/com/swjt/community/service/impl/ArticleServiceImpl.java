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
        article.setArticleBnum(article.getArticleBnum()+1);
        //浏览次数加一
        save(article);
        return  reAritcleDto;
    }
}
