package com.hugo.common.dao;

import com.hugo.common.entity.Album;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface AlbumDao extends BasicDao<Album> {

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public List<Album> queryAll(@Param("begin") Integer begin, @Param("rows") Integer rows);

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public Integer count();

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public List<Album> queryAlbum();
}
