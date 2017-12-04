package com.hugo.common.entity;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {
    private String articleId;
    private String guruId;
    private Date publishDate;
    private String title;
    private String articleUrl;
    private String userId;

    private Guru guru;

    @Override
    public String toString() {
        return "Article{" +
                "articleId='" + articleId + '\'' +
                ", guruId='" + guruId + '\'' +
                ", publishDate=" + publishDate +
                ", title='" + title + '\'' +
                ", articleUrl='" + articleUrl + '\'' +
                ", userId='" + userId + '\'' +
                ", guru=" + guru +
                '}';
    }

    public Article(String articleId, String guruId, Date publishDate,
                   String title, String articleUrl, String userId) {
        this.articleId = articleId;
        this.guruId = guruId;
        this.publishDate = publishDate;
        this.title = title;
        this.articleUrl = articleUrl;
        this.userId = userId;
    }

    public Article() {
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getGuruId() {
        return guruId;
    }

    public void setGuruId(String guruId) {
        this.guruId = guruId;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Guru getGuru() {
        return guru;
    }

    public void setGuru(Guru guru) {
        this.guru = guru;
    }
}
