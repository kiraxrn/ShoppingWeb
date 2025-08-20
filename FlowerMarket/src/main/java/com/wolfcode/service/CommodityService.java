package com.wolfcode.service;

import com.wolfcode.domain.Commodity;

import java.util.List;

public interface CommodityService {
    Commodity getById(Integer comId);

    List<Commodity> listAll();
    List<Commodity> listByDepartment(Integer comDepartment);
}
