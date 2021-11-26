package com.swjt.community.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-11-08
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@ApiModel(value="Love对象", description="")
public class Love{

    private static final long serialVersionUID = 1L;


    //所有表的主键
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    public  String id;

    @ApiModelProperty(value = "点赞人")
    private String userId;

    @ApiModelProperty(value = "点赞帖子")
    private String articleId;

    @ApiModelProperty(value = "消息id")
    private String messageId;


}
