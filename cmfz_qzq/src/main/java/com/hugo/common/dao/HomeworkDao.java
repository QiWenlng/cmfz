package com.hugo.common.dao;

import com.hugo.common.entity.Homework;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HomeworkDao extends BasicDao<Homework> {
    public Integer count(String userId);

    public List<Homework> query();

    public List<Homework> queryAll(@Param("begin") Integer begin, @Param("rows") Integer rows, @Param("userId") String userId);
}
