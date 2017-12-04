package com.hugo.common.entity;

import java.io.Serializable;
import java.util.Date;

public class Banner implements Serializable {
    private String id;
    private String imageUrl;
    private String imageName;
    private String status;
    private Date uploadDate;

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id='" + id + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageName='" + imageName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Banner() {
    }

    public Banner(String id, String imageUrl, String imageName, String status) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.imageName = imageName;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
