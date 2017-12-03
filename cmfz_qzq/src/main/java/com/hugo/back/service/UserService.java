package com.hugo.back.service;

import com.hugo.common.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public Map<String, Object> save(User user, String code);

    public Map<String, Object> remove(String id);

    public Map<String, Object> modify(User user);

    public User queryOne(String id);

    public List<User> queryAll(Integer begin, Integer rows);

    public Integer count();

    public User queryOneByPhone(String phone);

    public Map<String, Object> saveUserImage(String imageUrl, String userId);

    public List<User> queryUser(String guruId);
}
