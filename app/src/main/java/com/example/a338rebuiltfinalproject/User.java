package com.example.a338rebuiltfinalproject;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "USER_TABLE")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int mLogId;
    private String mUsername;
    private String mPassword;


    public User(String username, String password) {

        this.mUsername = username;
        this.mPassword = password;
    }

    public int getLogId() {
        return mLogId;
    }

    public void setLogId(int logId) {
        mLogId = logId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    @Override
    public String toString() {
        return "User: " + getUsername() + " | Password: " + getPassword();
    }
}
