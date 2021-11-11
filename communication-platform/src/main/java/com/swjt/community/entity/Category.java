package com.swjt.community.entity;

import com.swjt.community.entity.BaseEntity;
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
 * @since 2021-11-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="Category对象", description="")
public class Category extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "分类顺序")
    private Integer orderId;

    @ApiModelProperty(value = "分类路径")
    private String path;

}
