/**
 * @Type com.swjt.community.common.Dto
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/11/3  21:53
 * @VERSION Version 1.0
 **/

package com.swjt.community.common.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReplyDto implements Serializable {

    @ApiModelProperty(value = "回复评论内容")
    private String replyContent;

    @ApiModelProperty(value = "评论编号（外键）")
    private String commentId;

    @ApiModelProperty(value = "回复人编号（user_id外键）")
    private String replyUser;

    @ApiModelProperty(value = "被回复人编号(user_id外键)")
    private String replyedUser;
}
