package com.hugo.back.controller;

import com.hugo.back.service.UserService;
import com.hugo.common.entity.Paging;
import com.hugo.common.entity.User;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @RequestMapping("/save")
    @ResponseBody
    public Map<String, Object> save(User user, String code) {
        return userService.save(user, code);
    }

    @RequestMapping("/saveUserImage")
    @ResponseBody
    public Map<String, Object> saveUserImage(MultipartFile multipart, String userId, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = userService.queryOne(userId);
        //获取文件的原始名
        String fileName = multipart.getOriginalFilename();
        //获取文件的类型
        String type = multipart.getContentType();
        //获取文件的大小
        long size = multipart.getSize();
        //处理上传,获取文件要上传的路径
        //String realPath=request.getRealPath("/cmfz/upload");
        String realPath = request.getSession().getServletContext().getRealPath("/upload");
        //获取文件的后缀名不带点
        String extension = FilenameUtils.getExtension(fileName);
        //设置新的文件名
        String newFileName = UUID.randomUUID().toString().replace("-", "") + "." + extension;
        try {
            if (user.getImageUrl() != null) {
                File file = new File(user.getImageUrl());
                file.delete();
            }
            //创建新的文件
            multipart.transferTo(new File(realPath, newFileName));
            map = userService.saveUserImage("/cmfz/upload/" + newFileName, userId);
        } catch (Exception e) {
            File file = new File(realPath, newFileName);
            file.delete();
            map.put("success", false);
            map.put("msg", "上传失败...原因文件无法上传到服务器。。");
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("/remove")
    @ResponseBody
    public Map<String, Object> remove(String id) {
        return userService.remove(id);
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Map<String, Object> modify(User user) {
        return userService.modify(user);
    }

    @RequestMapping("/queryOne")
    @ResponseBody
    public User queryOne(String id) {
        User user = userService.queryOne(id);
        return user;
    }

    @RequestMapping("/queryOneByPhone")
    @ResponseBody
    public Map<String, Object> queryOneByPhone(User user) {
        Map<String, Object> map = new HashMap<String, Object>();

        try {
            User userDB = userService.queryOneByPhone(user.getPhone());
            String str = user.getPassword() + userDB.getSalt();
            if (DigestUtils.md5DigestAsHex(str.getBytes()).equals(userDB.getPassword())) {
                map.put("success", true);
                map.put("msg", "登录成功....");
            } else {
                throw new RuntimeException("密码错误...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @RequestMapping("/queryAll")
    @ResponseBody
    public Paging queryAll(Integer rows, Integer page) {
        //计算begin
        Integer begin = (page - 1) * rows;

        List<User> users = userService.queryAll(begin, rows);
        Integer count = userService.count();
        return new Paging(count, users);
    }

    @RequestMapping("/queryUser")
    @ResponseBody
    public List<User> queryUser(String guruId) {
        List<User> users = userService.queryUser(guruId);
        return users;
    }
}
