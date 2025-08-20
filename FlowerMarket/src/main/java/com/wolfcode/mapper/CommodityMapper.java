package com.wolfcode.mapper;

import com.wolfcode.domain.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommodityMapper {
    /**
     * 根据主键查询商品
     * @param comId 商品ID
     * @return 商品实体对象
     */
    Commodity selectById(@Param("comId") Integer comId);

    List<Commodity> selectAll();

    List<Commodity> selectByDepartment(Integer comDepartment);
}