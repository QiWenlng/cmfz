package com.hugo.common.dao;

import com.hugo.common.entity.Article;
import com.hugo.common.utils.DateTransformUtils;
import com.hugo.common.utils.LuceneUtil;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作索引库
 */
@Repository
public class ArticleLuceneDaoImpl {
    /**
     * 创建索引
     */

    public void addIndex(Article article) {
        IndexWriter indexWriter = LuceneUtil.getIndexWriter();
        try {
            Document document = new Document();
            document.add(new StringField("articleId", article.getArticleId(), Field.Store.YES));
            document.add(new StringField("guruId", article.getGuruId(), Field.Store.NO));
            document.add(new TextField("publishDate", DateTransformUtils.utilToStr(article.getPublishDate()), Field.Store.YES));
            document.add(new StringField("title", article.getTitle(), Field.Store.YES));
            document.add(new TextField("articleUrl", article.getArticleUrl(), Field.Store.NO));
            document.add(new StringField("userId", article.getUserId(), Field.Store.YES));
            indexWriter.addDocument(document);

            LuceneUtil.commitIndexWriter(indexWriter);
        } catch (Exception e) {
            LuceneUtil.rollbackIndexWriter(indexWriter);
            e.printStackTrace();
        }
    }

    /**
     * 删除索引
     */

    public void deleteIndex(String articleId) {
        IndexWriter indexWriter = LuceneUtil.getIndexWriter();
        try {
            indexWriter.deleteDocuments(new Term("articleId", articleId));
            LuceneUtil.commitIndexWriter(indexWriter);
        } catch (Exception e) {
            e.printStackTrace();
            LuceneUtil.rollbackIndexWriter(indexWriter);
        }
    }

    /**
     * 更新索引
     */

    public void modifyIndex(Article article) {
        IndexWriter indexWriter = LuceneUtil.getIndexWriter();
        try {
            Document document = new Document();
            document.add(new StringField("articleId", article.getArticleId(), Field.Store.YES));
            document.add(new StringField("guruId", article.getGuruId(), Field.Store.YES));
            document.add(new TextField("publishDate", article.getPublishDate().toString(), Field.Store.YES));
            document.add(new StringField("title", article.getTitle(), Field.Store.YES));
            document.add(new TextField("articleUrl", article.getArticleUrl(), Field.Store.NO));
            document.add(new StringField("userId", article.getUserId(), Field.Store.YES));
            indexWriter.updateDocument(new Term("articleId", article.getArticleId()), document);
            LuceneUtil.commitIndexWriter(indexWriter);
        } catch (Exception e) {
            LuceneUtil.rollbackIndexWriter(indexWriter);
            e.printStackTrace();
        }
    }

    /**
     * 查询一个文章
     */

    public Article queryOne(String articleId) {
        Article article = null;
        IndexSearcher indexSearcher = LuceneUtil.getIndexSearcher();
        try {
            TopDocs topDocs = indexSearcher.search(new TermQuery(new Term("articleId", articleId)), 1);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            if (scoreDocs != null) {
                article = new Article();
                ScoreDoc scoreDoc = scoreDocs[0];
                int doc = scoreDoc.doc;
                Document document = indexSearcher.doc(doc);
                article.setArticleId(document.get("articleId"));
                article.setGuruId(document.get("guruId"));
                article.setPublishDate(DateTransformUtils.strToUtil(document.get("publishDate")));
                article.setTitle(document.get("title"));
                article.setUserId(document.get("userId"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return article;
    }

    /**
     * 查询所有文章
     * 参数1: field: 你要搜索的字段
     * 参数2: value  你要搜索的值
     * 参数3: 查询的条数
     */

    public List<Article> queryAll(String field, String value) {
        List<Article> articles = null;
        IndexSearcher indexSearcher = LuceneUtil.getIndexSearcher();
        try {
            TopDocs topDocs = indexSearcher.search(new TermQuery(new Term(field, value)), 100);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            if (scoreDocs != null) {
                articles = new ArrayList<Article>();
                for (int i = 0; i < scoreDocs.length; i++) {
                    Article article = new Article();
                    ScoreDoc scoreDoc = scoreDocs[i];
                    int doc = scoreDoc.doc;
                    Document document = indexSearcher.doc(doc);
                    article.setArticleId(document.get("articleId"));
                    article.setGuruId(document.get("guruId"));
                    article.setPublishDate(DateTransformUtils.strToUtil(document.get("publishDate")));
                    article.setTitle(document.get("title"));
                    article.setUserId(document.get("userId"));
                    articles.add(article);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articles;
    }
}
