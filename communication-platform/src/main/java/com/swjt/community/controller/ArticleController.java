package com.swjt.community.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.swjt.community.common.Dto.ReAritcleDto;
import com.swjt.community.common.Dto.ArticleDto;
import com.swjt.community.common.lang.Const;
import com.swjt.community.common.lang.Result;
import com.swjt.community.entity.*;
import com.swjt.community.service.*;
import com.swjt.community.utils.BeanUtils;
import io.swagger.annotations.ApiOperation;
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
@ApiOperation("帖子接口")
public class ArticleController extends BaseController {
    @Autowired
    ArticleService articleService;

    @Autowired
    VideoService videoService;

    @Autowired
    PhotoService photoService;

    @Autowired
    UserService userService;

    @Autowired
    ArticleCategoryService articleCategoryService;

    @Autowired
    LoveService likeService;

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
        article.setArticleLike(0);
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
        if(articleDto.getArticleCategory()!=null){
            ArticleCategory articleCategory = new ArticleCategory();
            articleCategory.setCategoryId(articleDto.getArticleCategory());
            articleCategory.setArticleId(article.getId());
            articleCategoryService.save(articleCategory);
        }
        else{
            return Result.fail("没有输入正确分类");
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
    public Result articleListByDate(Principal principal){
        List<Article> list = articleService.listByDate();
        User user = new User();
        if(principal!=null){
            user = userService.getUserByAccount(principal.getName());
        }
        ArrayList<ReAritcleDto> reArticleDtos = new ArrayList<>();
        for (Article article:list) {
            ReAritcleDto reArticleDto=articleService.ArticleInfoById(article.getId());
            if(user!=null){
                Love one = likeService.getOne(new QueryWrapper<Love>().eq("user_id", user.getId()).eq("article_id", article.getId()));
                if(one!=null){
                    reArticleDto.setLike(true);
                }else{
                    reArticleDto.setLike(false);
                }
            }
            reArticleDtos.add(reArticleDto);
        }
        return Result.succ(reArticleDtos);
    }

    @GetMapping("/listByCategory/{categoryId}")
    public Result articleListByCategory(@PathVariable String categoryId){
        List<Article> list = articleService.listByCategory(categoryId);
        ArrayList<ReAritcleDto> reArticleDtos = new ArrayList<>();
        for (Article article:list) {
            ReAritcleDto reArticleDto=articleService.ArticleInfoById(article.getId());
            reArticleDtos.add(reArticleDto);
        }
        return Result.succ(reArticleDtos);
    }

    @GetMapping("/listMyself")
    public Result listMyself(Principal principal){
        User userByAccount = userService.getUserByAccount(principal.getName());
        List<Article> list = articleService.listMySelf(userByAccount.getId());
        ArrayList<ReAritcleDto> reArticleDtos = new ArrayList<>();
        for (Article article:list) {
            ReAritcleDto reArticleDto=articleService.ArticleInfoById(article.getId());
            reArticleDtos.add(reArticleDto);
        }
        return Result.succ(reArticleDtos);
    }
}
