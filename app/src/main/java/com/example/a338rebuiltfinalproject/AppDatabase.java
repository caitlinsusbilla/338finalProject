package com.example.a338rebuiltfinalproject;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.Executors;

@Database(entities = {User.class, SchoolClass.class}, version = 3)
    public abstract class AppDatabase extends RoomDatabase {

        public abstract UserDAO userDao();

        public abstract SchoolClassDAO schoolclassDao();
        private static volatile AppDatabase instance;

        public static AppDatabase getDatabase(final Context context) {
            if (instance == null) {
                synchronized (AppDatabase.class) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, "my_database").addCallback(sRoomDatabaseCallback).build();
                    }
                }
            }
            return instance;
        }

        private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        // set up predefined users
                        User testUser = new User("testuser1", "testuser1");
                        instance.userDao().insert(testUser);
                        System.out.println("user added");
                        User admin = new User("admin2","admin2");
                        instance.userDao().insert(admin);

                        //predefined schoolClass
                        SchoolClass testClass = new SchoolClass("English", "A");
                        instance.schoolclassDao().insert(testClass);

                    }
                });

            }
        };






    }
