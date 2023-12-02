package com.example.a338rebuiltfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class updateClass extends AppCompatActivity {

    private Spinner classSpinner;
    private EditText updatedClassNameEditText;
    private EditText updatedClassGradeEditText;
    private Button updateButton;
    private List<SchoolClass> classList;
    private ArrayAdapter<SchoolClass> spinnerAdapter;

    private String username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        username = getIntent().getStringExtra("USERNAME");
        int userId = getIntent().getIntExtra("USER_ID",0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_class);

        classSpinner = findViewById(R.id.classSpinner);
        updatedClassNameEditText = findViewById(R.id.updatedClassName);
        updatedClassGradeEditText = findViewById(R.id.updatedClassGrade);
        updateButton = findViewById(R.id.updateButton);

        // Retrieve the list of classes from the database
        classList = retrieveClassListFromDatabase();

        // Create an ArrayAdapter for the Spinner
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, classList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the Spinner
        classSpinner.setAdapter(spinnerAdapter);

        // Add the logic to update the class when the user clicks the update button
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the selected class from the Spinner
                SchoolClass selectedClass = (SchoolClass)classSpinner.getSelectedItem();

                // Retrieve the updated information from the EditText fields
                String updatedClassName = updatedClassNameEditText.getText().toString();
                String updatedClassGrade = updatedClassGradeEditText.getText().toString();

                // Update the selected class with the new information
                selectedClass.setClassName(updatedClassName);
                selectedClass.setClassGrade(updatedClassGrade);

                // Update the class in the database
                updateClassInDatabase(selectedClass);

                // Notify the user that the class is updated
                Toast.makeText(updateClass.this, "Class updated!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private List<SchoolClass> retrieveClassListFromDatabase() {
        final List<SchoolClass>[] result = new List[]{null};
        int userId = getIntent().getIntExtra("USER_ID",0);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Implement the logic to retrieve the list of classes from the database
                // (e.g., use Room Database or any other database framework)
                AppDatabase database = AppDatabase.getDatabase(getApplicationContext());
                SchoolClassDAO classDao = database.schoolclassDao();
                result[0] = classDao.getAllClassesForUser(userId);
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result[0];
    }

    private void updateClassInDatabase(final SchoolClass updatedClass) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Perform the database update using Room or any other database framework
                AppDatabase database = AppDatabase.getDatabase(getApplicationContext());
                SchoolClassDAO classDao = database.schoolclassDao();
                classDao.updateClass(updatedClass);

                // Notify the user that the class is updated on the main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(updateClass.this, "Class updated!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), LandingPage.class); // send to landing page
                        intent.putExtra("USERNAME", username);
                        boolean isAdmin = getIntent().getBooleanExtra("isAdmin", true);
                        intent.putExtra("isAdmin", isAdmin); //pass to LandingPage
                        startActivity(intent);
                    }
                });
            }
        }).start();

    }

}