package com.example.a338rebuiltfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class editAssignments extends AppCompatActivity {
    private Spinner assSpinner;
    private EditText updatedAssNameEditText;
    private EditText updatedAssDateEditText;
    private Button updateAssButton;
    private List<Assignments> assList;
    private ArrayAdapter<Assignments> spinnerAdapter;

    private String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        username = getIntent().getStringExtra("USERNAME");
        int userId = getIntent().getIntExtra("USER_ID",0);
        int classId = getIntent().getIntExtra("CLASS_ID",0);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignments);

        assSpinner = findViewById(R.id.assSpinner);
        updatedAssNameEditText = findViewById(R.id.updatedAssName);
        updatedAssDateEditText = findViewById(R.id.updatedAssDate);
        updateAssButton = findViewById(R.id.updateAssButton);

        assList = retrieveAssListFromDatabase();

        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, assList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        assSpinner.setAdapter(spinnerAdapter);

        updateAssButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the selected class from the Spinner
                Assignments selectedAss = (Assignments)assSpinner.getSelectedItem();

                // Retrieve the updated information from the EditText fields
                String updatedAssName = updatedAssNameEditText.getText().toString();
                String updatedAssDate = updatedAssDateEditText.getText().toString();

                // Update the selected class with the new information
                selectedAss.setAssName(updatedAssName);
                selectedAss.setAssDue(updatedAssDate);

                // Update the class in the database
                updateAssInDatabase(selectedAss);

                // Notify the user that the class is updated
                Toast.makeText(editAssignments.this, "Assignment updated!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private List<Assignments> retrieveAssListFromDatabase() {
        final List<Assignments>[] result = new List[]{null};
        int classId = getIntent().getIntExtra("CLASS_ID",0);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase database = AppDatabase.getDatabase(getApplicationContext());
                AssignmentsDAO assDao = database.assignmentsDao();
                result[0] = assDao.getAllAssForUser(classId);
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

    private void updateAssInDatabase(final Assignments updatedAss) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase database = AppDatabase.getDatabase(getApplicationContext());
                AssignmentsDAO assDao = database.assignmentsDao();
                assDao.updateAss(updatedAss);

                // Notify the user that the class is updated on the main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(editAssignments.this, "Assignment updated!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), viewClass.class); // send to landing page
                        intent.putExtra("USERNAME", username);
                        boolean isAdmin = getIntent().getBooleanExtra("isAdmin", true);
                        intent.putExtra("isAdmin", isAdmin); //pass to viewClass
                        startActivity(intent);
                    }
                });
            }
        }).start();

    }
}