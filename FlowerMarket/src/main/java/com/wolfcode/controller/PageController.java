package com.wolfcode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;

//页面跳转控制
@Controller
public class PageController {

    // 处理初始登录页面
    @GetMapping("/")
    public String home() {
        return "login1"; // 自动跳转到 templates/login1.html
    }

    // 显式声明 login1 映射
    @GetMapping("/login1")
    public String showLogin1() {
        return "login1";
    }

    // 新增其他页面映射
    @GetMapping("/login2")
    public String showLogin2() {
        return "login2";
    }

    @GetMapping("/login3")
    public String showLogin3() {
        return "login3";
    }

    @GetMapping("/help")
    public String showHelp() {
        return "help";
    }




}