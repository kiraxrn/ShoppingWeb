package com.wolfcode.domain;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@TableName("shopcart")
@EqualsAndHashCode(callSuper = false)
public class ShopCart implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "shopcart_id", type = IdType.AUTO)
    private Integer shopcartId;

    @TableField("user_id")
    private String userId;

    @TableField("com_id")
    private Integer comId;

    private Integer quantity;

    @TableField("is_checked")
    private Boolean checked;

    @TableField(exist = false) // 非数据库字段 ,关联的商品对象（
    private Commodity commodity;

    public Commodity getCommodity() {
        return commodity;
    }

    public void setCommodity(Commodity commodity) {
        this.commodity = commodity;
    }


}

