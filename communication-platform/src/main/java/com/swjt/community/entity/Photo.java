package com.swjt.community.entity;


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
@ApiModel(value="Photo对象", description="")
public class Photo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "图片地址")
    private String photoUrl;

    @ApiModelProperty(value = "所属帖子（外键）")
    private String articleId;


}
