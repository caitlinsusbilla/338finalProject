package com.example.a338rebuiltfinalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AssignmentsDAO {
        @Insert
        void insert(Assignments assignments);
        @Delete
        void delete(Assignments assignments);
        @Update
        void updateClass(SchoolClass schoolClass);
        @Query("SELECT * FROM ASSIGNMENTS WHERE mAssName = :assName AND mAssDue = :assDue")
        Assignments getClass(String assName, String assDue);

        @Query("SELECT * FROM ASSIGNMENTS WHERE mAssName = :assName")
        Assignments getClassName(String assName);

        @Query("SELECT * FROM ASSIGNMENTS WHERE mAssId = :assId")
        List<Assignments> getAssById(int assId);

        @Query("SELECT * FROM ASSIGNMENTS WHERE mUserId = :userId")
        List<Assignments> getAllAssForUser(int userId);


}
