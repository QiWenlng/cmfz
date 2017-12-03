package com.hugo.front.service;

import com.hugo.common.annotations.Cache;
import com.hugo.common.annotations.FlushCache;
import com.hugo.common.dao.UserDao;
import com.hugo.common.entity.User;
import com.hugo.common.utils.SaltUtils;
import com.hugo.common.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

/**
 * 与用户相关的业务实现类
 */
@Service("userServiceFront")
@Transactional
public class UserFrontServiceImpl implements UserFrontService {

    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;


    @FlushCache
    public void save(User user) {
        //处理核心业务
        User userDB = userDao.queryByName(user.getName());
        if (userDB == null) {
            //保存用户
            //1.生成id
            user.setUserId(UUIDUtils.getId());
            //2.生成salt
            String salt = SaltUtils.getSalt(8);
            //3.生成MD5密码   明文+salt 做md5加密
            String md5pwd = DigestUtils.md5DigestAsHex((user.getPassword() + salt).getBytes());
            //4.保存新的密码
            user.setPassword(md5pwd);
            //5.保存盐
            user.setSalt(salt);
            //6.保存状态
            user.setUserStatus("Y");
            //7.保存用户
            userDao.save(user);

        } else {
            throw new RuntimeException("用户名已经存在~~~");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Cache
    public User login(User user) {

        //1.根据用户查询用户
        User userDB = userDao.queryByName(user.getName());
        if (userDB != null) {
            //2.验证密码
            //3.将用户前台输入明文密码+ 数据库中的salt  进行MD5加密
            String md5DigestAsHex = DigestUtils.md5DigestAsHex((user.getPassword() + userDB.getSalt()).getBytes());
            //4.比较md5密码与数据库用户的密码
            if (md5DigestAsHex.equals(userDB.getPassword())) {
                return userDB;
            }
            throw new RuntimeException("密码输入错误....");
        }
        throw new RuntimeException("用户名不存在~~~");
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Cache
    public User loginMessage(String phone) {

        //1.根据手机查询用户
        User userDB = userDao.queryOneByPhone(phone);
        if (userDB != null) {
            return userDB;
        }
        throw new RuntimeException("账号不存在~~~");
    }
}
