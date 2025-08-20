package com.wolfcode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wolfcode.domain.ShopCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShopCartMapper extends BaseMapper<ShopCart> {

    ShopCart selectById(Integer cartId);

    int deleteById(Integer cartId);

    int updateById(ShopCart cartItem);

    List<ShopCart> selectByUserId(String userId);

    int insert(ShopCart newItem);
}