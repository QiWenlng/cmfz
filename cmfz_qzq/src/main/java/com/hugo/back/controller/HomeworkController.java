package com.hugo.back.controller;

import com.hugo.back.service.HomeworkService;
import com.hugo.common.entity.Homework;
import com.hugo.common.entity.Paging;
import com.hugo.common.utils.PoiUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/homework")
public class HomeworkController {

    @Autowired
    @Qualifier("homeworkService")
    private HomeworkService homeworkService;

    @Autowired
    private Jedis jedis;


    /**
     * 定时备份功课Excell表
     */
    @Scheduled(cron = "* * 21 * * ?")
    public void export(HttpServletRequest request) throws Exception {
        List<Homework> homeworks = homeworkService.query();
        String realPath = request.getSession().getServletContext().getRealPath("/upload/export");
        PoiUtil.exportExcel(Homework.class, homeworks, realPath + "\\homework.xlsx");
    }

    /**
     * 导出Excell表
     */
    @RequestMapping("/exportHomework")
    @ResponseBody
    public Map<String, Object> exportHomework(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        FileInputStream fis = null;
        ServletOutputStream os = null;
        try {
            List<Homework> homeworks = homeworkService.query();
            String realPath = request.getSession().getServletContext().getRealPath("/upload/export");
            PoiUtil.exportExcel(Homework.class, homeworks, realPath + "\\homework.xlsx");
            //文件输入流  读取指定文件
            fis = new FileInputStream(new File(realPath, "homework.xlsx"));
            //响应类型，响应头
            response.setContentType(request.getSession().getServletContext().getMimeType(".xlsx"));
            //文件下载是为中文件名编码
            response.setHeader("content-disposition", "attachment;fileName=" + URLEncoder.encode("homework.xlsx", "UTF-8"));
            //获取响应流
            os = response.getOutputStream();
            map.put("success", true);
            map.put("msg", "导出成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "导出失败");
            e.printStackTrace();
        }

        //下载，关闭资源
        IOUtils.copy(fis, os);
        IOUtils.closeQuietly(fis);
        IOUtils.closeQuietly(os);
        return map;
    }

    /**
     * 导入Excell表
     */
    @RequestMapping("/importHomework")
    @ResponseBody
    public Map<String, Object> importHomework(MultipartFile multipart, HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //获取文件的原始名
            String fileName = multipart.getOriginalFilename();
            //获取文件的类型
            String type = multipart.getContentType();
            //获取文件的大小
            long size = multipart.getSize();
            //处理上传,获取文件要上传的路径
            String realPath = request.getSession().getServletContext().getRealPath("/upload/import");
            //获取文件的后缀名不带点
            String extension = FilenameUtils.getExtension(fileName);
            //设置新的文件名
            String newFileName = UUID.randomUUID().toString().replace("-", "") + "." + extension;
            //创建新的文件
            multipart.transferTo(new File(realPath, newFileName));
            //删除一个文件
            //File file=new File(realPath,newFileName);
            //file.delete();
            List<Homework> homeworks = PoiUtil.importExcel(Homework.class, new FileInputStream(realPath + "\\" + newFileName));
            for (Homework homework : homeworks) {
                homeworkService.save(homework);
            }
            map.put("success", true);
            map.put("msg", "导入成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "导入失败");
            e.printStackTrace();
        }

        return map;
    }


    @RequestMapping("/save")
    @ResponseBody
    public Map<String, Object> save(Homework homework, String phone) {
        String jsonUserId = jedis.get(DigestUtils.md5DigestAsHex(phone.getBytes()));
        homework.setUserId(jsonUserId);
        return homeworkService.save(homework);
    }

    @RequestMapping("/remove")
    @ResponseBody
    public Map<String, Object> remove(String id) {
        return homeworkService.remove(id);
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Map<String, Object> modify(Homework homework) {
        return homeworkService.modify(homework);
    }

    @RequestMapping("/queryOne")
    @ResponseBody
    public Homework queryOne(String id) {
        Homework homework = homeworkService.queryOne(id);
        return homework;
    }

    @RequestMapping("/queryAll")
    @ResponseBody
    public Paging queryAll(Integer rows, Integer page, String phone) {
        //计算begin
        Integer begin = (page - 1) * rows;
        String jsonUserId = jedis.get(DigestUtils.md5DigestAsHex(phone.getBytes()));
        List<Homework> homeworks = homeworkService.queryAll(begin, rows, jsonUserId);
        Integer count = homeworkService.count(jsonUserId);
        return new Paging(count, homeworks);
    }
}
