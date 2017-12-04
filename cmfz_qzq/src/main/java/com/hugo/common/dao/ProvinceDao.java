package com.hugo.common.dao;

import com.hugo.common.entity.City;
import com.hugo.common.entity.Province;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface ProvinceDao extends BasicDao<Province> {
    @Cacheable(value = "baseCache", key = "#root.methodName")
    public List<Province> queryProvinceAll();

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public List<City> queryCityByCode(String code);
}
