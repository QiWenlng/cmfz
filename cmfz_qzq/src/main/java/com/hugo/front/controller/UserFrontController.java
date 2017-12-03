package com.hugo.front.controller;

import com.hugo.common.entity.User;
import com.hugo.common.utils.MessageCode;
import com.hugo.common.utils.VerifyCodeUtil;
import com.hugo.front.service.UserFrontService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

/**
 * 与用户相关的控制器类
 * <p>
 * //springmvc 默认单例   线程不安全
 */
@Controller
@RequestMapping("/userFront")
public class UserFrontController {


    @Autowired
    private UserFrontService userService;

    @Autowired
    private Jedis jedis;


    /**
     * 用户登录的控制器方法
     */
    @RequestMapping("/login")
    @ResponseBody
    public Map<String, Object> login(User user, HttpSession session) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            User userDB = userService.login(user);
            //放入用户信息到session
            session.setAttribute("user", user);
            result.put("success", true);
            result.put("msg", "登录成功....");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("msg", e.getMessage());
        }
        return result;
    }

    @RequestMapping("/sendMessage")
    @ResponseBody
    public Map<String, Object> sendMesageCode(@RequestParam(value = "phone", required = true) String phone) {
        Map<String, Object> result = new HashMap<String, Object>();
        //发送短信
        try {
            MessageCode.sendMessageCode(phone);
            result.put("success", true);
            result.put("msg", "验证码已发送...");
        } catch (Exception e) {
            result.put("success", false);
            result.put("msg", "发送失败");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 用户登录的控制器方法
     */
    @RequestMapping("/loginMessage")
    @ResponseBody
    public Map<String, Object> loginMessage(String phone, String messageCode, HttpSession session) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            User userDB = userService.loginMessage(phone);
            //判断手机号码是否正确
            if (userDB != null) {
                //获取数据库中验证码
                String messageCodeDB = jedis.get(DigestUtils.md5DigestAsHex((phone + "66").getBytes()));
                //判断验证码是否有误
                if (messageCodeDB.equals(messageCode)) {
                    //放入用户信息到redis
                    jedis.set(DigestUtils.md5DigestAsHex(userDB.getPhone().getBytes()), userDB.getUserId());
                    result.put("success", true);
                    result.put("msg", "登录成功....");
                } else {
                    result.put("success", false);
                    result.put("msg", "验证码有误，请确认后输入。。");
                }

            } else {
                result.put("success", false);
                result.put("msg", "绑定手机有误，请确认后输入。。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("success", false);
            result.put("msg", e.getMessage());
        }
        return result;
    }


    /**
     * 保存用户
     *
     * @param user 接收前台提交参数
     * @param code 接收验证码
     * @return 跳转页面
     */
    @RequestMapping("/resgister")
    @ResponseBody
    public Map<String, Object> register(HttpSession session, User user, String code) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            //1.获取session验证码
            String sessionCode = (String) session.getAttribute("code");
            //比较验证验证码
            if (sessionCode.equalsIgnoreCase(code)) {
                userService.save(user);
                result.put("success", true);
                result.put("msg", "注册成功~~~");
            } else {
                throw new RuntimeException("验证码不匹配...");
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
        //放入session中
        session.setAttribute("code", code);
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
