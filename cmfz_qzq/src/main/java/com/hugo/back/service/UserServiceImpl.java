package com.hugo.back.service;

import com.hugo.common.annotations.Cache;
import com.hugo.common.annotations.FlushCache;
import com.hugo.common.dao.UserDao;
import com.hugo.common.entity.User;
import com.hugo.common.utils.SaltUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("userDao")
    private UserDao userDao;

    @Autowired
    private Jedis jedis;


    private Map<String, Object> map = new HashMap<String, Object>();


    @FlushCache
    @Override
    public Map<String, Object> save(User user, String code) {
        user.setUserId(UUID.randomUUID().toString());
        try {
            //从redis中获取验证码，判断验证码
            String imageVerifyCode = jedis.get("imageVerifyCode");
            if (imageVerifyCode != null) {
                if (code.equalsIgnoreCase(imageVerifyCode)) {
                    String salt = SaltUtils.getSalt(10);
                    String str = user.getPassword() + salt;
                    user.setSalt(salt);
                    user.setPassword(DigestUtils.md5DigestAsHex(str.getBytes()));
                    userDao.save(user);
                    map.put("success", true);
                    map.put("msg", "添加成功");
                } else {
                    throw new RuntimeException("验证码错误");
                }
            } else {
                throw new RuntimeException("验证码失效...");
            }
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "添加失败\n" + e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @FlushCache
    @Override
    public Map<String, Object> remove(String id) {
        try {
            userDao.remove(id);
            map.put("success", true);
            map.put("msg", "删除成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "删除失败\n" + e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @FlushCache
    @Override
    public Map<String, Object> modify(User user) {
        try {
            String salt = SaltUtils.getSalt(10);
            String str = user.getPassword() + salt;
            user.setSalt(salt);
            user.setPassword(DigestUtils.md5DigestAsHex(str.getBytes()));
            userDao.modify(user);
            map.put("success", true);
            map.put("msg", "修改成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "修改失败\n" + e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User queryOne(String guruId) {
        User user = userDao.queryOne(guruId);
        return user;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<User> queryAll(Integer begin, Integer rows) {
        List<User> users = userDao.queryAll(begin, rows);
        return users;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer count() {
        Integer count = userDao.count();
        return count;
    }

    @Override
    @Cache
    @Transactional(propagation = Propagation.SUPPORTS)
    public User queryOneByPhone(String phone) {
        User userDB = userDao.queryOneByPhone(phone);
        //存入redis中
        jedis.set(DigestUtils.md5DigestAsHex(userDB.getPhone().getBytes()), userDB.getUserId());
        return userDB;
    }

    @FlushCache
    @Override
    public Map<String, Object> saveUserImage(String imageUrl, String userId) {
        try {
            userDao.saveUserImage(imageUrl, userId);
            map.put("success", true);
            map.put("msg", "上传成功....");
        } catch (Exception e) {
            map.put("success", true);
            map.put("msg", "上传失败...原因保存路径出错");
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<User> queryUser(String guruId) {
        List<User> users = userDao.queryUser(guruId);
        return users;
    }
}
