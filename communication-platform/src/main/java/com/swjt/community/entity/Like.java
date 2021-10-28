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
@ApiModel(value="Like对象", description="")
public class Like extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "点赞人")
    private String userId;

    @ApiModelProperty(value = "点赞帖子")
    private String articleId;


}
