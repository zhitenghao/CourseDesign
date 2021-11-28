package com.swjt.community.common.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReMessageReplyDto extends  ReMessageArticleDto{

    @ApiModelProperty(value = "回复/评论id")
    private String comReId;

    @ApiModelProperty(value = "评论/回复的内容")
    private String content;


    @ApiModelProperty(value = "（0：.....关注了我  1:....收藏了我的帖子（那个帖子，我也会返回）,2: ...评论我的帖子（评论内容和帖子都会返回），3:...回复了我的评论（回复内容和帖子都会返回），4:...点赞了我的帖子（帖子照样返回））")
    private Integer processType;

}
