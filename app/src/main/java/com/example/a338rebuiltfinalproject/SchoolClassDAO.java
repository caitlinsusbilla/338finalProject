package com.example.a338rebuiltfinalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SchoolClassDAO {
    @Insert
    void insert(SchoolClass schoolClass);
    @Delete
    void delete(SchoolClass schoolClass);
    @Update
    void updateClass(SchoolClass schoolClass);
    @Query("SELECT * FROM CLASS_TABLE WHERE mClassName = :className AND mClassGrade = :classGrade")
    SchoolClass getClass(String className, String classGrade);

    @Query("SELECT * FROM CLASS_TABLE WHERE mClassName = :className")
    SchoolClass getClassName(String className);

    @Query("SELECT * FROM CLASS_TABLE WHERE mClassId = :classId")
    List<SchoolClass> getClassById(int classId);

    @Query("SELECT * FROM CLASS_TABLE WHERE mUserId = :userId")
    List<SchoolClass> getAllClassesForUser(int userId);


}