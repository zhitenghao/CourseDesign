package com.swjt.community.entity;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author DaiRui
 * @since 2021-10-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="User对象", description="")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;


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

    @ApiModelProperty(value = "用户账户状态（0：冻结，1：正常）")
    private Integer userStatus;

    @ApiModelProperty(value = "用户学院")
    private String userCollege;

    @ApiModelProperty(value = "用户简介")
    private String userDescription;

    @ApiModelProperty(value = "用户出生日期")
    private LocalDateTime userBirthday;

    @ApiModelProperty(value = "用户年龄")
    private Integer userAge;

    @ApiModelProperty(value = "用户头像")
    private String userAvatar;

    @ApiModelProperty(value = "被多少用户关注(初始为0)")
    private Integer userConcerned;

    @ApiModelProperty(value = "关注了多少用户(初始为0)")
    private Integer userConcern;


}
