/**
 * @Type com.swjt.community.common.Dto
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/11/2  22:46
 * @VERSION Version 1.0
 **/

package com.swjt.community.common.Dto;

import com.swjt.community.entity.ArticleCategory;
import com.swjt.community.entity.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel("帖子详细内容")
public class ReAritcleDto implements Serializable {

    @ApiModelProperty(value = "帖子id")
    private String id;

    @ApiModelProperty(value = "帖子文字内容")
    private String articleContent;

    @ApiModelProperty(value = "帖子是否有视频")
    private Integer isVideo;

    @ApiModelProperty(value = "视频/图片的地址")
    private List<String> Urls;

    @ApiModelProperty(value = "拥有该帖子的用户")
    private ReUserDto reUserDto;

    @ApiModelProperty(value = "该帖子的所有评论")
    private List<ReCommentDto> reCommentDtos;

    @ApiModelProperty(value = "该帖子的发布时间")
    private LocalDateTime addTime;

    @ApiModelProperty(value = "帖子浏览次数")
    private Integer bNum;

    @ApiModelProperty(value = "帖子点赞次数")
    private Integer likeNum;

    @ApiModelProperty(value = "帖子被收藏次数")
    private Integer collectionNum;

    @ApiModelProperty(value = "帖子是否被自己点赞")
    private boolean isLike;

    @ApiModelProperty(value = "帖子评论数")
    private Integer CommentNum;

    @ApiModelProperty(value = "帖子是否被自己收藏")
    private boolean isCollection;

    @ApiModelProperty(value = "up主是否被关注")
    private boolean isConcern;

    @ApiModelProperty(value = "up主是否是自己")
    private boolean isMyself;

    @ApiModelProperty(value = "帖子分类")
    private Category category;


}
