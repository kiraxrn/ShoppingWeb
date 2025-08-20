package com.wolfcode.service.impl;

import com.wolfcode.domain.User;
import com.wolfcode.mapper.UserMapper;
import com.wolfcode.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    private final String AVATAR_DIR = System.getProperty("user.dir") + "/uploads/avatars/";
//头像储存路径

    //登录
    @Override
    public User login(String username, String password) {
        // 使用实例变量userMapper调用方法
        User user = userMapper.selectByUsernameAndPassword(username, password);
        return user;
    }
    @Override
    public boolean validateUser(String username, String password) {
        User user = userMapper.findByUserid(username);
        // 直接比较明文密码
        return user != null && password.equals(user.getPassword());
    }

//注册
    @Override
    @Transactional
    public Map<String, Object> register(User user, String confirmPasw) {
        Map<String, Object> response = new HashMap<>();

        // 1. 校验密码一致性
        if (!user.getPassword().equals(confirmPasw)) {
            response.put("success", false);
            response.put("msg", "两次输入的密码不一致");
            return response;
        }

        // 2. 校验用户ID唯一性
        if (userMapper.findByUserid(user.getUserId())!=null) {
            response.put("success", false);
            response.put("msg", "该用户ID已被注册");
            return response;
        }

        // 3. 校验邮箱唯一性
        if (userMapper.findByEmail(user.getEmail())!=null) {
            response.put("success", false);
            response.put("msg", "该邮箱已被注册");
            return response;
        }
        // 4. 邮箱格式校验
        if (!isValidEmail(user.getEmail())) {
            response.put("success", false);
            response.put("msg", "邮箱格式不正确");
            return response;
        }

        // 6. 保存用户信息
        userMapper.insertUser(user);

        // 7. 返回成功响应
        response.put("success", true);
        response.put("msg", "注册成功");
        return response;
    }

    @Override
    public Integer getUserIdByUsername(String username) {
        User user = userMapper.selectByUsername(username);
        return user != null ? Integer.valueOf(user.getUserId()) : null;
    }

    // 邮箱正则校验
    private boolean isValidEmail(String email) {
        String regex = "^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(regex);
    }

    @Override
    public User getUserById(String userId) {
        return userMapper.findByUserid(userId);
    }

    @Override
    public Map<String, Object> updateUserSettings(String userId, Map<String, String> updates) {
        User user = userMapper.findByUserid(userId);
        Map<String, Object> result = new HashMap<>();

        updates.forEach((key, value) -> {
            switch (key) {
                case "username" -> user.setUsername(value);
                case "email" -> user.setEmail(value);

            }
        });

        userMapper.update(user);
        result.put("success", true);
        result.put("message", "信息更新成功");
        return result;
    }

    @Override
    public Map<String, Object> changePassword(String userId, String oldPassword, String newPassword) {
        Map<String, Object> result = new HashMap<>();
        User user = userMapper.findByUserid(userId);

        if (!user.getPassword().equals(oldPassword)) {
            result.put("success", false);
            result.put("message", "旧密码不正确");
            return result;
        }

        user.setPassword(newPassword);
        userMapper.update(user);
        result.put("success", true);
        result.put("message", "密码修改成功");
        return result;
    }

    @Override
    public Map<String, Object> uploadAvatar(String userId, MultipartFile file) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 1. 确保目录存在（使用 Path 处理路径）
            Path uploadPath = Paths.get(AVATAR_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 2. 生成唯一文件名
            String fileName = userId + "_" + System.currentTimeMillis() +
                    file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

            // 3. 保存文件（使用 Path 拼接路径）
            Path targetPath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // 4. 更新用户头像 URL（确保与静态资源映射一致）
            User user = userMapper.findByUserid(userId);
            user.setUserpicture("/uploads/avatars/" + fileName);
            userMapper.update(user);

            result.put("success", true);
            result.put("userpicture", "/avatars/" + fileName);
        } catch (IOException e) {
            result.put("success", false);
            result.put("message", "头像上传失败: " + e.getMessage());
        }
        return result;
    }

}