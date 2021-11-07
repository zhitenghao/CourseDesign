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
 * @since 2021-11-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="Message对象", description="")
public class Message extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "消息主体（发出动作的用户id）")
    private String principleId;

    @ApiModelProperty(value = "消息客体 (承受动作的用户id)")
    private String objectId;

    @ApiModelProperty(value = "是否被主体阅读(0：未阅读，1：已阅读)")
    private Integer principleRead;

    @ApiModelProperty(value = "是否被客体阅读(0：未阅读，1：已阅读)")
    private Integer objectRead;

    @ApiModelProperty(value = "（0：.....关注了..，1:....收藏了...的帖子,2: ...评论..的帖子,3:...回复了..评论，4:...点赞了...的帖子）")
    private Integer processType;


}
