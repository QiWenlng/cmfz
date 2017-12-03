package com.hugo.common.entity;

public class User {
    private String userId;
    private String phone;
    private String fname;
    private String name;
    private String sex;
    private String address;
    private String signature;
    private String guruId;
    private String password;
    private String salt;
    private String imageUrl;
    private String userStatus;

    private Guru guru;

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", phone='" + phone + '\'' +
                ", fname='" + fname + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", address='" + address + '\'' +
                ", signature='" + signature + '\'' +
                ", guruId='" + guruId + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", guru=" + guru +
                '}';
    }

    public User(String userId, String phone, String fname, String name,
                String sex, String address, String signature, String guruId,
                String password, String salt, String imageUrl, String userStatus, Guru guru) {
        this.userId = userId;
        this.phone = phone;
        this.fname = fname;
        this.name = name;
        this.sex = sex;
        this.address = address;
        this.signature = signature;
        this.guruId = guruId;
        this.password = password;
        this.salt = salt;
        this.imageUrl = imageUrl;
        this.userStatus = userStatus;
        this.guru = guru;
    }

    public User() {

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getGuruId() {
        return guruId;
    }

    public void setGuruId(String guruId) {
        this.guruId = guruId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public Guru getGuru() {
        return guru;
    }

    public void setGuru(Guru guru) {
        this.guru = guru;
    }
}