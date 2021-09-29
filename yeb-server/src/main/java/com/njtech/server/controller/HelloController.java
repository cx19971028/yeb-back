package com.njtech.server.controller;

import com.njtech.server.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenxin
 * @date 2021/9/16 15:17
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public Result hello(){
        return Result.success("hello");
    }

    @GetMapping("/employee/basic/hello")
    public Result hello2(){
        return Result.success("/employee/basic/hello");
    }

    @GetMapping("/employee/advanced/hello")
    public Result hello3(){
        return Result.success("/employee/advanced/hello");
    }
}
