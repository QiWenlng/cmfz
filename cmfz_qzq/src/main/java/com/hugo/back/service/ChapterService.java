package com.hugo.back.service;

import com.hugo.common.entity.Chapter;

import java.util.List;
import java.util.Map;

public interface ChapterService {
    public Map<String, Object> save(Chapter chapter);

    public Map<String, Object> remove(String chapterId);

    public Map<String, Object> modify(Chapter chapter);

    public Chapter queryOne(String chapterId);

    public List<Chapter> queryAll(Integer begin, Integer rows);

    public List<Chapter> queryByAlbum(Integer begin, Integer rows, String albumId);

    public Integer count();

    public Integer countByAlbum(String albumId);

    public List<Chapter> query();
}
