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
@ApiModel(value="Photo对象", description="")
public class Photo {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    public  String id;

    @ApiModelProperty(value = "图片地址")
    private String photoUrl;

    @ApiModelProperty(value = "所属帖子（外键）")
    private String articleId;


}
