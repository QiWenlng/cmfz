package com.hugo.common.dao;

import com.hugo.common.entity.Homework;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface HomeworkDao extends BasicDao<Homework> {
    @Cacheable(value = "baseCache", key = "#root.methodName")
    public Integer count(String userId);

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public List<Homework> query();

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public List<Homework> queryAll(@Param("begin") Integer begin, @Param("rows") Integer rows, @Param("userId") String userId);
}
