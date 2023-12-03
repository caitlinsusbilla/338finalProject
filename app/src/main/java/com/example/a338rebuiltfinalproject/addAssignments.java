package com.example.a338rebuiltfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a338rebuiltfinalproject.databinding.ActivityAddAssignmentsBinding;
import com.example.a338rebuiltfinalproject.databinding.ActivityAddClassBinding;

public class addAssignments extends AppCompatActivity {
    Button addAssignment;

    // String username = getIntent().getStringExtra("username");
    ActivityAddAssignmentsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignments);

        binding = ActivityAddAssignmentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addAssignment = binding.addAssButton;

        String username = getIntent().getStringExtra("USERNAME");
        int userId = getIntent().getIntExtra("USER_ID",0);
        int classId = getIntent().getIntExtra("CLASS_ID",0);

        Log.d("class", String.valueOf(classId));

        addAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText addAssNameInput = findViewById(R.id.add_ass_name);
                EditText addDateInput = findViewById(R.id.add_date);

                // convert inputs to string
                String assName = addAssNameInput.getText().toString();
                String addDate = addDateInput.getText().toString();

                SharedPreferences.Editor editor = getSharedPreferences("myapp", MODE_PRIVATE).edit();

                editor.putString("addedAss", assName);
                editor.putString("addedDate", addDate);
                editor.apply();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase database = AppDatabase.getDatabase(getApplicationContext());
                        AssignmentsDAO assDao = database.assignmentsDao();

                        Assignments newAss = new Assignments(assName, addDate);
                        newAss.setAssId(classId); //save it to the specific class

                        assDao.insert(newAss);
                        Log.d("mytab", newAss.getAssName());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(addAssignments.this, assName + " is added! ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();

                Intent intent = new Intent(getApplicationContext(), viewClass.class); // send to landing page
                intent.putExtra("USERNAME", username);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("CLASS_ID", classId);
                boolean isAdmin = getIntent().getBooleanExtra("isAdmin", true);
                intent.putExtra("isAdmin", isAdmin); //pass to LandingPage
                startActivity(intent);
            }
        });
    }
}