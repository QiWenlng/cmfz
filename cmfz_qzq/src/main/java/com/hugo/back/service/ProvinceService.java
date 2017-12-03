package com.hugo.back.service;

import com.hugo.common.entity.City;
import com.hugo.common.entity.Province;

import java.util.List;

public interface ProvinceService {
    public List<Province> queryProvinceAllService();

    public List<City> queryCityByCodeService(String code);



	/*public Province queryProvinceName(String code);
    public City queryCityName(String code);*/

}
