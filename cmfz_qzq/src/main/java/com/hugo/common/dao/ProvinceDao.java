package com.hugo.common.dao;

import com.hugo.common.entity.City;
import com.hugo.common.entity.Province;

import java.util.List;

public interface ProvinceDao extends BasicDao<Province> {
    public List<Province> queryProvinceAll();

    public List<City> queryCityByCode(String code);
}
