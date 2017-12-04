package com.hugo.common.entity;

import java.io.Serializable;
import java.util.Date;

public class Album implements Serializable {
    private String albumId;
    private String albumName;
    private String author;
    private String albumCount;
    private String broadcastAuthor;
    private String contentIntro;
    private String imageUrl;
    private Date publishDate;
    private Integer score;

    @Override
    public String toString() {
        return "Album{" +
                "albumId='" + albumId + '\'' +
                ", albumName='" + albumName + '\'' +
                ", author='" + author + '\'' +
                ", albumCount='" + albumCount + '\'' +
                ", broadcastAuthor='" + broadcastAuthor + '\'' +
                ", contentIntro='" + contentIntro + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", publishDate=" + publishDate +
                ", score=" + score +
                '}';
    }

    public Album() {
    }

    public Album(String albumId, String albumName, String author, String albumCount,
                 String broadcastAuthor, String contentIntro, String imageUrl,
                 Date publishDate, Integer score) {
        this.albumId = albumId;
        this.albumName = albumName;
        this.author = author;
        this.albumCount = albumCount;
        this.broadcastAuthor = broadcastAuthor;
        this.contentIntro = contentIntro;
        this.imageUrl = imageUrl;
        this.publishDate = publishDate;
        this.score = score;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbumCount() {
        return albumCount;
    }

    public void setAlbumCount(String albumCount) {
        this.albumCount = albumCount;
    }

    public String getBroadcastAuthor() {
        return broadcastAuthor;
    }

    public void setBroadcastAuthor(String broadcastAuthor) {
        this.broadcastAuthor = broadcastAuthor;
    }

    public String getContentIntro() {
        return contentIntro;
    }

    public void setContentIntro(String contentIntro) {
        this.contentIntro = contentIntro;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
