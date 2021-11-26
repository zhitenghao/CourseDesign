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
 * @since 2021-10-27
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@ApiModel(value="Collection对象", description="")
public class Collection{

    private static final long serialVersionUID = 1L;

    //所有表的主键
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    public  String id;

    @ApiModelProperty(value = "收藏的用户")
    private String userId;

    @ApiModelProperty(value = "用户收藏的帖子")
    private String articleId;

    @ApiModelProperty(value = "消息id")
    private String messageId;


}
