package com.hugo.common.entity;

public class Chapter {
    private String chapterId;
    private String chapterCode;
    private Integer chapterSize;
    private String downloadUrl;
    private String albumId;
    private String chapterName;

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "chapterId='" + chapterId + '\'' +
                ", chapterCode='" + chapterCode + '\'' +
                ", chapterSize=" + chapterSize +
                ", downloadUrl='" + downloadUrl + '\'' +
                ", albumId='" + albumId + '\'' +
                '}';
    }

    public Chapter() {

    }

    public Chapter(String chapterId, String chapterCode, Integer chapterSize,
                   String downloadUrl, String albumId) {
        this.chapterId = chapterId;
        this.chapterCode = chapterCode;
        this.chapterSize = chapterSize;
        this.downloadUrl = downloadUrl;
        this.albumId = albumId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterCode() {
        return chapterCode;
    }

    public void setChapterCode(String chapterCode) {
        this.chapterCode = chapterCode;
    }

    public Integer getChapterSize() {
        return chapterSize;
    }

    public void setChapterSize(Integer chapterSize) {
        this.chapterSize = chapterSize;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }
}
