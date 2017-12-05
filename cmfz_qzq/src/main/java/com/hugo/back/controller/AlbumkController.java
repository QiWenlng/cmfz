package com.hugo.back.controller;

import com.hugo.back.service.AlbumService;
import com.hugo.back.service.ChapterService;
import com.hugo.common.entity.Album;
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
import java.util.*;

@Controller
@RequestMapping("/album")
public class AlbumkController {

    @Autowired
    @Qualifier("albumService")
    private AlbumService albumService;

    @Autowired
    private ChapterService chapterService;

    @RequestMapping("/save")
    @ResponseBody
    public Map<String, Object> save(Album album, MultipartFile multipart, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        //获取文件的原始名
        String fileName = multipart.getOriginalFilename();
        //获取文件的类型
        String type = multipart.getContentType();
        //获取文件的大小
        long size = multipart.getSize();
        //处理上传,获取文件要上传的路径
        String realPath = request.getSession().getServletContext().getRealPath("/upload/album");
        //获取文件的后缀名不带点
        String extension = FilenameUtils.getExtension(fileName);
        //设置新的文件名
        String newFileName = UUID.randomUUID().toString().replace("-", "") + "." + extension;
        try {
            //创建新的文件
            multipart.transferTo(new File(realPath, newFileName));
            album.setImageUrl("/cmfz/upload/album/" + newFileName);
            map = albumService.save(album);
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
    public Map<String, Object> remove(String albumId) {
        return albumService.remove(albumId);
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Map<String, Object> modify(Album album) {
        return albumService.modify(album);
    }

    @RequestMapping("/queryOne")
    @ResponseBody
    public Album queryOne(String albumId) {
        Album album = albumService.queryOne(albumId);
        return album;
    }

    @RequestMapping("/queryAll")
    @ResponseBody
    public Paging queryAll(Integer rows, Integer page) {
        //计算begin
        Integer begin = (page - 1) * rows;
        List<Album> albums = albumService.queryAll(begin, rows);
        Integer count = albumService.count();
        return new Paging(count, albums);
    }

    @RequestMapping("/queryAlbum")
    @ResponseBody
    public List<Album> queryAlbum() {
        List<Album> albums = albumService.queryAlbum();
        return albums;
    }


    /**
     * 统计专辑章节数
     */
    @RequestMapping("/queryChapterCount")
    @ResponseBody
    public Map<String, List<Object>> queryChapterCount() throws Exception {
        //查询所有章节
        List<Chapter> chapters = chapterService.query();
        //创建一个list集合用于存放专辑id
        List<String> albumids = new ArrayList<String>();
        //遍历章节，以获取每个章节的专辑id
        for (Chapter chapter : chapters) {
            //String jsonObj = JSONObject.toJSONString(chapter);
            //Chapter stud = JSONObject.parseObject(jsonObj, Chapter.class);
            //判断list集合中专辑id是否重复，去重复
            if (!albumids.contains(chapter.getAlbumId())) {
                albumids.add(chapter.getAlbumId());
            }
        }
        Map<String, List<Object>> map = new HashMap<String, List<Object>>();
        List<Object> albumNames = new ArrayList<Object>();
        List<Object> chapterSums = new ArrayList<Object>();
        //把专辑名作为值，章节数量作为值存入map集合中
        map.put("albumName", albumNames);
        map.put("chapterSum", chapterSums);
        //对专辑id集合进行遍历
        for (String str : albumids) {
            //根据专辑id查询每个班的章节数量
            Integer count = chapterService.countByAlbum(str);
            //根据专辑id查询专辑名字
            Album album2 = albumService.queryOne(str);
            //把专辑名作为键，章节数量作为值存入map集合中
            map.get("albumName").add(album2.getAlbumName());
            if (count == null) {
                map.get("chapterSum").add(0);
            }
            map.get("chapterSum").add(count);
        }
        return map;
    }
}
