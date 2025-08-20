package com.wolfcode.controller;

import com.github.pagehelper.PageInfo;
import com.wolfcode.domain.ShopCart;
import com.wolfcode.domain.User;
import com.wolfcode.service.ShopCartService;
import com.wolfcode.service.UserService;
import com.wolfcode.service.impl.ShopCartServiceImpl;
import com.wolfcode.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/cart", method = {RequestMethod.GET, RequestMethod.POST})
public class ShopCartController {

    @Autowired
    private ShopCartService shopCartService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String getCart(HttpSession session, Model model) {
        // 1. 从Session中获取当前用户Id
        String userId = (String) session.getAttribute("userId");
        System.out.println("当前用户ID: " + userId);
        if (userId == null) {
            return "redirect:/login"; // 未登录则跳转到登录页
        }

        // 使用userId查询购物车数据
        List<ShopCart> cartItems = shopCartService.getCurrentUserCart(userId);
        //遍历每个 item 设置 quantity 默认值
        cartItems.forEach(item -> {
            if (item.getQuantity() == null) {
                item.setQuantity(1);
            }
        });
        // 确保 cartItems 不为 null
        model.addAttribute("cartItems", cartItems != null ? cartItems : Collections.emptyList());
        System.out.println("购物车项详情: " + cartItems);
        // 计算总价时通过 commodity 获取价格
        double totalPrice = cartItems.stream()
                .filter(ShopCart::getChecked)  // 使用 isChecked() 方法
                .mapToDouble(item -> item.getCommodity().getComPrice() * item.getQuantity())
                .sum();
        System.out.println("计算总价: " + totalPrice);
        model.addAttribute("totalPrice", totalPrice);

        return "ShoppingCart"; // Thymeleaf模板名称
    }
    // 更新商品数量
    @PostMapping("/update")
    public ResponseEntity<?> updateQuantity(
            @RequestParam("cartId") Integer shopcartId,
            @RequestParam("quantity") Integer quantity) {
        try {
            shopCartService.updateQuantity(shopcartId, quantity);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("更新失败");
        }
    }

    // 删除购物车项
    @PostMapping("/delete")
    public ResponseEntity<?> deleteItem(@RequestParam("cartId") Integer shopcartId) {
        try {
            shopCartService.deleteItem(shopcartId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("删除失败");
        }
    }

    // 切换选中状态
    @PostMapping("/toggleCheck")
    public ResponseEntity<?> toggleCheck(
            @RequestParam("cartId") Integer shopcartId,
            @RequestParam("checked") Boolean Checked) {
        try {
            shopCartService.toggleCheck(shopcartId, Checked);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("状态更新失败");
        }
    }
    //加入购物车
    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> addToCart(@RequestBody ShopCart cartItem, HttpSession session) {
        Map<String, Object> result = new HashMap<>();
        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            result.put("success", false);
            result.put("redirect", "/login");
            return result;
        }
        try {
            String addResult = shopCartService.insert(userId, cartItem);
            if (addResult == null) {
                result.put("success", true);
            } else {
                result.put("success", false);
                result.put("message", addResult);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "服务器异常: " + e.getMessage());
        }
        return result;
    }

}