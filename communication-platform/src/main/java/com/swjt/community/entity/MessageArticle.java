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
 * @since 2021-11-15
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@ApiModel(value="MessageArticle对象", description="")
public class MessageArticle {

    private static final long serialVersionUID = 1L;

    //所有表的主键
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    public  String id;

    @ApiModelProperty(value = "对应的消息")
    private String messageId;

    @ApiModelProperty(value = "收藏，点赞对应的帖子")
    private String articleId;


}
