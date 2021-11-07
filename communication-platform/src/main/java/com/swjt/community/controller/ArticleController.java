package com.swjt.community.controller;


import cn.hutool.core.map.MapUtil;
import com.swjt.community.common.Dto.ReAritcleDto;
import com.swjt.community.common.Dto.ArticleDto;
import com.swjt.community.common.lang.Const;
import com.swjt.community.common.lang.Result;
import com.swjt.community.entity.Article;
import com.swjt.community.entity.Photo;
import com.swjt.community.entity.User;
import com.swjt.community.entity.Video;
import com.swjt.community.service.ArticleService;
import com.swjt.community.service.PhotoService;
import com.swjt.community.service.UserService;
import com.swjt.community.service.VideoService;
import com.swjt.community.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author DaiRui
 * @since 2021-10-27
 */
@RestController
@RequestMapping("/article")
public class ArticleController extends BaseController {
    @Autowired
    ArticleService articleService;

    @Autowired
    VideoService videoService;

    @Autowired
    PhotoService photoService;

    @Autowired
    UserService userService;

    @PreAuthorize("hasRole('normal')")
    @PostMapping("/add")
    public Result addArticle(@RequestBody ArticleDto articleDto, Principal principal){
        Article article = new Article();
        BeanUtils.copyProperties(articleDto,article);
        article.setAddTime(LocalDateTime.now());
        User user=userService.getUserByAccount(principal.getName());
        article.setArticleBnum(0);
        article.setUserId(user.getId());
        article.setArticleCollection(0);
        article.setArticleStatus(Const.STATUS_ON);
        articleService.save(article);
        if(article.getIsVideo() == 1){
            Video video = new Video();
            video.setVideoUrl(articleDto.getUrls().get(0));
            video.setArticleId(article.getId());
            videoService.save(video);
        }
        else if(article.getIsVideo()==0 && articleDto.getUrls()!=null){
            for (String Url:articleDto.getUrls()) {
                Photo photo = new Photo();
                photo.setArticleId(article.getId());
                photo.setPhotoUrl(Url);
                photoService.save(photo);
            }
        }
        return Result.succ(
                MapUtil.builder()
                .put("id",article.getId())
                .map()
        );
    }
    @PreAuthorize("hasRole('normal')")
    @GetMapping("/articleInfo/{id}")
    public Result articleInfo(@PathVariable String id){
        Article article = articleService.getById(id);
        article.setArticleBnum(article.getArticleBnum()+1);
        articleService.updateById(article);
        return Result.succ(articleService.ArticleInfoById(id));
    }

    @GetMapping("/listByDate")
    public Result articleListByDate(){
        List<Article> list = articleService.listByDate();
        ArrayList<ReAritcleDto> reArticleDtos = new ArrayList<>();
        for (Article article:list) {
            ReAritcleDto reArticleDto=articleService.ArticleInfoById(article.getId());
            reArticleDtos.add(reArticleDto);
        }
        return Result.succ(reArticleDtos);
    }

}
