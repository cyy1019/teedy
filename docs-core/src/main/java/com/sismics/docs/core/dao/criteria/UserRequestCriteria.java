package com.sismics.docs.core.dao.criteria;

import java.util.Date;

/**
 * Criteria for searching UserRequest.
 */
public class UserRequestCriteria {

    private String userId;
    private String type;
    private Date fromDate;
    private Date toDate;
    private String search; // 可选：模糊搜索字段，如 data 中包含某些关键字

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

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}