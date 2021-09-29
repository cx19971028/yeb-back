package com.njtech.server.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.security.auth.login.CredentialNotFoundException;

/**
 *  公共返回对象
 * @author chenxin
 * @date 2021/9/14 9:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {
    private long code;
    private String message;
    private Object obj;

    /**
     * 成功返回
     * @param message
     * @return
     */
    public static Result success(String message){
        return new Result(200,message,null);
    }

    /**
     * 成功返回
     * @param message
     * @param obj
     * @return
     */
    public static Result success(String message,Object obj){
        return new Result(200,message,obj);
    }

    /**
     * 成功返回
     * @param message
     * @param obj
     * @return
     */
    public static Result success(Object obj){
        return new Result(200,"success",obj);
    }
    /**
     * 失败返回
     * @param message
     * @return
     */
    public static Result fail(String message){
        return new Result(500,message,null);
    }


    /**
     * 失败返回
     * @param message
     * @return
     */
    public static Result fail(String message,Object obj){
        return new Result(500,message,obj);
    }
}
