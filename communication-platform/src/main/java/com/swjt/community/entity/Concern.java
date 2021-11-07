package com.swjt.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-11-05
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@ApiModel(value="Concern对象", description="")
public class Concern{

    private static final long serialVersionUID = 1L;

    //所有表的主键
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    public  String id;

    @ApiModelProperty(value = "关注人")
    private String userId;

    @ApiModelProperty(value = "被关注人")
    private String useredId;


}
