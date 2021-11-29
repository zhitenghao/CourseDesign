/**
 * @Type com.swjt.community.common.Dto
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/11/13  15:59
 * @VERSION Version 1.0
 **/

package com.swjt.community.common.Dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class ExtendReUserDto extends ReUserDto implements Serializable {

    @ApiModelProperty(value = "用户简介")
    private String userDescription;

    @ApiModelProperty(value = "0:未关注，1:已关注，2:互相关注")
    private Integer isConcerned;

    @ApiModelProperty(value = "是否  是自己")
    private boolean isMyself;

}
