package com.hugo.back.service;

import com.hugo.common.entity.Guru;

import java.util.List;
import java.util.Map;

public interface GuruService {
    public Map<String, Object> save(Guru guru);

    public Map<String, Object> remove(String guruId);

    public Map<String, Object> modify(Guru guru);

    public Guru queryOne(String guruId);

    public List<Guru> queryAll(Integer begin, Integer rows);

    public Integer count();

    public List<Guru> queryAllGuru();
}
