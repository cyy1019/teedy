package com.sismics.docs.core.dao.dto;

import java.util.Date;

/**
 * User request DTO.
 *
 * @author jtremeaux
 */
public class UserRequestDto {

    private String id;
    private String userName;
    private String type;
    private String password;
    private String email;
    private Date createDate;

    // Constructor
    public UserRequestDto(String id, String userName, String type, String password, String email, Date createDate) {
        this.id = id;
        this.userName = userName;
        this.type = type;
        this.password = password;
        this.email = email;
        this.createDate = createDate;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateDate() {
        return createDate;
    }

    // toString method for better logging
    @Override
    public String toString() {
        return "UserRequestDTO{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", type='" + type + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
