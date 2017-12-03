package com.hugo.back.service;

import com.hugo.common.annotations.Cache;
import com.hugo.common.dao.ProvinceDao;
import com.hugo.common.entity.City;
import com.hugo.common.entity.Province;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("provinceService")
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    @Qualifier("provinceDao")
    private ProvinceDao provinceDao;

    @Cache
    @Override
    public List<Province> queryProvinceAllService() {
        List<Province> lists = provinceDao.queryProvinceAll();
        return lists;
    }

    @Cache
    @Override
    public List<City> queryCityByCodeService(String code) {
        List<City> lists = provinceDao.queryCityByCode(code);
        return lists;
    }

	/*@Override
    public Province queryProvinceName(String code) {
		Province province = provinceDao.queryProvinceName(code);
		return province;
	}

	@Override
	public City queryCityName(String code) {
		City city = provinceDao.queryCityName(code);
		return city;
	}*/
}
