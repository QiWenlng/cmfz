package com.hugo.back.service;

import com.hugo.common.annotations.Cache;
import com.hugo.common.annotations.FlushCache;
import com.hugo.common.dao.ChapterDao;
import com.hugo.common.entity.Chapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("chapterService")
@Transactional
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    @Qualifier("chapterDao")
    private ChapterDao chapterDao;

    private Map<String, Object> map = new HashMap<String, Object>();


    @FlushCache
    @Override
    public Map<String, Object> save(Chapter chapter) {
        chapter.setChapterId(UUID.randomUUID().toString());
        try {
            chapterDao.save(chapter);
            map.put("success", true);
            map.put("msg", "添加成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "添加失败\n" + e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @FlushCache
    @Override
    public Map<String, Object> remove(String chapterId) {
        try {
            chapterDao.remove(chapterId);
            map.put("success", true);
            map.put("msg", "删除成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "删除失败\n" + e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @FlushCache
    @Override
    public Map<String, Object> modify(Chapter chapter) {
        try {
            chapterDao.modify(chapter);
            map.put("success", true);
            map.put("msg", "修改成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "修改失败\n" + e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Chapter queryOne(String chapterId) {
        Chapter chapter = chapterDao.queryOne(chapterId);
        return chapter;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Chapter> queryAll(Integer begin, Integer rows) {
        List<Chapter> chapters = chapterDao.queryAll(begin, rows);
        return chapters;
    }

    @Override
    public List<Chapter> queryByAlbum(Integer begin, Integer rows, String albumId) {
        List<Chapter> chapters = chapterDao.queryByAlbum(begin, rows, albumId);
        return chapters;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer countByAlbum(String albumId) {
        Integer count = chapterDao.countByAlbum(albumId);
        return count;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Chapter> query() {
        List<Chapter> chapters = chapterDao.query();
        return chapters;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer count() {
        Integer count = chapterDao.count();
        return count;
    }
}
