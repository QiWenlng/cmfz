package com.hugo.back.service;

import com.hugo.common.annotations.Cache;
import com.hugo.common.annotations.FlushCache;
import com.hugo.common.dao.GuruDao;
import com.hugo.common.entity.Guru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("guruService")
@Transactional
public class GuruServiceImpl implements GuruService {

    @Autowired
    @Qualifier("guruDao")
    private GuruDao guruDao;

    private Map<String, Object> map = new HashMap<String, Object>();


    @FlushCache
    @Override
    public Map<String, Object> save(Guru guru) {
        guru.setGuruId(UUID.randomUUID().toString());
        try {
            guruDao.save(guru);
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
    public Map<String, Object> remove(String guruId) {
        try {
            guruDao.remove(guruId);
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
    public Map<String, Object> modify(Guru guru) {
        try {
            guruDao.modify(guru);
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
    public Guru queryOne(String guruId) {
        Guru guru = guruDao.queryOne(guruId);
        return guru;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Guru> queryAll(Integer begin, Integer rows) {
        List<Guru> gurus = guruDao.queryAll(begin, rows);
        return gurus;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer count() {
        Integer count = guruDao.count();
        return count;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Guru> queryAllGuru() {
        List<Guru> gurus = guruDao.queryAllGuru();
        return gurus;
    }


}
