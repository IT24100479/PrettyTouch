package com.janani.prettytouch.model;

import com.janani.prettytouch.services.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Model {
    private int id;
    protected int createdBy;
    protected LocalDateTime createdAt;
    protected String status;

    public Model(String id, String createdBy, String createdAt, String status) {
        setId(id);
        setCreatedBy(createdBy);
        setCreatedAt(createdAt);
        setStatus(status);
    }

    public Model() {
    }

    public int getId() {
        return id;
    }

    public void setId(String id) {
        try{
            this.id = Integer.parseInt(id);
        }catch (Exception e){
            e.printStackTrace();
            this.id = 0;
        }
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public UserModel getCreatedByUser() {
        return (UserModel) UserService.getInstance().getById(createdBy);
    }

    public void setCreatedBy(String createdBy) {
        try{
            this.createdBy = Integer.parseInt(createdBy);
        }catch (Exception e){
            e.printStackTrace();
            this.createdBy = 0;
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        try{
            this.createdAt = LocalDateTime.parse(createdAt);
        } catch (Exception e) {
            e.printStackTrace();
            this.createdAt=null;
        }
    }

    public boolean getStatus() {
        return "1".equals(status);
    }

    public String getStatusForCsv() {
        return status;
    }

    public void setStatus(String status) {
        this.status=status;
    }
}
