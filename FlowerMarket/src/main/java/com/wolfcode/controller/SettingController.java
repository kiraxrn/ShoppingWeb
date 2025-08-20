package com.wolfcode.controller;

import com.wolfcode.domain.User;
import com.wolfcode.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class SettingController {

    private final UserService userService;

    @GetMapping("/settings")
    public String settingsPage(HttpSession session, Model model) {
        // 验证登录状态
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login2";
        }

        // 获取用户数据
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 向模板注入数据
        model.addAttribute("response", user);
        return "setting"; // 对应 templates/setting.html
    }
}