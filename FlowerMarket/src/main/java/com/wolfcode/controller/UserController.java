package com.wolfcode.controller;

import com.wolfcode.domain.User;
import com.wolfcode.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    // 获取用户设置信息
    @GetMapping("/settings")
    public ResponseEntity<Map<String, Object>> getSettings(HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        // 检查用户是否登录
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, "/login2")
                    .build();
        }

        User user = userService.getUserById(userId);

        // 检查用户是否存在
        if (user == null) {
            return ResponseEntity.status(404).body(Map.of("error", "用户不存在"));
        }

        Map<String, Object> response = new HashMap<>();
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("userpicture", user.getUserpicture());
        return ResponseEntity.ok(response);
    }

    // 更新基本信息
    @PutMapping("/settings")
    public Map<String, Object> updateSettings(
            @RequestBody Map<String, String> updates,
            HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        return userService.updateUserSettings(userId, updates);
    }

    // 修改密码
    @PostMapping("/change-password")
    public Map<String, Object> changePassword(
            @RequestBody Map<String, String> passwords,
            HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        return userService.changePassword(
                userId,
                passwords.get("oldPassword"),
                passwords.get("newPassword")
        );
    }

    // 上传头像
    @PostMapping("/avatar")
    public ResponseEntity<Map<String, Object>> uploadAvatar(
            @RequestParam("avatar") MultipartFile file,
            HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        return ResponseEntity.ok(userService.uploadAvatar(userId, file));
    }
}