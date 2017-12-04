package com.hugo.common.entity;

import java.io.Serializable;

public class Guru implements Serializable {
    private String guruId;
    private String guruName;
    private String guruImageUrl;
    private String guruStatus;
    private String intro;

    @Override
    public String toString() {
        return "Guru{" +
                "guruId='" + guruId + '\'' +
                ", guruName='" + guruName + '\'' +
                ", guruImageUrl='" + guruImageUrl + '\'' +
                ", guruStatus='" + guruStatus + '\'' +
                ", intro='" + intro + '\'' +
                '}';
    }

    public Guru() {
    }

    public Guru(String guruId, String guruName, String guruImageUrl, String guruStatus, String intro) {
        this.guruId = guruId;
        this.guruName = guruName;
        this.guruImageUrl = guruImageUrl;
        this.guruStatus = guruStatus;
        this.intro = intro;
    }

    public String getGuruId() {
        return guruId;
    }

    public void setGuruId(String guruId) {
        this.guruId = guruId;
    }

    public String getGuruName() {
        return guruName;
    }

    public void setGuruName(String guruName) {
        this.guruName = guruName;
    }

    public String getGuruImageUrl() {
        return guruImageUrl;
    }

    public void setGuruImageUrl(String guruImageUrl) {
        this.guruImageUrl = guruImageUrl;
    }

    public String getGuruStatus() {
        return guruStatus;
    }

    public void setGuruStatus(String guruStatus) {
        this.guruStatus = guruStatus;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
