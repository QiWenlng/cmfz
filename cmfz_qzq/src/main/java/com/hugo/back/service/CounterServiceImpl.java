package com.hugo.back.service;

import com.hugo.common.annotations.Cache;
import com.hugo.common.annotations.FlushCache;
import com.hugo.common.dao.CounterDao;
import com.hugo.common.entity.Counter;
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

@Service("counterService")
@Transactional
public class CounterServiceImpl implements CounterService {

    @Autowired
    @Qualifier("counterDao")
    private CounterDao counterDao;

    private Map<String, Object> map = new HashMap<String, Object>();

    @FlushCache
    @Override
    public Map<String, Object> save(Counter counter) {
        counter.setCounterId(UUID.randomUUID().toString());
        try {
            String salt = SaltUtils.getSalt(10);
            counterDao.save(counter);
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
    public Map<String, Object> remove(String counterId) {
        try {
            counterDao.remove(counterId);
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
    public Map<String, Object> modify(Counter counter) {
        try {

            counterDao.modify(counter);
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
    public Counter queryOne(String counterId) {
        Counter counter = counterDao.queryOne(counterId);
        return counter;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Counter> queryAll(Integer begin, Integer rows, String hwId) {
        List<Counter> counters = counterDao.queryAll(begin, rows, hwId);
        return counters;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer count(String hwId) {
        Integer count = counterDao.count(hwId);
        return count;
    }
}
