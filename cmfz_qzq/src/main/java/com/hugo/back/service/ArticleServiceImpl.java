package com.hugo.back.service;

import com.hugo.common.annotations.Cache;
import com.hugo.common.annotations.FlushCache;
import com.hugo.common.dao.ArticleDao;
import com.hugo.common.dao.ArticleLuceneDaoImpl;
import com.hugo.common.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.*;

@Service("articleService")
@Transactional
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    @Qualifier("articleDao")
    private ArticleDao articleDao;

    @Autowired
    @Qualifier("articleLuceneDaoImpl")
    private ArticleLuceneDaoImpl articleLuceneDaoImpl;

    @Autowired
    private Jedis jedis;


    private Map<String, Object> map = new HashMap<String, Object>();


    @FlushCache
    @Override
    public Map<String, Object> save(Article article) {
        article.setArticleId(UUID.randomUUID().toString());
        article.setPublishDate(new Date());
        try {
            articleDao.save(article);
            articleLuceneDaoImpl.addIndex(article);
            map.put("success", true);
            map.put("msg", "添加成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "添加失败\n" + e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @FlushCache
    @Override
    public Map<String, Object> remove(String articleId) {
        try {
            articleDao.remove(articleId);
            map.put("success", true);
            map.put("msg", "删除成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "删除失败\n" + e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @FlushCache
    @Override
    public Map<String, Object> modify(Article article) {
        try {
            articleDao.modify(article);
            map.put("success", true);
            map.put("msg", "修改成功");
        } catch (Exception e) {
            map.put("success", false);
            map.put("msg", "修改失败\n" + e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Article queryOne(String articleId) {
        Article article = articleDao.queryOne(articleId);
        return article;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Article> queryAll(Integer begin, Integer rows) {
        List<Article> articles = articleDao.queryAll(begin, rows);
        return articles;
    }

    @Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer count() {
        Integer count = articleDao.count();
        return count;
    }

    //@Cache
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Article> searcher(String attribute, String value) {
        List<Article> articles = articleLuceneDaoImpl.queryAll(attribute, value);
        return articles;
    }
}
