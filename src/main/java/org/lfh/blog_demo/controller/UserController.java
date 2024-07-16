package org.lfh.blog_demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.lfh.blog_demo.service.UserService;
import org.lfh.blog_demo.util.Result;
import org.springframework.web.bind.annotation.*;
import vo.param.UserParam;

@Tag(name = "UserController", description = "用户控制器")
@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Resource
    private UserService userService;


    @Operation(summary = "用户注册", description = "用户注册")
    @Parameters({
            @Parameter(name = "userParam", description = "用户姓名、密码、邮箱", required = true),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "注册成功"),
            @ApiResponse(responseCode = "505", description = "注册失败，填写项不正确"),
            @ApiResponse(responseCode = "506", description = "注册失败，已有用户注册"),
            @ApiResponse(responseCode = "507", description = "注册失败，请稍后重试")

    })
    @PostMapping("register")
    public Result register(@RequestBody UserParam userParam) {
        return userService.register(userParam);
    }


    @Operation(summary = "用户登录", description = "用户登录")
    @Parameters({
            @Parameter(name = "userParam", description = "用户姓名、密码、邮箱", required = true),
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "登录成功"),
            @ApiResponse(responseCode = "508", description = "邮箱或密码错误"),
            @ApiResponse(responseCode = "509", description = "登录失败，填写项不正确"),
    })
    @PostMapping("login")
    public Result login(@RequestBody UserParam userParam) {
        return userService.login(userParam);
    }


    @Operation(summary = "获取当前用户信息", description = "获取当前用户信息（需要认证）")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "操作成功"),
            @ApiResponse(responseCode = "510", description = "系统异常，请稍后重试"),
    })
    @GetMapping("me")
    public Result me() {
        return userService.me();
    }

}
