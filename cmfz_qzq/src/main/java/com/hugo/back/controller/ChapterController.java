package com.hugo.back.controller;

import com.hugo.back.service.ChapterService;
import com.hugo.common.entity.Chapter;
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
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    @Qualifier("chapterService")
    private ChapterService chapterService;

    @RequestMapping("/save")
    @ResponseBody
    public Map<String, Object> save(Chapter chapter, MultipartFile multipart, HttpServletRequest request) {

        Map<String, Object> map = new HashMap<String, Object>();
        //获取文件的原始名
        String fileName = multipart.getOriginalFilename();
        //获取文件的类型
        String type = multipart.getContentType();
        //获取文件的大小
        int size = (int) multipart.getSize();
        //处理上传,获取文件要上传的路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/chapter");
        //获取文件的后缀名不带点
        String extension = FilenameUtils.getExtension(fileName);
        //设置新的文件名
        String newFileName = UUID.randomUUID().toString().replace("-", "") + "." + extension;
        try {
            //创建新的文件
            multipart.transferTo(new File(realPath, newFileName));
            chapter.setDownloadUrl("/cmfz/upload/chapter/" + newFileName);
            chapter.setChapterSize(size);
            map = chapterService.save(chapter);
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
    public Map<String, Object> remove(String chapterId) {
        return chapterService.remove(chapterId);
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Map<String, Object> modify(Chapter chapter) {
        return chapterService.modify(chapter);
    }

    @RequestMapping("/queryOne")
    @ResponseBody
    public Chapter queryOne(String chapterId) {
        Chapter chapter = chapterService.queryOne(chapterId);
        return chapter;
    }

    @RequestMapping("/queryAll")
    @ResponseBody
    public Paging queryAll(Integer rows, Integer page) {
        //计算begin
        Integer begin = (page - 1) * rows;

        List<Chapter> chapters = chapterService.queryAll(begin, rows);
        Integer count = chapterService.count();
        return new Paging(count, chapters);
    }

    @RequestMapping("/queryByAlbum")
    @ResponseBody
    public Paging queryByAlbum(Integer rows, Integer page, String albumId) {
        //计算begin
        Integer begin = (page - 1) * rows;

        List<Chapter> chapters = chapterService.queryByAlbum(begin, rows, albumId);
        Integer count = chapterService.countByAlbum(albumId);
        return new Paging(count, chapters);
    }
}
