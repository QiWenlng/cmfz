package com.hugo.back.controller;

import com.hugo.back.service.CounterService;
import com.hugo.common.entity.Counter;
import com.hugo.common.entity.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/counter")
public class CounterController {
    @Autowired
    @Qualifier("counterService")
    private CounterService counterService;

    @Autowired
    private Jedis jedis;

    @RequestMapping("/save")
    @ResponseBody
    public Map<String, Object> save(Counter counter, String phone) {
        String jsonUserId = jedis.get(DigestUtils.md5DigestAsHex(phone.getBytes()));
        String hwId = jedis.get(phone);
        counter.setHwId(hwId);
        counter.setUserId(jsonUserId);
        counter.setCount(0);
        counter.setUpdateDate(new Date());
        return counterService.save(counter);
    }

    @RequestMapping("/remove")
    @ResponseBody
    public Map<String, Object> remove(String counterId) {
        return counterService.remove(counterId);
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Map<String, Object> modify(Counter counter) {
        Integer count = counter.getCount();
        if (counter.getCounterStatus().equals("0")) {
            counter = counterService.queryOne(counter.getCounterId());
            counter.setCount(count + counter.getCount());
        } else if ((counter.getCounterStatus().equals("1"))) {
            counter = counterService.queryOne(counter.getCounterId());
            counter.setCount(counter.getCount() - count);
        } else {
            counter = counterService.queryOne(counter.getCounterId());
            counter.setCount(counter.getCount() + 1);
        }
        counter.setUpdateDate(new Date());
        return counterService.modify(counter);
    }

    @RequestMapping("/queryOne")
    @ResponseBody
    public Counter queryOne(String counterId) {
        Counter counter = counterService.queryOne(counterId);
        return counter;
    }

    @RequestMapping("/queryAll")
    @ResponseBody
    public Paging queryAll(Integer rows, Integer page, String phone) {
        //计算begin
        Integer begin = (page - 1) * rows;
        String hwId = jedis.get(phone);
        List<Counter> counters = counterService.queryAll(begin, rows, hwId);
        Integer count = counterService.count(hwId);
        return new Paging(count, counters);
    }

    @RequestMapping("/query")
    @ResponseBody
    public Paging query(Integer rows, Integer page) {
        //计算begin
        Integer begin = (page - 1) * rows;
        List<Counter> counters = counterService.query(begin, rows);
        Integer count = counterService.countHw();
        return new Paging(count, counters);
    }

    @RequestMapping("/savehwId")
    @ResponseBody
    public String savehwId(String hwId, String phone) {
        jedis.set(phone, hwId);
        return "";
    }
}
