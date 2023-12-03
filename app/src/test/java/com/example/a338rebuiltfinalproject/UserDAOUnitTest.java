//package com.example.a338rebuiltfinalproject;
//
//import android.content.Context;
//import androidx.room.Room;
//import com.example.a338rebuiltfinalproject.User;
//import com.example.a338rebuiltfinalproject.UserDAO;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.io.IOException;
//import java.util.List;
//
//@RunWith(AndroidJUnit4.class)
//public class UserDAOUnitTest {
//
//    private UserDAO userDao;
//    private UserDatabase userDatabase;
//
//    @Before
//    public void createDb() {
//        Context context = ApplicationProvider.getApplicationContext();
//        userDatabase = Room.inMemoryDatabaseBuilder(context, UserDatabase.class).build();
//        userDao = userDatabase.getUserDAO();
//    }
//
//    @After
//    public void closeDb() throws IOException {
//        userDatabase.close();
//    }
//
//    @Test
//    public void insertAndGetUser() throws Exception {
//        User user = new User("testUser", "testPassword");
//        userDao.insert(user);
//
//        User retrievedUser = userDao.getUser("testUser", "testPassword");
//
//        assert(retrievedUser != null);
//        assert(retrievedUser.getUsername().equals("testUser"));
//        assert(retrievedUser.getPassword().equals("testPassword"));
//    }
//
//    @Test
//    public void getAllUsers() throws Exception {
//        User user1 = new User("user1", "password1");
//        User user2 = new User("user2", "password2");
//
//        userDao.insert(user1);
//        userDao.insert(user2);
//
//        List<User> userList = userDao.getAllUsers();
//
//        assert(userList != null);
//        assert(userList.size() == 2);
//    }
//
//    @Test
//    public void deleteUser() throws Exception {
//        User user = new User("userToDelete", "passwordToDelete");
//        userDao.insert(user);
//
//        userDao.delete(user);
//
//        User retrievedUser = userDao.getUser("userToDelete", "passwordToDelete");
//
//        assert(retrievedUser == null);
//    }
//
//    @Test
//    public void updateUser() throws Exception {
//        User user = new User("userToUpdate", "oldPassword");
//        userDao.insert(user);
//
//        // Retrieve the user and update the password
//        User retrievedUser = userDao.getUser("userToUpdate", "oldPassword");
//        assert(retrievedUser != null);
//
//        retrievedUser.setPassword("newPassword");
//        userDao.updateUser(retrievedUser);
//
//        // Retrieve the user again and assert that the password has been updated
//        User updatedUser = userDao.getUser("userToUpdate", "newPassword");
//        assert(updatedUser != null);
//        assert(updatedUser.getPassword().equals("newPassword"));
//    }
//
//}