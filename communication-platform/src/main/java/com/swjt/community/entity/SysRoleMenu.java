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
@ApiModel(value="SysRoleMenu对象", description="")
public class SysRoleMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String roleId;

    private String menuId;


}
