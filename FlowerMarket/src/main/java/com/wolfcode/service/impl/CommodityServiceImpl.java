package com.wolfcode.service.impl;

import com.wolfcode.domain.Commodity;
import com.wolfcode.mapper.CommodityMapper;
import com.wolfcode.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommodityServiceImpl implements CommodityService {

    private final CommodityMapper commodityMapper;

    @Autowired
    public CommodityServiceImpl(CommodityMapper commodityMapper) {
        this.commodityMapper = commodityMapper;
    }

    @Override
    public Commodity getById(Integer comId) {
        return commodityMapper.selectById(comId);
    }

    @Override
    public List<Commodity> listAll() {
        return commodityMapper.selectAll();
    }

    @Override
    public List<Commodity> listByDepartment(Integer comDepartment) {
        return commodityMapper.selectByDepartment(comDepartment);
    }

}