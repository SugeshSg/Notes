package com.sugesh.notes.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "user_table")
public class Users {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "email_id")
    public String email_id;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "user_id")
    public String userID;

    public Users (String email_id,String password, String userID){
        this.email_id = email_id;
        this.password = password;
        this.userID = userID;
    }

    public int getUid() {
        return uid;
    }

    public String getUserID() {
        return userID;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getEmail_id() {
        return email_id;
    }

    public String getPassword() {
        return password;
    }
}
