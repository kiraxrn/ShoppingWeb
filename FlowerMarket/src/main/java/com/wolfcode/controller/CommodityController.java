package com.wolfcode.controller;

import com.wolfcode.domain.Commodity;
import com.wolfcode.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/product", method = {RequestMethod.GET, RequestMethod.POST})
public class CommodityController {

    @Autowired
    private CommodityService commodityService;

    // 处理带 ID 的路径（如 /product/1）
    @GetMapping("/{comId}")
    public String productDetail(@PathVariable Integer comId, Model model) {
        Commodity commodity = commodityService.getById(comId);
        model.addAttribute("commodity", commodity);
        // 输出所有属性验证数据
        if (commodity != null) {
            System.out.println("===== 商品数据验证 =====");
            System.out.println("comId: " + commodity.getComId());
            System.out.println("comName: " + commodity.getComName());
            System.out.println("comPrice: " + commodity.getComPrice());
            System.out.println("comPicture: " + commodity.getComPicture());
            System.out.println("=======================");
        } else {
            System.out.println("商品不存在，comId: " + comId);
            return "redirect:/main";
        }

        return "product"; // 返回视图名称
    }
    // 处理不带 ID 的默认路径
    @GetMapping
    public String defaultProductPage() {
        return "redirect:/product/1"; // 重定向到默认商品
    }

}
