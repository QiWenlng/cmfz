package com.hugo.back.service;

import com.hugo.common.entity.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    public Map<String, Object> save(Article article);

    public Map<String, Object> remove(String id);

    public Map<String, Object> modify(Article article);

    public Article queryOne(String id);

    public List<Article> queryAll(Integer begin, Integer rows);

    public Integer count();

    public List<Article> searcher(String attribute, String value);
}
