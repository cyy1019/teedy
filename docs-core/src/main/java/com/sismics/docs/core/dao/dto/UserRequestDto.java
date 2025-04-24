package com.sismics.docs.core.dao.dto;

import java.util.Date;

public class UserRequestDto {

    private String id;
    private String userId;
    private String type;
    private String data;
    private Date createDate;

    // 可选：附加用户信息（比如用户名或邮箱），需要联表查询时使用
    private String userName;
    private String userEmail;

    public UserRequestDto() {
    }

    public UserRequestDto(String id, String userId, String type, String data, Date createDate) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.data = data;
        this.createDate = createDate;
    }

    // Getter & Setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}