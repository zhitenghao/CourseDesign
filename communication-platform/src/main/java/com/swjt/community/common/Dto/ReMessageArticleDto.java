package com.swjt.community.common.Dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReMessageArticleDto extends ReMessageDto{


    @ApiModelProperty(value = "对应的帖子id")
    private String articleId;


}
