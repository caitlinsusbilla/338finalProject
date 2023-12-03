package com.example.a338rebuiltfinalproject;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ASSIGNMENTS")
public class Assignments {
    @PrimaryKey(autoGenerate = true)
    private int mAssId;
    private int mUserId;
    private String mAssName;
    private String mAssDue;

    public Assignments(String assName, String assDue){
        this.mAssName = assName;
        this.mAssDue = assDue;
    }
    public int getAssId() {
        return mAssId;
    }

    public void setAssId(int assId) {
        mAssId = assId;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public String getAssName() {
        return mAssName;
    }

    public void setAssName(String assName) {
        mAssName = assName;
    }

    public String getAssDue() {
        return mAssDue;
    }

    public void setAssDue(String assDue) {
        mAssDue = assDue;
    }

    @Override
    public String toString() {
        return getAssName() + " | Due Date: " + getAssDue();
    }
}
