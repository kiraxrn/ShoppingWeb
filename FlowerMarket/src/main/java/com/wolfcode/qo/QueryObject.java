package com.wolfcode.qo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//分页查询条件的父类
@Data
@NoArgsConstructor //无参构造
@AllArgsConstructor //全参构造
public class QueryObject {
    //当前页
    private int currentPage = 1;
    //每页显示条数
    private int pageSize = 3;

}
