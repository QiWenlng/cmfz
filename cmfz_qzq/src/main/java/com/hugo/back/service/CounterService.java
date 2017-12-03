package com.hugo.back.service;

import com.hugo.common.entity.Counter;

import java.util.List;
import java.util.Map;

public interface CounterService {
    public Map<String, Object> save(Counter counter);

    public Map<String, Object> remove(String counterId);

    public Map<String, Object> modify(Counter counter);

    public Counter queryOne(String counterId);

    public List<Counter> queryAll(Integer begin, Integer rows, String hwId);

    public Integer count(String hwId);
}
