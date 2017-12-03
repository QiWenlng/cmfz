package com.hugo.common.dao;

import com.hugo.common.entity.Guru;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GuruDao extends BasicDao<Guru> {
    public Integer count();

    public List<Guru> queryAllGuru();

    public List<Guru> queryAll(@Param("begin") Integer begin, @Param("rows") Integer rows);
}
