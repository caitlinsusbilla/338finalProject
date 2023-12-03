package com.example.a338rebuiltfinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class viewAssignments extends AppCompatActivity {
    Button backButton;
    Button addAssButton;

    private List<Assignments> assList = new ArrayList<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_assignments);

        ListView listView = findViewById(R.id.listAssignments);
        backButton = findViewById(R.id.assignBackButton);
        addAssButton = findViewById(R.id.addAssignment);

        String username = getIntent().getStringExtra("USERNAME");
        boolean isAdmin = getIntent().getBooleanExtra("isAdmin", true);
        int userId = getIntent().getIntExtra("USER_ID",0);
        int classId = getIntent().getIntExtra("CLASS_ID",0);

        Log.d("mytag", String.valueOf(classId));

        // retrieve classes
        new Thread(new Runnable() {
            @Override
            public void run() {
                assList = AppDatabase.getDatabase(viewAssignments.this).assignmentsDao().getAllAssForUser(classId);
                ArrayAdapter adapter = new ArrayAdapter<>(viewAssignments.this,android.R.layout.simple_list_item_1, assList);
                listView.setAdapter(adapter);

            }
        }).start();

        // populate the list that the user can click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Assignments indexAss = assList.get(position);
                Log.d("mytag", indexAss.toString());
                int indexAssId = indexAss.getAssId();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LandingPage.class);
                intent.putExtra("USERNAME",username);
                intent.putExtra("isAdmin",isAdmin);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });

        addAssButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), addAssignments.class);
                intent.putExtra("USERNAME",username);
                intent.putExtra("isAdmin",isAdmin);
                intent.putExtra("USER_ID", userId);
                intent.putExtra("CLASS_ID", classId);
                startActivity(intent);
            }
        });
    }
}