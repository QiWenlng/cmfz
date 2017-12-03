package com.hugo.common.dao;

import com.hugo.common.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumDao extends BasicDao<Album> {
    public List<Album> queryAll(@Param("begin") Integer begin, @Param("rows") Integer rows);

    public Integer count();

    public List<Album> queryAlbum();
}
