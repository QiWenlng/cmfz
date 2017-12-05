package com.hugo.back.controller;

import com.hugo.back.service.ArticleService;
import com.hugo.common.entity.Article;
import com.hugo.common.entity.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    @Qualifier("articleService")
    private ArticleService articleService;

    @RequestMapping("/save")
    @ResponseBody
    public Map<String, Object> save(Article article) {
        return articleService.save(article);
    }

    @RequestMapping("/remove")
    @ResponseBody
    public Map<String, Object> remove(String id) {
        return articleService.remove(id);
    }

    @RequestMapping("/modify")
    @ResponseBody
    public Map<String, Object> modify(Article article) {
        return articleService.modify(article);
    }

    @RequestMapping("/queryOne")
    @ResponseBody
    public Article queryOne(String articleId) {
        Article article = articleService.queryOne(articleId);
        return article;
    }

    @RequestMapping("/queryAll")
    @ResponseBody
    public Paging queryAll(Integer rows, Integer page) {
        //计算begin
        Integer begin = (page - 1) * rows;

        List<Article> articles = articleService.queryAll(begin, rows);
        Integer count = articleService.count();
        return new Paging(count, articles);
    }

    @RequestMapping("/query")
    @ResponseBody
    public Article query(String articleId) {
        Article article = articleService.queryOne(articleId);
        return article;
    }

    @RequestMapping("/searcher")
    @ResponseBody
    public List<Article> searcher(String attribute, String value) {
        List<Article> articles = articleService.searcher(attribute, value);
        return articles;
    }
}
