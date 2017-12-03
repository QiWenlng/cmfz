package com.hugo.back.controller;

import com.hugo.back.service.AdminService;
import com.hugo.common.entity.Admin;
import com.hugo.common.utils.VerifyCodeUtil;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    @Qualifier("adminService")
    private AdminService adminService;

    @Autowired
    @Qualifier("jedis")
    private Jedis jedis;

    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(Admin admin, HttpSession session) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Admin adminDB = adminService.queryOne(admin.getName());
            if (admin.getPassword().equals(adminDB.getPassword())) {
                //放入管理员信息到session
                session.setAttribute("name", adminDB.getName());
                result.put("success", true);
                result.put("msg", "登录成功....");
            } else {
                throw new RuntimeException("密码错误...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @RequestMapping("/logout")
    @ResponseBody
    public void logout(HttpSession session) {
        //Map<String,String> result = new HashMap<String,String>();
        session.setAttribute("name", null);
        //result.put("msg","登出成功");
        // return result;
    }


    @RequestMapping("/register")
    @ResponseBody
    public Map<String, Object> register(HttpSession session, Admin admin, String code) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            //1.获取redis中验证码
            String imageVerifyCode = jedis.get("imageVerifyCode");
            if (imageVerifyCode != null) {
                //比较验证验证码
                if (imageVerifyCode.equalsIgnoreCase(code)) {
                    adminService.save(admin);
                    result.put("success", true);
                    result.put("msg", "注册成功~~~");
                } else {
                    throw new RuntimeException("验证码不匹配...");
                }
            } else {
                throw new RuntimeException("验证码失效...");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("msg", e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 生成验证码的方法
     */
    @RequestMapping("/getImage")
    public void getImage(HttpSession session, HttpServletResponse response) throws IOException {

        //生成验证码
        String code = VerifyCodeUtil.generateVerifyCode(4);
        //放入redis中
        jedis.setex("imageVerifyCode", 180, code);
        //生成验证码图片
        BufferedImage image = VerifyCodeUtil.getImage(120, 40, code);
        //响应图片到页面
        //设置响应类型
        response.setContentType("image/png");
        ServletOutputStream os = response.getOutputStream();
        //输出图片
        ImageIO.write(image, "png", os);
        //关闭流
        IOUtils.closeQuietly(os);
    }
}
