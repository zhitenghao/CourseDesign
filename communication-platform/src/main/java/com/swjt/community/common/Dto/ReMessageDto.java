package com.swjt.community.common.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReMessageDto {
    //所有表的主键
    @ApiModelProperty(value = "消息主体")
    public  String messageId;

    //添加时间
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime addTime;

    //更新时间
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "消息主体（发出动作的用户）")
    private ReUserDto reUserDto;

    @ApiModelProperty(value = "是否被阅读(0：未阅读，1：已阅读)")
    private Integer objectRead;

    @ApiModelProperty(value = "是否是 自己")
    private boolean isMyself;
}
