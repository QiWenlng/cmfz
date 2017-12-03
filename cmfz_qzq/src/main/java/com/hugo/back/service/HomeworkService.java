package com.hugo.back.service;

import com.hugo.common.entity.Homework;

import java.util.List;
import java.util.Map;

public interface HomeworkService {
    public Map<String, Object> save(Homework homework);

    public Map<String, Object> remove(String id);

    public Map<String, Object> modify(Homework homework);

    public Homework queryOne(String id);

    public List<Homework> queryAll(Integer begin, Integer rows, String userId);

    public Integer count(String userId);

    public List<Homework> query();
}
