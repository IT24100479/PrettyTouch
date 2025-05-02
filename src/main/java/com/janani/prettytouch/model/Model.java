package com.janani.prettytouch.model;

import com.janani.prettytouch.services.UserService;
import com.janani.prettytouch.util.TypeConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public abstract class Model {
    protected int id;
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
        this.id = TypeConverter.stringToInt(id);
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public UserModel getCreatedByUser() {
        return (UserModel) UserService.getInstance().getById(createdBy);
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = TypeConverter.stringToInt(createdBy);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = TypeConverter.stringToLocalDateTime(createdAt);
    }

    public void checkCreatedAtAndID(List<Model> list){
        if (this.getCreatedAt() == null || this.getCreatedAt().toString().isEmpty()) {
            this.setCreatedAt(LocalDateTime.now().toString());
        }
        if(this.getId()==0){
            int id = 1;
            if(!list.isEmpty()){
                id = list.getLast().getId()+1;
            }
            this.setId(id+"");
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

    public abstract String[] getCSVLine();

    public abstract boolean validate();
}
