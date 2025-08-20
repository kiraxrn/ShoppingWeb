package com.wolfcode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wolfcode.domain.Commodity;
import com.wolfcode.domain.ShopCart;
import com.wolfcode.mapper.ShopCartMapper;
import com.wolfcode.service.CommodityService;
import com.wolfcode.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopCartServiceImpl implements ShopCartService {

    @Autowired
    private ShopCartMapper shopCartMapper;
    @Autowired
    private CommodityService commodityService;


    @Override
    public List<ShopCart> getCurrentUserCart(String userId) {
        // 查询逻辑
        return shopCartMapper.selectByUserId(userId);
    }

    @Override
    public void updateQuantity(Integer shopcartId, Integer quantity) {
        ShopCart cartItem = new ShopCart();
        cartItem.setShopcartId(shopcartId);
        cartItem.setQuantity(quantity);
        shopCartMapper.updateById(cartItem); 
    }

    @Override
    public void deleteItem(Integer shopcartId) {
        shopCartMapper.deleteById(shopcartId);
    }

    @Override
    public void toggleCheck(Integer shopcartId, Boolean isChecked) {
        ShopCart cartItem = new ShopCart();
        cartItem.setShopcartId(shopcartId);
        cartItem.setChecked(isChecked);
        shopCartMapper.updateById(cartItem);
    }

    @Override
    public String insert(String userId, ShopCart cart) {
        // 1. 检查商品是否存在
        Commodity commodity = commodityService.getById(cart.getComId());
        if (commodity == null) {
            return "商品不存在";
        }
        // 2. 使用 QueryWrapper 查询
        LambdaQueryWrapper<ShopCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShopCart::getUserId, userId)
                .eq(ShopCart::getComId, cart.getComId());

        ShopCart existingItem = shopCartMapper.selectOne(queryWrapper);
        // 3. 更新或插入
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + cart.getQuantity());
            shopCartMapper.updateById(existingItem);
        } else {
            ShopCart newItem = new ShopCart();
            newItem.setUserId(userId);
            newItem.setComId(cart.getComId());
            newItem.setQuantity(cart.getQuantity());
            newItem.setChecked(true);
            shopCartMapper.insert(newItem);
        }
        return null;
    }
}