/**
 * @Type com.swjt.community.com.lang
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/10/27  19:27
 * @VERSION Version 1.0
 **/

package com.swjt.community.common.lang;

import lombok.Data;

@Data
public class Result {


    private int code;
    private String msg;
    private Object Data;


    public static Result succ(Object Data){
        return succ(200,"操作成功",Data);
    }

    public static Result succ(int code,String msg,Object Data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(Data);
        return result;
    }

    public static Result fail(int code,String msg,Object Data){
        Result result = new Result();
        result.setCode(400);
        result.setMsg(msg);
        result.setData(Data);
        return result;
    }
    public static Result fail(Object Data){
        return fail(400,"操作失败",Data);
    }

}
