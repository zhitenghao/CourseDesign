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
@ApiModel(value="Article对象", description="")
public class Article extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "拥有人id（是个外键）")
    private String userId;

    @ApiModelProperty(value = "帖子浏览数（初始为0，依次累加）")
    private Integer articleBnum;

    @ApiModelProperty(value = "帖子文字内容")
    private String articleContent;


    @ApiModelProperty(value = "帖子收藏数(初始为0，依次累加)")
    private Integer articleCollection;

    @ApiModelProperty(value = "帖子状态(0 冻结，1 正常)")
    private Integer articleStatus;


    @ApiModelProperty(value = "帖子是否有视频(0 无，1 有)")
    private Integer isVideo;

}
