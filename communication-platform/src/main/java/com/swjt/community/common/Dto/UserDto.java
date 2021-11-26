/**
 * @Type com.swjt.community.common.Dto
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/10/29  15:51
 * @VERSION Version 1.0
 **/

package com.swjt.community.common.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserDto implements Serializable {

    @ApiModelProperty(value = "用户昵称")
    private String userName;

    @ApiModelProperty(value = "用户密码")
    private String userPwd;

    @ApiModelProperty(value = "用户账户")
    private String userAccount;

    @ApiModelProperty(value = "用户电话")
    private String userPhone;

    @ApiModelProperty(value = "用户性别(0:男，1:女)")
    private Integer userSex;

    @ApiModelProperty(value = "用户住址")
    private String userAddress;

    @ApiModelProperty(value = "用户学院")
    private String userCollege;

    @ApiModelProperty(value = "用户简介")
    private String userDescription;

    @ApiModelProperty(value = "用户出生日期")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime userBirthday;

    @ApiModelProperty(value = "用户头像")
    private String userAvatar;


}
