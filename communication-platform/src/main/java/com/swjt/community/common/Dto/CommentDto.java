/**
 * @Type com.swjt.community.common.Dto
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/11/3  14:02
 * @VERSION Version 1.0
 **/

package com.swjt.community.common.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommentDto implements Serializable {

    @ApiModelProperty(value = "所属帖子（外键）")
    private String articleId;


    @ApiModelProperty(value = "评论内容")
    private String commentContent;
}
