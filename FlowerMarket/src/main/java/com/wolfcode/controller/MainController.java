package com.wolfcode.controller;

import com.wolfcode.domain.Commodity;
import com.wolfcode.service.CommodityService;
import jakarta.servlet.http.HttpSession;
import com.wolfcode.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private CommodityService commodityService;

    @RequestMapping(value = "/main", method = {RequestMethod.GET, RequestMethod.POST})
    public String mainPage(HttpSession session, Model model) {
        // 从会话中获取用户信息
        User user = (User) session.getAttribute("curruser");
        if (user == null) {return "redirect:/login2"; // 未登录跳回登录页
        }
        // 向模板传递用户名和头像
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userpicture", user.getUserpicture());
        List<Commodity> commodities = commodityService.listAll(); // 获取所有商品
        model.addAttribute("commodities", commodities);
        return "main"; // 对应模板文件名
    }

    // 同城好物页面（comDepartment=2）
    @GetMapping("/tongcheng")
    public String tongcheng(HttpSession session,Model model) {
        // 从会话中获取用户信息
        User user = (User) session.getAttribute("curruser");
        System.out.println(user.getUserpicture());

        if (user == null) {return "redirect:/login2"; // 未登录跳回登录页
        }
        // 向模板传递用户名和头像
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userpicture", user.getUserpicture());
        //加载商品
        List<Commodity> commodities = commodityService.listByDepartment(2);
        model.addAttribute("commodities", commodities);
        return "navigation1_tongcheng";
    }
    // 特价秒杀页面（comDepartment=3）
    @GetMapping("/miaosha")
    public String 秒杀(HttpSession session,Model model) {
        // 从会话中获取用户信息
        User user = (User) session.getAttribute("curruser");
        if (user == null) {return "redirect:/login2"; // 未登录跳回登录页
        }
        // 向模板传递用户名和头像
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userpicture", user.getUserpicture());
        //加载商品
        List<Commodity> commodities = commodityService.listByDepartment(3);
        model.addAttribute("commodities", commodities);
        return "navigation2_miaosha";
    }
    // 迎宾花卉页面（comDepartment=1）
    @GetMapping("/classify1")
    public String classify1(HttpSession session,Model model) {
        // 从会话中获取用户信息
        User user = (User) session.getAttribute("curruser");
        if (user == null) {return "redirect:/login2"; // 未登录跳回登录页
        }
        // 向模板传递用户名和头像
        model.addAttribute("username", user.getUsername());
        model.addAttribute("userpicture", user.getUserpicture());
        List<Commodity> commodities = commodityService.listByDepartment(1);
        model.addAttribute("commodities", commodities);
        return "classify1";
    }
}