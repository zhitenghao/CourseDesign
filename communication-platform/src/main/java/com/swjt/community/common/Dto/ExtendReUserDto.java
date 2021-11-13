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

    @ApiModelProperty(value = "是否已经关注")
    private boolean isConcerned;

}
