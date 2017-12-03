package com.hugo.common.entity;

public class Homework {
    private String hwId;
    private String hwName;
    private String hwStatus;
    private String userId;

    @Override
    public String toString() {
        return "Homework{" +
                "hwId='" + hwId + '\'' +
                ", hwName='" + hwName + '\'' +
                ", hwStatus='" + hwStatus + '\'' +
                ", userId='" + userId +
                '}';
    }

    public Homework() {
    }

    public Homework(String hwId, String hwName, String hwStatus, String userId) {
        this.hwId = hwId;
        this.hwName = hwName;
        this.hwStatus = hwStatus;
        this.userId = userId;
    }

    public String getHwId() {
        return hwId;
    }

    public void setHwId(String hwId) {
        this.hwId = hwId;
    }

    public String getHwName() {
        return hwName;
    }

    public void setHwName(String hwName) {
        this.hwName = hwName;
    }

    public String getHwStatus() {
        return hwStatus;
    }

    public void setHwStatus(String hwStatus) {
        this.hwStatus = hwStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
