package com.wolfcode.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class Commodity implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 商品编号
     */
    @TableId("comId")
    private Integer comId;

    /**
     * 商品名称
     */
    @TableField("comName")
    private String comName;

    @TableField("comPrice")
    private Double comPrice;

    /**
     * 商品图片url
     */
    @Getter
    @TableField("comPicture")
    private String comPicture;

    /**
     * 商品展区分类
     */
    @TableId("comDepartment")
    private Integer comDepartment;

    public String getComPicture() {return comPicture;}
    public String setComPicture() {return comPicture;}

}
