package com.hugo.back.service;

import com.hugo.common.annotations.Cache;
import com.hugo.common.annotations.FlushCache;
import com.hugo.common.dao.BannerDao;
import com.hugo.common.entity.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("bannerService")
@Transactional
public class BannerServiceImpl implements BannerService {

    @Autowired
    @Qualifier("bannerDao")
    private BannerDao bannerDao;

    @Autowired
    private Jedis jedis;


    private Map<String, Object> map = new HashMap<String, Object>();


    @FlushCache
    @Override
    public Map<String, Object> save(Banner banner) {
        banner.setId(UUID.randomUUID().toString());
        try {
            bannerDao.save(banner);
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
    public Map<String, Object> remove(String id) {
        try {
            bannerDao.remove(id);
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
    public Map<String, Object> modify(Banner banner) {
        try {
            bannerDao.modify(banner);
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
    public Banner queryOne(String id) {
        Banner banner = bannerDao.queryOne(id);
        return banner;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Banner> queryAll(Integer begin, Integer rows) {
        List<Banner> banners = bannerDao.queryAll(begin, rows);
        return banners;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer count() {
        Integer count = bannerDao.count();
        return count;
    }

    @Override
    public List<Banner> query() {
        List<Banner> banners = bannerDao.query();
        return banners;
    }
}
