package com.wolfcode.controller;

import com.wolfcode.domain.User;
import com.wolfcode.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController//组合了@Controller和@ResponseBody，表示该类处理HTTP请求并直接返回JSON数据。
@CrossOrigin//许跨域请求，默认允许所有来源
@Slf4j//Lombok注解，自动生成日志对象 log
public class LoginController {
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public LoginController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> login(
            //@RequestParam 获取表单提交的用户名和密码
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session//存储登录后的用户信息
    ) {
        log.debug("Login attempt - username: {}, password: {}", username, password);
        User user = userServiceImpl.login(username, password);
        Map<String, Object> map = new HashMap<>();
        if (user != null) {
            session.setAttribute("curruser", user);
            session.setAttribute("userId", user.getUserId()); // 存储用户ID
            session.setAttribute("username", user.getUsername()); // 存储用户名
            session.setAttribute("userpicture", user.getUserpicture()); // 存储用户头像

            map.put("success", true);
            map.put("msg", "登录成功");
        } else if(user == null) {
            map.put("success", false);
            map.put("msg", "账号或密码错误");
        }
        return map;
    }
}