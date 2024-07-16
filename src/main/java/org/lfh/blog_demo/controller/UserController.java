package org.lfh.blog_demo.controller;

import jakarta.annotation.Resource;
import org.lfh.blog_demo.service.UserService;
import org.lfh.blog_demo.util.Result;
import org.springframework.web.bind.annotation.*;
import vo.param.UserParam;

@RestController
@RequestMapping("/api/auth")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户注册
     *
     * @param userParam 注册数据
     * @return 注册结果
     */
    @PostMapping("register")
    public Result register(@RequestBody UserParam userParam) {
        return userService.register(userParam);
    }

    /**
     * 用户登录
     *
     * @param userParam 登录参数
     * @return token
     */
    @PostMapping("login")
    public Result login(@RequestBody UserParam userParam) {
        return userService.login(userParam);
    }

    /**
     * 获取当前用户信息（需要认证）
     *
     * @return 当前用户信息
     */
    @GetMapping("me")
    public Result me() {
        return userService.me();
    }

}
