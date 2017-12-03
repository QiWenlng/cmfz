package com.hugo.back.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2017/12/2.
 */
@Controller
@RequestMapping("/file")
public class FileController {


    /**
     * 上传文件
     *
     * @param imgFile
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Map<String, Object> upload(MultipartFile imgFile, HttpServletRequest request) throws IOException {
        System.out.println("66666666666");
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            //获取上传路径
            String path = request.getSession().getServletContext().getRealPath("/upload/article");
            String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            File file = new File(path + File.separator + format);
            if (!file.exists()) {
                file.mkdirs();
            }
            //上传文件
            imgFile.transferTo(new File(file, imgFile.getOriginalFilename()));
            result.put("error", 0);
            result.put("url", request.getContextPath() + "/upload/article/" + format + File.separator + imgFile.getOriginalFilename());
            result.put("message", "上传成功!!!");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("error", 1);
            result.put("message", e.getMessage());
        }
        return result;
    }


    /**
     * 文件回显
     */
    @RequestMapping("/findAll")
    @ResponseBody
    public JSONObject findAllFiles(String path, HttpServletRequest request) {

        JSONObject map = new JSONObject();
        String realpath = request.getSession().getServletContext().getRealPath("/upload/article");
        //第一打开图片空间path 值为空间   点击图片空间的目录时path才会有值
        if (!StringUtils.isEmpty(path)) {
            realpath = realpath + "/" + path;
        }
        //浏览操作
        File file = new File(realpath);
        //遍历目录取的文件信息
        List<Hashtable> fileList = new ArrayList<Hashtable>();
        if (file.listFiles() != null) {
            for (File fi : file.listFiles()) {
                Hashtable<String, Object> hash = new Hashtable<String, Object>();
                String fileName = fi.getName();
                if (fi.isDirectory()) {
                    hash.put("is_dir", true);
                    hash.put("has_file", (fi.listFiles() != null));
                    hash.put("filesize", 0L);
                    hash.put("is_photo", false);
                    hash.put("filetype", "");
                } else if (fi.isFile()) {
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    hash.put("is_dir", false);
                    hash.put("has_file", false);
                    hash.put("filesize", fi.length());
                    hash.put("is_photo", true);
                    hash.put("filetype", fileExt);
                }
                hash.put("filename", fileName);
                hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fi.lastModified()));
                fileList.add(hash);
            }
        }
        //初始化kindeditor需要map的key
        String currentDirPath = path;
        String moveupDirPath = "";
        if (!"".equals(path)) {
            String str = currentDirPath.substring(0, currentDirPath.length() - 1);
            moveupDirPath = str.lastIndexOf("/") >= 0 ? str.substring(0, str.lastIndexOf("/") + 1) : "";
        }
        map.put("moveup_dir_path", moveupDirPath);
        map.put("current_dir_path", currentDirPath);
        //当前的url  项目名+文件存储路径
        map.put("current_url", request.getContextPath() + "/upload/article/" + path + File.separator);
        map.put("total_count", "");
        map.put("file_list", fileList);

        return map;

    }

}
