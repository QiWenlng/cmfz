package com.hugo.common.dao;

import com.hugo.common.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface ArticleDao extends BasicDao<Article> {
    @Cacheable(value = "baseCache", key = "#root.methodName")
    public List<Article> queryAll(@Param("begin") Integer begin, @Param("rows") Integer rows);

    @Cacheable(value = "baseCache", key = "#root.methodName")
    public Integer count();
}
