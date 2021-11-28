package com.swjt.community.common.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MessageReplyDto {

    @ApiModelProperty(value = "回复评论内容")
    private String replyContent;

    @ApiModelProperty(value = "评论/回复编号（外键）")
    private String comReId;

    @ApiModelProperty(value = "被回复人编号(user_id外键)")
    private String repliedUser;

    @ApiModelProperty(value = "评论还是回复")
    private Integer processType;

}
