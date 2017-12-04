package com.hugo.common.entity;

import java.io.Serializable;
import java.util.Date;

public class Counter implements Serializable {
    private String counterId;
    private String hwId;
    private String userId;
    private String name;
    private Integer count;
    private Date updateDate;
    private String counterStatus;

    public String getCounterStatus() {
        return counterStatus;
    }

    public void setCounterStatus(String counterStatus) {
        this.counterStatus = counterStatus;
    }

    private User user;
    private Homework homework;

    @Override
    public String toString() {
        return "Count{" +
                "countId='" + counterId + '\'' +
                ", hwId='" + hwId + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", updateDate=" + updateDate +
                ", user=" + user +
                '}';
    }

    public Counter() {
    }

    public Counter(String counterId, String hwId, String userId, String name,
                   Integer count, Date updateDate) {
        this.counterId = counterId;
        this.hwId = hwId;
        this.userId = userId;
        this.name = name;
        this.count = count;
        this.updateDate = updateDate;
    }

    public Homework getHomework() {
        return homework;
    }

    public void setHomework(Homework homework) {
        this.homework = homework;
    }

    public String getCounterId() {
        return counterId;
    }

    public void setCounterId(String counterId) {
        this.counterId = counterId;
    }

    public String getHwId() {
        return hwId;
    }

    public void setHwId(String hwId) {
        this.hwId = hwId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
