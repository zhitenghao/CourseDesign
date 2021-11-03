/**
 * @Type com.swjt.community.common.Dto
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/11/2  18:25
 * @VERSION Version 1.0
 **/

package com.swjt.community.common.Dto;

import io.swagger.annotations.ApiModelProperty;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class ArticleDto implements Serializable {

    @ApiModelProperty(value = "帖子文字内容")
    private String articleContent;

    @ApiModelProperty(value = "帖子是否有视频")
    private Integer isVideo;

    @ApiModelProperty(value = "视频/图片的地址")
    private List<String> Urls;

}
