package com.hugo.back.service;

import com.hugo.common.annotations.Cache;
import com.hugo.common.annotations.FlushCache;
import com.hugo.common.dao.AlbumDao;
import com.hugo.common.entity.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("albumService")
@Transactional
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    @Qualifier("albumDao")
    private AlbumDao albumDao;

    private Map<String, Object> map = new HashMap<String, Object>();

    @FlushCache
    @Override
    public Map<String, Object> save(Album album) {
        album.setAlbumId(UUID.randomUUID().toString());
        try {
            albumDao.save(album);
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
    public Map<String, Object> remove(String albumId) {
        try {
            albumDao.remove(albumId);
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
    public Map<String, Object> modify(Album album) {
        try {
            albumDao.modify(album);
            map.put("success", true);
            map.put("msg", "修改成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "修改失败");
            e.printStackTrace();
        }
        return map;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Album queryOne(String albumId) {
        Album album = albumDao.queryOne(albumId);
        return album;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Album> queryAll(Integer begin, Integer rows) {
        List<Album> albums = albumDao.queryAll(begin, rows);
        return albums;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer count() {
        Integer count = albumDao.count();
        return count;
    }

    @Override
    public List<Album> queryAlbum() {
        List<Album> albums = albumDao.queryAlbum();
        return albums;
    }
}
