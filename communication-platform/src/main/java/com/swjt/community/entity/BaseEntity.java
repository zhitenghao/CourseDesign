/**
 * @Type com.swjt.community.entity
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/10/27  19:14
 * @VERSION Version 1.0
 **/

package com.swjt.community.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BaseEntity implements Serializable {

    //所有表的主键
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    public  String id;

    //添加时间
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime addTime;

    //更新时间
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;


}
