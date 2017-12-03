package com.hugo.common.dao;

import com.hugo.common.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerDao extends BasicDao<Banner> {
    public List<Banner> query();

    public Integer count();

    public List<Banner> queryAll(@Param("begin") Integer begin, @Param("rows") Integer rows);
}
