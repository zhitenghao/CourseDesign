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
@ApiModel(value="Reply对象", description="")
public class Reply extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "回复评论内容")
    private String replyContent;

    @ApiModelProperty(value = "评论编号（外键）")
    private String commentId;

    @ApiModelProperty(value = "回复人编号（user_id外键）")
    private String replyUser;

    @ApiModelProperty(value = "被回复人编号(user_id外键)")
    private String repliedUser;

    public String getReplyedUser()
    {
        return this.repliedUser;
    }

    public void setReplyedUser(String replyedUser){
        this.repliedUser=replyedUser;
    }


}
