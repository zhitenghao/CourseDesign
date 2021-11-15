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
 * @since 2021-11-15
 */
@Data
//@EqualsAndHashCode(callSuper = true)
@ApiModel(value="MessageReply对象", description="")
public class MessageReply extends BaseEntity {

    private static final long serialVersionUID = 1L;

    //所有表的主键
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    public  String id;

    @ApiModelProperty(value = "消息id")
    private String messageId;

    @ApiModelProperty(value = "回复id")
    private String replyId;


}
