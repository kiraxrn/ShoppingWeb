package com.wolfcode.service;

import com.wolfcode.domain.ShopCart;

import java.util.List;

public interface ShopCartService {
    List<ShopCart> getCurrentUserCart(String userId);
    void updateQuantity(Integer shopcartId, Integer quantity);
    void deleteItem(Integer shopcartId);
    void toggleCheck(Integer shopcartId, Boolean isChecked);

    String insert(String userId, ShopCart cartItemDTO);
}
