package com.njtech.server.exception;

import com.njtech.server.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 *
 * 全局异常处理
 *
 * @author chenxin
 * @date 2021/9/19 19:26
 */
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(SQLException.class)
    public Result sqlExceptionHandler(SQLException e){
        return Result.fail("数据库异常，操作失败",e.getMessage());
    }
}
