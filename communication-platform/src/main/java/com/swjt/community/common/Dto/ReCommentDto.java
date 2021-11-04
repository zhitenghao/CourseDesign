/**
 * @Type com.swjt.community.common.Dto
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/11/4  17:09
 * @VERSION Version 1.0
 **/

package com.swjt.community.common.Dto;

import com.swjt.community.entity.Reply;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReCommentDto implements Serializable {

    @ApiModelProperty(value = "评论用户（外键）")
    private ReUserDto commentUser;

    @ApiModelProperty(value = "评论内容")
    private String commentContent;

    @ApiModelProperty(value = "评论下面的回复")
    private List<ReReplyDto> replyList;

    @ApiModelProperty(value = "评论时间")
    private LocalDateTime commentTime;

}
