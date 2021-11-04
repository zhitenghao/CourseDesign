/**
 * @Type com.swjt.community.common.Dto
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/11/4  17:15
 * @VERSION Version 1.0
 **/

package com.swjt.community.common.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReUserDto implements Serializable {
    @ApiModelProperty(value = "用户id")
    private String id;


    @ApiModelProperty(value = "用户昵称")
    private String userName;

    @ApiModelProperty(value = "用户头像url")
    private String userAvatar;

}
