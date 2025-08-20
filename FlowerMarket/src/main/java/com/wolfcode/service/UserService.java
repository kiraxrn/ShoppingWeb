package com.wolfcode.service;
import com.wolfcode.domain.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.Map;

public interface UserService {
    User login(String username, String password);

    boolean validateUser(String username, String password);

    @Transactional
    Map<String, Object> register(User user, String confirmPassword);

    Integer getUserIdByUsername(String username);

    User getUserById(String userId);
    Map<String, Object> updateUserSettings(String userId, Map<String, String> updates);
    Map<String, Object> changePassword(String userId, String oldPassword, String newPassword);
    Map<String, Object> uploadAvatar(String userId, MultipartFile file);

}
