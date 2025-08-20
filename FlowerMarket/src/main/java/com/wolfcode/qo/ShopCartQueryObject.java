package com.wolfcode.qo;

import lombok.Data;

@Data
public class ShopCartQueryObject extends QueryObject {
    private Integer userId;  // 当前用户ID
    private Boolean isChecked; // 选中状态过滤
    private String commodityName; // 商品名称模糊查询
}