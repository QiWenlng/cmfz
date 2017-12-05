package com.hugo.common.dao;

import com.hugo.common.entity.Counter;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface CounterDao extends BasicDao<Counter> {
    @Cacheable(value = "baseCache", key = "#root.methodName")
    public Integer count(String hwId);

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public List<Counter> queryAll(@Param("begin") Integer begin, @Param("rows") Integer rows, @Param("hwId") String hwId);

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public List<Counter> query(@Param("begin") Integer begin, @Param("rows") Integer rows);

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public Integer countHw();
}
