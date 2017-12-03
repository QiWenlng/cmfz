package com.hugo.back.controller;

import com.hugo.back.service.GuruService;
import com.hugo.common.entity.Guru;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/guru")
public class GuruController {
    @Autowired
    @Qualifier("guruService")
    private GuruService guruService;

    @RequestMapping("/save")
    @ResponseBody
    public Map<String, Object> save(Guru guru, MultipartFile multipart, HttpServletRequest request) {

        Map<String, Object> map = new HashMap<String, Object>();
        //获取文件的原始名
        String fileName = multipart.getOriginalFilename();
        //获取文件的类型
        String type = multipart.getContentType();
        //获取文件的大小
        long size = multipart.getSize();
        //处理上传,获取文件要上传的路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/guru");
        //获取文件的后缀名不带点
        String extension = FilenameUtils.getExtension(fileName);
        //设置新的文件名
        String newFileName = UUID.randomUUID().toString().replace("-", "") + "." + extension;
        try {
            //创建新的文件
            multipart.transferTo(new File(realPath, newFileName));
            guru.setGuruImageUrl("/cmfz/upload/guru/" + newFileName);
            map = guruService.save(guru);
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
        return guruService.remove(id);
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Map<String, Object> modify(Guru guru, MultipartFile multipart, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        //获取文件的原始名
        String fileName = multipart.getOriginalFilename();
        //获取文件的类型
        String type = multipart.getContentType();
        //获取文件的大小
        long size = multipart.getSize();
        //处理上传,获取文件要上传的路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/guru");
        //获取文件的后缀名不带点
        String extension = FilenameUtils.getExtension(fileName);
        //设置新的文件名
        String newFileName = UUID.randomUUID().toString().replace("-", "") + "." + extension;
        try {
            //创建新的文件
            multipart.transferTo(new File(realPath, newFileName));
            guru.setGuruImageUrl("/cmfz/upload/guru/" + newFileName);
            map = guruService.modify(guru);
        } catch (Exception e) {
            File file = new File(realPath, newFileName);
            file.delete();
            map.put("success", false);
            map.put("msg", "上传失败...原因文件无法上传到服务器。。");
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping("/queryOne")
    @ResponseBody
    public Guru queryOne(String guruId) {
        Guru guru = guruService.queryOne(guruId);
        return guru;
    }

    @RequestMapping("/queryAll")
    @ResponseBody
    public Paging queryAll(Integer rows, Integer page) {
        //计算begin
        Integer begin = (page - 1) * rows;

        List<Guru> gurus = guruService.queryAll(begin, rows);
        Integer count = guruService.count();
        return new Paging(count, gurus);
    }

    @RequestMapping("/query")
    @ResponseBody
    public Guru query(String guruId) {
        Guru guru = guruService.queryOne(guruId);
        return guru;
    }

    @RequestMapping("queryAllGuru")
    @ResponseBody
    public List<Guru> queryAllGuru() {
        return guruService.queryAllGuru();
    }
}
