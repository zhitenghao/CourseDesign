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
@ApiModel(value="Video对象", description="")
public class Video{

    private static final long serialVersionUID = 1L;


    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    public  String id;

    @ApiModelProperty(value = "视频地址")
    private String videoUrl;

    @ApiModelProperty(value = "帖子编号（外键）")
    private String articleId;


}
