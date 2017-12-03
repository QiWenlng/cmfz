package com.hugo.back.service;

import com.hugo.common.entity.Album;

import java.util.List;
import java.util.Map;

public interface AlbumService {
    public Map<String, Object> save(Album album);

    public Map<String, Object> remove(String albumId);

    public Map<String, Object> modify(Album album);

    public Album queryOne(String albumId);

    public List<Album> queryAll(Integer begin, Integer rows);

    public Integer count();

    public List<Album> queryAlbum();
}
