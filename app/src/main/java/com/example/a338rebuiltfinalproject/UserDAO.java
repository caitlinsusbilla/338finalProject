package com.example.a338rebuiltfinalproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void updateUser(User user);

    @Query("SELECT * FROM USER_TABLE WHERE mUsername = :username AND mPassword = :password")
    User getUser(String username, String password);

    @Query("SELECT * FROM USER_TABLE WHERE mLogId = :logID")
    List<User> getLogById(int logID);

    @Query("SELECT * FROM USER_TABLE")
    List<User> getAllUsers();

}
