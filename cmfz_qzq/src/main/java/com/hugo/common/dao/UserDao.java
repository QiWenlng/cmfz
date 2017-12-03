package com.hugo.common.dao;

import com.hugo.common.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao extends BasicDao<User> {
    public Integer count();

    public List<User> queryUser(String guruId);

    public User queryOneByPhone(String phone);

    public User queryByName(String name);

    public List<User> queryAll(@Param("begin") Integer begin, @Param("rows") Integer rows);

    public void saveUserImage(@Param("imageUrl") String imageUrl, @Param("userId") String userId);
}
