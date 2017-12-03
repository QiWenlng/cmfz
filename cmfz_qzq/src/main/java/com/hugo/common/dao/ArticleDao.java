package com.hugo.common.dao;

import com.hugo.common.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao extends BasicDao<Article> {
    public List<Article> queryAll(@Param("begin") Integer begin, @Param("rows") Integer rows);

    public Integer count();
}
