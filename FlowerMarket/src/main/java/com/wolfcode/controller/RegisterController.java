package com.wolfcode.controller;

import com.wolfcode.service.impl.UserServiceImpl;
import com.wolfcode.domain.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@RestController
@CrossOrigin
@Controller
public class RegisterController {
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public RegisterController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, Object> requestBody) {
        try {
            // 参数提取
            String userid = (String) requestBody.get("userid");
            String username = (String) requestBody.get("username");
            String email = (String) requestBody.get("email");
            String pasw = (String) requestBody.get("pasw");
            String confirmPasw = (String) requestBody.get("confirmPasw");
            // 基础参数校验
            if (userid == null || userid.trim().isEmpty() ||
                    username == null || username.trim().isEmpty() ||
                    email == null || email.trim().isEmpty() ||
                    pasw == null || pasw.trim().isEmpty()) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("msg", "所有字段均为必填项");
                return ResponseEntity.badRequest().body(response);
            }
            // 创建用户对象
            User user = new User();
            user.setUserId(userid);
            user.setUsername(username); // 正确设置用户名
            user.setPassword(pasw);
            user.setEmail(email);

            // 调用服务层
            return ResponseEntity.ok(userServiceImpl.register(user, confirmPasw));
        } catch (NumberFormatException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("msg", "用户ID必须为有效字符串");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("msg", "请求参数格式错误");
            return ResponseEntity.badRequest().body(response);
        }
    }
}