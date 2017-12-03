package com.hugo.common.dao;

import com.hugo.common.entity.Counter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CounterDao extends BasicDao<Counter> {
    public Integer count(String hwId);

    public List<Counter> queryAll(@Param("begin") Integer begin, @Param("rows") Integer rows, @Param("hwId") String hwId);

}
