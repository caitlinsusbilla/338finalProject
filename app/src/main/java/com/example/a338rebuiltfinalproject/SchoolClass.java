package com.example.a338rebuiltfinalproject;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "CLASS_TABLE")
public class SchoolClass {
    @PrimaryKey(autoGenerate = true)
    private int mClassId;
    private int mUserId;
    private String mClassName;

    private String mClassGrade;


    public SchoolClass(String className, String classGrade) {
        this.mClassName = className;
        this.mClassGrade = classGrade;
    }

    public int getClassId() {
        return mClassId;
    }

    public void setClassId(int classId) {
        mClassId = classId;
    }

    public String getClassName() {
        return mClassName;
    }

    public void setClassName(String className) {
        mClassName = className;
    }

    public String getClassGrade() {
        return mClassGrade;
    }

    public void setClassGrade(String classGrade) {
        mClassGrade = classGrade;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        this.mUserId = userId;
    }

    @Override
    public String toString() {
        return "Class Name: " + getClassName() + " | Grade: " + getClassGrade();
    }
}
