/**
 * @Type com.swjt.community.common.Dto
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/11/4  17:18
 * @VERSION Version 1.0
 **/

package com.swjt.community.common.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ReReplyDto implements Serializable {

    @ApiModelProperty(value = "回复评论内容")
    private String replyContent;


    @ApiModelProperty(value = "回复人")
    private ReUserDto replyUser;

    @ApiModelProperty(value = "被回复人")
    private ReUserDto repliedUser;


    @ApiModelProperty(value = "回复时间")
    private LocalDateTime replyTime;

    public void setReplyedUser(ReUserDto replyedUser){
        this.repliedUser=replyedUser;
    }

    public ReUserDto getReplyedUser(){
        return this.repliedUser;
    }
}
