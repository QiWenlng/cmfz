package com.hugo.back.controller;

import com.hugo.back.service.BannerService;
import com.hugo.common.entity.Banner;
import com.hugo.common.entity.Paging;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Controller
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    @Qualifier("bannerService")
    private BannerService bannerService;

    @RequestMapping("/save")
    @ResponseBody
    public Map<String, Object> save(Banner banner, MultipartFile multipart, HttpServletRequest request) {

        Map<String, Object> map = new HashMap<String, Object>();
        //获取文件的原始名
        String fileName = multipart.getOriginalFilename();
        //获取文件的类型
        String type = multipart.getContentType();
        //获取文件的大小
        long size = multipart.getSize();
        //处理上传,获取文件要上传的路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/banner");
        //获取文件的后缀名不带点
        String extension = FilenameUtils.getExtension(fileName);
        //设置新的文件名
        String newFileName = UUID.randomUUID().toString().replace("-", "") + "." + extension;
        try {
            //创建新的文件
            multipart.transferTo(new File(realPath, newFileName));
            banner.setImageUrl("/cmfz/upload/banner/" + newFileName);
            banner.setUploadDate(new Date());
            bannerService.save(banner);
            map.put("success", true);
            map.put("msg", "上传成功...");
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
        return bannerService.remove(id);
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Map<String, Object> modify(Banner banner) {
        return bannerService.modify(banner);
    }

    @RequestMapping("/queryOne")
    @ResponseBody
    public Banner queryOne(String id) {
        Banner banner = bannerService.queryOne(id);
        return banner;
    }

    @RequestMapping("/queryAll")
    @ResponseBody
    public Paging queryAll(Integer rows, Integer page) {
        //计算begin
        Integer begin = (page - 1) * rows;

        List<Banner> banners = bannerService.queryAll(begin, rows);
        Integer count = bannerService.count();
        return new Paging(count, banners);
    }

    @RequestMapping("/query")
    @ResponseBody
    public Banner query(String id) {
        Banner banner = bannerService.queryOne(id);
        return banner;
    }
}
