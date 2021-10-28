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
@ApiModel(value="Collection对象", description="")
public class Collection extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收藏的用户")
    private String userId;

    @ApiModelProperty(value = "用户收藏的帖子")
    private String articleId;


}
