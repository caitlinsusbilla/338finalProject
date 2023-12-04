package com.example.a338rebuiltfinalproject;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.a338rebuiltfinalproject.SchoolClass;
import com.example.a338rebuiltfinalproject.SchoolClassDAO;
import com.example.a338rebuiltfinalproject.User;
import com.example.a338rebuiltfinalproject.UserDAO;
import com.example.a338rebuiltfinalproject.UserDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;


@RunWith(AndroidJUnit4.class)
public class SchoolClassDAOUnitTest {

    private SchoolClassDAO classDao;
    private UserDAO userDao;
    private UserDatabase userDatabase;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        userDatabase = Room.inMemoryDatabaseBuilder(context, UserDatabase.class).build();
        userDao = userDatabase.getUserDAO();
        classDao = userDatabase.getSchoolClassDAO();
    }

    @After
    public void closeDb() throws IOException {
        userDatabase.close();
    }

    @Test
    public void insertAndGetClass() throws Exception {
        User user = new User("testUser", "testPassword");
        userDao.insert(user);

        SchoolClass schoolClass = new SchoolClass("Math", "A", user.getUserId());
        classDao.insert(schoolClass);

        SchoolClass retrievedClass = classDao.getClass("Math", "A");

        // Assert that the retrieved class is not null and has the expected values
        assert(retrievedClass != null);
        assert(retrievedClass.getClassName().equals("Math"));
        assert(retrievedClass.getClassGrade().equals("A"));
    }

    @Test
    public void getAllClassesForUser() throws Exception {
        User user = new User("testUser", "testPassword");
        userDao.insert(user);

        SchoolClass class1 = new SchoolClass("Math", "A", user.getUserId());
        SchoolClass class2 = new SchoolClass("Science", "B", user.getUserId());

        classDao.insert(class1);
        classDao.insert(class2);

        List<SchoolClass> classList = classDao.getAllClassesForUser(user.getUserId());

        // Assert that the list is not null and contains the expected number of classes
        assert(classList != null);
        assert(classList.size() == 2);
    }

    @Test
    public void deleteClass() throws Exception {
        User user = new User("testUser", "testPassword");
        userDao.insert(user);

        SchoolClass schoolClass = new SchoolClass("Math", "A", user.getUserId());
        classDao.insert(schoolClass);

        classDao.delete(schoolClass);

        SchoolClass retrievedClass = classDao.getClass("Math", "A");

        // Assert that the retrieved class is null after deletion
        assert(retrievedClass == null);
    }

    @Test
    public void updateClass() throws Exception {
        User user = new User("testUser", "testPassword");
        userDao.insert(user);

        SchoolClass schoolClass = new SchoolClass("Math", "A", user.getUserId());
        classDao.insert(schoolClass);

        // Retrieve the class and update the grade
        SchoolClass retrievedClass = classDao.getClass("Math", "A");
        assert(retrievedClass != null);

        retrievedClass.setClassGrade("B");
        classDao.updateClass(retrievedClass);

        // Retrieve the class again and assert that the grade has been updated
        SchoolClass updatedClass = classDao.getClass("Math", "B");
        assert(updatedClass != null);
        assert(updatedClass.getClassGrade().equals("B"));
    }
}
