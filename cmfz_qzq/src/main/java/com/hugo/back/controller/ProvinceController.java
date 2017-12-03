package com.hugo.back.controller;

import com.hugo.back.service.ProvinceService;
import com.hugo.common.entity.City;
import com.hugo.common.entity.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/province")
public class ProvinceController {

    @Autowired
    @Qualifier("provinceService")
    private ProvinceService provinceService;

    @RequestMapping("/queryProvince")
    @ResponseBody
    public List<Province> queryProvince() {
        List<Province> provinces = provinceService.queryProvinceAllService();
        return provinces;
    }

    @RequestMapping("queryCity")
    @ResponseBody
    public List<City> queryCity(String code) {
        List<City> cities = provinceService.queryCityByCodeService(code);
        return cities;
    }
}
