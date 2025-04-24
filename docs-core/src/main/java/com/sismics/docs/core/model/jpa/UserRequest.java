package com.sismics.docs.core.model.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "T_USER_REQUEST")
public class UserRequest {

    @Id
    @Column(name = "URQ_ID_C")
    private String id;

    @Column(name = "URQ_IDUSER_C")
    private String userId;

    @Column(name = "URQ_TYPE_C")
    private String type;

    @Column(name = "URQ_DATA_C")
    private String data;

    @Column(name = "URQ_CREATEDATE_D")
    private Date createDate;

    @Column(name = "URQ_DELETEDATE_D")
    private Date deleteDate;

   public String getId() {
       return this.id;
   }

   public void setId(String id) {
       this.id = id;
   }

   public String getUserId() {
       return this.userId;
   }

   public void setUserId(String userId) {
       this.userId = userId;
   }

   public String getType() {
       return this.type;
   }

   public void setType(String type) {
       this.type = type;
   }

   public String getData() {
       return this.data;
   }

   public void setData(String data) {
       this.data = data;
   }

   public Date getCreateDate() {
       return this.createDate;
   }

   public void setCreateDate(Date createDate) {
       this.createDate = createDate;
   }

   public Date getDeleteDate() {
       return this.deleteDate;
   }

   public void setDeleteDate(Date deleteDate) {
       this.deleteDate = deleteDate;
   }
}

