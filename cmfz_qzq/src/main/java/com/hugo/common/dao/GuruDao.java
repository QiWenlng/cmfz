package com.hugo.common.dao;

import com.hugo.common.entity.Guru;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface GuruDao extends BasicDao<Guru> {
    @Cacheable(value = "baseCache", key = "#root.methodName")
    public Integer count();

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public List<Guru> queryAllGuru();

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public List<Guru> queryAll(@Param("begin") Integer begin, @Param("rows") Integer rows);
}
