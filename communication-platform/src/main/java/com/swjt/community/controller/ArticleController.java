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
import io.swagger.annotations.*;
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
@Api(value = "帖子接口" ,tags = "帖子接口")
public class ArticleController extends BaseController {

    @Autowired
    VideoService videoService;

    @Autowired
    PhotoService photoService;

    @Autowired
    ArticleCategoryService articleCategoryService;

    @Autowired
    CollectionService collectionService;

    @Autowired
    ConcernService concernService;

    @Autowired
    LoveService likeService;

    @PreAuthorize("hasRole('normal')")
    @PostMapping("/add")
    @ApiOperation(value="添加帖子")
    public Result addArticle(@RequestBody ArticleDto articleDto, Principal principal){
        Article article = new Article();
        BeanUtils.copyProperties(articleDto,article);
        article.setAddTime(LocalDateTime.now());
        User user=userService.getUserByAccount(principal.getName());
        article.setArticleBnum(0);
        article.setArticleComment(0);
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
    @ApiOperation(value="帖子详细信息")
    public Result articleInfo(@PathVariable String id){
        Article article = articleService.getById(id);
        article.setArticleBnum(article.getArticleBnum()+1);
        articleService.updateById(article);
        return Result.succ(articleService.ArticleInfoById(id));
    }

    @GetMapping("/listByDate")
    @ApiOperation(value="根据时间先后返回帖子列表")
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
                Collection collection=collectionService.getOne(new QueryWrapper<Collection>().eq("user_id", user.getId()).eq("article_id", article.getId()));
                if(collection!=null){
                    reArticleDto.setCollection(true);
                }else{
                    reArticleDto.setCollection(false);
                }
                Concern concern=concernService.getOne(new QueryWrapper<Concern>().eq("user_id", user.getId()).eq("usered_id", reArticleDto.getReUserDto().getId()));
                if(concern!=null){
                    reArticleDto.setConcern(true);
                }else{
                    reArticleDto.setConcern(false);
                }
                if(user.getId().equals(article.getUserId())){
                    reArticleDto.setMyself(true);
                }
                else{
                    reArticleDto.setMyself(false);
                }
            }
            reArticleDtos.add(reArticleDto);
        }
        return Result.succ(reArticleDtos);
    }

    @GetMapping("/listByCategory/{categoryId}")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "categoryId",value = "某个分类的id",required = true)
    )
    @ApiOperation(value="根据帖子分类返回帖子列表")
    public Result articleListByCategory(@PathVariable String categoryId,Principal principal){
        List<Article> list = articleService.listByCategory(categoryId);
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
                Collection collection=collectionService.getOne(new QueryWrapper<Collection>().eq("user_id", user.getId()).eq("article_id", article.getId()));
                if(collection!=null){
                    reArticleDto.setCollection(true);
                }else{
                    reArticleDto.setCollection(false);
                }
                Concern concern=concernService.getOne(new QueryWrapper<Concern>().eq("user_id", user.getId()).eq("usered_id", reArticleDto.getReUserDto().getId()));
                if(concern!=null){
                    reArticleDto.setConcern(true);
                }else{
                    reArticleDto.setConcern(false);
                }
                if(user.getId().equals(article.getUserId())){
                    reArticleDto.setMyself(true);
                }
                else{
                    reArticleDto.setMyself(false);
                }
            }
            reArticleDtos.add(reArticleDto);
        }
        return Result.succ(reArticleDtos);
    }

    @GetMapping("/listMyself")
    @ApiOperation(value="返回用户自己的帖子")
    public Result listMyself(Principal principal){
        User userByAccount = userService.getUserByAccount(principal.getName());
        List<Article> list = articleService.listMySelf(userByAccount.getId());
        ArrayList<ReAritcleDto> reArticleDtos = new ArrayList<>();
        for (Article article:list) {
            ReAritcleDto reArticleDto=articleService.ArticleInfoById(article.getId());
            if(userByAccount!=null){
                Love one = likeService.getOne(new QueryWrapper<Love>().eq("user_id", userByAccount.getId()).eq("article_id", article.getId()));
                if(one!=null){
                    reArticleDto.setLike(true);
                }else{
                    reArticleDto.setLike(false);
                }
                Collection collection=collectionService.getOne(new QueryWrapper<Collection>().eq("user_id", userByAccount.getId()).eq("article_id", article.getId()));
                if(collection!=null){
                    reArticleDto.setCollection(true);
                }else{
                    reArticleDto.setCollection(false);
                }
                Concern concern=concernService.getOne(new QueryWrapper<Concern>().eq("user_id", userByAccount.getId()).eq("usered_id", reArticleDto.getReUserDto().getId()));
                if(concern!=null){
                    reArticleDto.setConcern(true);
                }else{
                    reArticleDto.setConcern(false);
                }
                if(userByAccount.getId().equals(article.getUserId())){
                    reArticleDto.setMyself(true);
                }
                else{
                    reArticleDto.setMyself(false);
                }
            }
            reArticleDtos.add(reArticleDto);
        }
        return Result.succ(reArticleDtos);
    }

    @GetMapping("/listByUserId")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "userId",value = "用户id",required = true)
    )
    @ApiOperation(value="返回某个用户的帖子")
    public Result listByUserId(String userId,Principal principal){
        User userByAccount = userService.getUserByAccount(principal.getName());
        List<Article> list = articleService.listMySelf(userId);
        ArrayList<ReAritcleDto> reArticleDtos = new ArrayList<>();
        for (Article article:list) {
            ReAritcleDto reArticleDto=articleService.ArticleInfoById(article.getId());
            if(userByAccount!=null){
                Love one = likeService.getOne(new QueryWrapper<Love>().eq("user_id", userByAccount.getId()).eq("article_id", article.getId()));
                if(one!=null){
                    reArticleDto.setLike(true);
                }else{
                    reArticleDto.setLike(false);
                }
                Collection collection=collectionService.getOne(new QueryWrapper<Collection>().eq("user_id", userByAccount.getId()).eq("article_id", article.getId()));
                if(collection!=null){
                    reArticleDto.setCollection(true);
                }else{
                    reArticleDto.setCollection(false);
                }
                Concern concern=concernService.getOne(new QueryWrapper<Concern>().eq("user_id", userByAccount.getId()).eq("usered_id", reArticleDto.getReUserDto().getId()));
                if(concern!=null){
                    reArticleDto.setConcern(true);
                }else{
                    reArticleDto.setConcern(false);
                }
                if(userByAccount.getId().equals(article.getUserId())){
                    reArticleDto.setMyself(true);
                }
                else{
                    reArticleDto.setMyself(false);
                }
            }
            reArticleDtos.add(reArticleDto);
        }
        return Result.succ(reArticleDtos);
    }

    @GetMapping("/listConcernByUserId")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "userId",value = "用户id(为空表示查自己的，不为空就传别人的id)",required = false)
    )
    @ApiOperation(value="返回某个用户收藏的帖子")
    public Result listConcernByUserId(String userId,Principal principal){
        User userByAccount = userService.getUserByAccount(principal.getName());
        if(userId==null || "".equals(userId)){
            userId=userByAccount.getId();
        }
        List<Article> list = articleService.listConcernByUserId(userId);
        ArrayList<ReAritcleDto> reArticleDtos = new ArrayList<>();
        for (Article article:list) {
            ReAritcleDto reArticleDto=articleService.ArticleInfoById(article.getId());
            if(userByAccount!=null){
                Love one = likeService.getOne(new QueryWrapper<Love>().eq("user_id", userByAccount.getId()).eq("article_id", article.getId()));
                if(one!=null){
                    reArticleDto.setLike(true);
                }else{
                    reArticleDto.setLike(false);
                }
                Collection collection=collectionService.getOne(new QueryWrapper<Collection>().eq("user_id", userByAccount.getId()).eq("article_id", article.getId()));
                if(collection!=null){
                    reArticleDto.setCollection(true);
                }else{
                    reArticleDto.setCollection(false);
                }
                Concern concern=concernService.getOne(new QueryWrapper<Concern>().eq("user_id", userByAccount.getId()).eq("usered_id", reArticleDto.getReUserDto().getId()));
                if(concern!=null){
                    reArticleDto.setConcern(true);
                }else{
                    reArticleDto.setConcern(false);
                }
                if(userByAccount.getId().equals(article.getUserId())){
                    reArticleDto.setMyself(true);
                }
                else{
                    reArticleDto.setMyself(false);
                }
            }
            reArticleDtos.add(reArticleDto);
        }
        return Result.succ(reArticleDtos);
    }

    @GetMapping("/listLikeByUserId")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "userId",value = "用户id(为空表示查自己的，不为空就传别人的id)",required = false)
    )
    @ApiOperation(value="返回某个用户点赞了的帖子")
    public Result listLikeByUserId(String userId,Principal principal){
        User userByAccount = userService.getUserByAccount(principal.getName());
        if(userId==null || "".equals(userId)){
            userId=userByAccount.getId();
        }
        List<Article> list = articleService.listLikeByUserId(userId);
        ArrayList<ReAritcleDto> reArticleDtos = new ArrayList<>();
        for (Article article:list) {
            ReAritcleDto reArticleDto=articleService.ArticleInfoById(article.getId());
            if(userByAccount!=null){
                Love one = likeService.getOne(new QueryWrapper<Love>().eq("user_id", userByAccount.getId()).eq("article_id", article.getId()));
                if(one!=null){
                    reArticleDto.setLike(true);
                }else{
                    reArticleDto.setLike(false);
                }
                Collection collection=collectionService.getOne(new QueryWrapper<Collection>().eq("user_id", userByAccount.getId()).eq("article_id", article.getId()));
                if(collection!=null){
                    reArticleDto.setCollection(true);
                }else{
                    reArticleDto.setCollection(false);
                }
                Concern concern=concernService.getOne(new QueryWrapper<Concern>().eq("user_id", userByAccount.getId()).eq("usered_id", reArticleDto.getReUserDto().getId()));
                if(concern!=null){
                    reArticleDto.setConcern(true);
                }else{
                    reArticleDto.setConcern(false);
                }
                if(userByAccount.getId().equals(article.getUserId())){
                    reArticleDto.setMyself(true);
                }
                else{
                    reArticleDto.setMyself(false);
                }
            }
            reArticleDtos.add(reArticleDto);
        }
        return Result.succ(reArticleDtos);
    }

    @GetMapping("/listArticleConcernUserByUserId")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "userId",value = "用户id(为空表示查自己的，不为空就传别人的id)",required = false)
    )
    @ApiOperation(value="返回某个用户关注的用户的帖子")
    public Result listArticleConcernUserByUserId(String userId,Principal principal){
        User userByAccount = userService.getUserByAccount(principal.getName());
        if(userId==null || "".equals(userId)){
            userId=userByAccount.getId();
        }
        List<Article> list = articleService.listArticleConcernUserByUserId(userId);
        ArrayList<ReAritcleDto> reArticleDtos = new ArrayList<>();
        for (Article article:list) {
            ReAritcleDto reArticleDto=articleService.ArticleInfoById(article.getId());
            if(userByAccount!=null){
                Love one = likeService.getOne(new QueryWrapper<Love>().eq("user_id", userByAccount.getId()).eq("article_id", article.getId()));
                if(one!=null){
                    reArticleDto.setLike(true);
                }else{
                    reArticleDto.setLike(false);
                }
                Collection collection=collectionService.getOne(new QueryWrapper<Collection>().eq("user_id", userByAccount.getId()).eq("article_id", article.getId()));
                if(collection!=null){
                    reArticleDto.setCollection(true);
                }else{
                    reArticleDto.setCollection(false);
                }
                Concern concern=concernService.getOne(new QueryWrapper<Concern>().eq("user_id", userByAccount.getId()).eq("usered_id", reArticleDto.getReUserDto().getId()));
                if(concern!=null){
                    reArticleDto.setConcern(true);
                }else{
                    reArticleDto.setConcern(false);
                }
                if(userByAccount.getId().equals(article.getUserId())){
                    reArticleDto.setMyself(true);
                }
                else{
                    reArticleDto.setMyself(false);
                }
            }
            reArticleDtos.add(reArticleDto);
        }
        return Result.succ(reArticleDtos);
    }
}
