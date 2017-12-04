package com.hugo.common.dao;

import com.hugo.common.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface UserDao extends BasicDao<User> {
    @Cacheable(value = "baseCache", key = "#root.methodName")
    public Integer count();

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public List<User> queryUser(String guruId);

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public User queryOneByPhone(String phone);

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public User queryByName(String name);

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public List<User> queryAll(@Param("begin") Integer begin, @Param("rows") Integer rows);

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public void saveUserImage(@Param("imageUrl") String imageUrl, @Param("userId") String userId);
}
