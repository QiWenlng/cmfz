package com.hugo.common.dao;

import com.hugo.common.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDao extends BasicDao<Chapter> {
    public List<Chapter> queryAll(@Param("begin") Integer begin, @Param("rows") Integer rows);

    public List<Chapter> queryByAlbum(@Param("begin") Integer begin, @Param("rows") Integer rows, @Param("albumId") String albumId);

    public Integer count();

    public Integer countByAlbum(String albumId);

    public List<Chapter> query();
}
