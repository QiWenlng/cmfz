package com.hugo.common.dao;

import com.hugo.common.entity.Chapter;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface ChapterDao extends BasicDao<Chapter> {
    @Cacheable(value = "baseCache", key = "#root.methodName")
    public List<Chapter> queryAll(@Param("begin") Integer begin, @Param("rows") Integer rows);

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public List<Chapter> queryByAlbum(@Param("begin") Integer begin, @Param("rows") Integer rows, @Param("albumId") String albumId);

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public Integer count();

    //@Cacheable(value = "baseCache", key = "#root.methodName")
    public Integer countByAlbum(String albumId);

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public List<Chapter> query();
}
