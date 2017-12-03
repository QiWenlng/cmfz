package com.hugo.back.service;

import com.hugo.common.entity.Banner;

import java.util.List;
import java.util.Map;

public interface BannerService {
    public Map<String, Object> save(Banner banner);

    public Map<String, Object> remove(String id);

    public Map<String, Object> modify(Banner banner);

    public Banner queryOne(String id);

    public List<Banner> queryAll(Integer begin, Integer rows);

    public Integer count();

    public List<Banner> query();
}
