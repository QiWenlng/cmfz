package com.hugo.back.service;

import com.hugo.common.annotations.Cache;
import com.hugo.common.annotations.FlushCache;
import com.hugo.common.dao.HomeworkDao;
import com.hugo.common.entity.Homework;
import com.hugo.common.utils.SaltUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("homeworkService")
@Transactional
public class HomeworkServiceImpl implements HomeworkService {

    @Autowired
    @Qualifier("homeworkDao")
    private HomeworkDao homeworkDao;

    private Map<String, Object> map = new HashMap<String, Object>();

    @FlushCache
    @Override
    public Map<String, Object> save(Homework homework) {
        homework.setHwId(UUID.randomUUID().toString());
        try {
            String salt = SaltUtils.getSalt(10);
            homeworkDao.save(homework);
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
    public Map<String, Object> remove(String hwId) {
        try {
            homeworkDao.remove(hwId);
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
    public Map<String, Object> modify(Homework homework) {
        try {
            homeworkDao.modify(homework);
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
    public Homework queryOne(String hwId) {
        Homework homework = homeworkDao.queryOne(hwId);
        return homework;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Homework> queryAll(Integer begin, Integer rows, String userId) {
        List<Homework> homeworks = homeworkDao.queryAll(begin, rows, userId);
        return homeworks;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer count(String userId) {
        Integer count = homeworkDao.count(userId);
        return count;
    }

    @Override
    public List<Homework> query() {
        List<Homework> homeworks = homeworkDao.query();
        return homeworks;
    }
}
