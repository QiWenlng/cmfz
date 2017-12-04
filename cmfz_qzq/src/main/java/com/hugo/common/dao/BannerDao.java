package com.hugo.common.dao;

import com.hugo.common.entity.Banner;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface BannerDao extends BasicDao<Banner> {
    @Cacheable(value = "baseCache", key = "#root.methodName")
    public List<Banner> query();

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public Integer count();

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public List<Banner> queryAll(@Param("begin") Integer begin, @Param("rows") Integer rows);
}
