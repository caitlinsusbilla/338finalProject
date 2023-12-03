//package com.example.a338rebuiltfinalproject;
//
//import android.content.Context;
//import androidx.room.Room;
//import androidx.test.core.app.ApplicationProvider;
//import com.example.a338rebuiltfinalproject.Assignments;
//import com.example.a338rebuiltfinalproject.AssignmentsDAO;
//import com.example.a338rebuiltfinalproject.SchoolClass;
//import com.example.a338rebuiltfinalproject.SchoolClassDAO;
//import com.example.a338rebuiltfinalproject.User;
//import com.example.a338rebuiltfinalproject.UserDAO;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.io.IOException;
//import java.util.List;
//
//public class AssignmentsDAOUnitTest {
//
//    private AssignmentsDAO assignmentsDao;
//    private SchoolClassDAO classDao;
//    private UserDAO userDao;
//    private UserDatabase userDatabase;
//
//    @Before
//    public void createDb() {
//        Context context = ApplicationProvider.getApplicationContext();
//        userDatabase = Room.inMemoryDatabaseBuilder(context, UserDatabase.class).build();
//        userDao = userDatabase.getUserDAO();
//        classDao = userDatabase.getSchoolClassDAO();
//        assignmentsDao = userDatabase.getAssignmentsDAO();
//    }
//
//    @After
//    public void closeDb() throws IOException {
//        userDatabase.close();
//    }
//
//    @Test
//    public void insertAndGetAssignment() throws Exception {
//        User user = new User("testUser", "testPassword");
//        userDao.insert(user);
//
//        SchoolClass schoolClass = new SchoolClass("Math", "A", user.getUserId());
//        classDao.insert(schoolClass);
//
//        Assignments assignment = new Assignments("Assignment1", "2023-01-01", schoolClass.getClassId());
//        assignmentsDao.insert(assignment);
//
//        Assignments retrievedAssignment = assignmentsDao.getClass("Assignment1", "2023-01-01");
//
//        // Assert that the retrieved assignment is not null and has the expected values
//        assert (retrievedAssignment != null);
//        assert (retrievedAssignment.getAssName().equals("Assignment1"));
//        assert (retrievedAssignment.getAssDue().equals("2023-01-01"));
//    }
//
//    @Test
//    public void getAllAssignmentsForClass() throws Exception {
//        User user = new User("testUser", "testPassword");
//        userDao.insert(user);
//
//        SchoolClass schoolClass = new SchoolClass("Math", "A", user.getUserId());
//        classDao.insert(schoolClass);
//
//        Assignments assignment1 = new Assignments("Assignment1", "2023-01-01", schoolClass.getClassId());
//        Assignments assignment2 = new Assignments("Assignment2", "2023-01-02", schoolClass.getClassId());
//
//        assignmentsDao.insert(assignment1);
//        assignmentsDao.insert(assignment2);
//
//        List<Assignments> assignmentList = assignmentsDao.getAllAssForUser(schoolClass.getClassId());
//
//        // Assert that the list is not null and contains the expected number of assignments
//        assert (assignmentList != null);
//        assert (assignmentList.size() == 2);
//    }
//
//    @Test
//    public void deleteAssignment() throws Exception {
//        User user = new User("testUser", "testPassword");
//        userDao.insert(user);
//
//        SchoolClass schoolClass = new SchoolClass("Math", "A", user.getUserId());
//        classDao.insert(schoolClass);
//
//        Assignments assignment = new Assignments("Assignment1", "2023-01-01", schoolClass.getClassId());
//        assignmentsDao.insert(assignment);
//
//        assignmentsDao.delete(assignment);
//
//        Assignments retrievedAssignment = assignmentsDao.getClass("Assignment1", "2023-01-01");
//
//        // Assert that the retrieved assignment is null after deletion
//        assert (retrievedAssignment == null);
//    }
//
//    @Test
//    public void updateAssignment() throws Exception {
//        User user = new User("testUser", "testPassword");
//        userDao.insert(user);
//
//        SchoolClass schoolClass = new SchoolClass("Math", "A", user.getUserId());
//        classDao.insert(schoolClass);
//
//        Assignments assignment = new Assignments("Assignment1", "2023-01-01", schoolClass.getClassId());
//        assignmentsDao.insert(assignment);
//
//        // Retrieve the assignment and update the due date
//        Assignments retrievedAssignment = assignmentsDao.getClass("Assignment1", "2023-01-01");
//        assert (retrievedAssignment != null);
//
//        retrievedAssignment.setAssDue("2023-02-01");
//        assignmentsDao.updateClass(retrievedAssignment);
//
//        // Retrieve the assignment again and assert that the due date has been updated
//        Assignments updatedAssignment = assignmentsDao.getClass("Assignment1", "2023-02-01");
//        assert (updatedAssignment != null);
//        assert (updatedAssignment.getAssDue().equals("2023-02-01"));
//    }
//}