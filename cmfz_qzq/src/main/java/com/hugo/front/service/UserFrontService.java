package com.hugo.front.service;


import com.hugo.common.entity.User;

/**
 * 和用户相关的一组业务接口类
 */
public interface UserFrontService {


    /**
     * 保存用户的业务方法
     */
    public void save(User user);


    /**
     * 用户登录的业务方法
     */
    public User login(User user);


    public User loginMessage(String phone);

}
