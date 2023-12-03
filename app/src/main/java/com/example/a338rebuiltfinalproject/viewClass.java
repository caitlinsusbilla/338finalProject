package com.example.a338rebuiltfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class viewClass extends AppCompatActivity {

    Button backButton;

    private List<SchoolClass> classList = new ArrayList<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_class);

        ListView listView = findViewById(R.id.listClass);
        backButton = findViewById(R.id.backButton);

        String username = getIntent().getStringExtra("USERNAME");
        boolean isAdmin = getIntent().getBooleanExtra("isAdmin", true);
        int userId = getIntent().getIntExtra("USER_ID",0);

        Log.d("mytag", String.valueOf(userId));

        // retrieve classes
        new Thread(new Runnable() {
            @Override
            public void run() {
                classList = AppDatabase.getDatabase(viewClass.this).schoolclassDao().getAllClassesForUser(userId);
                ArrayAdapter adapter = new ArrayAdapter<>(viewClass.this,android.R.layout.simple_list_item_1, classList);
                listView.setAdapter(adapter);

            }
        }).start();

        // populate the list that the user can click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SchoolClass indexClass = classList.get(position);
                Log.d("mytag", indexClass.toString());
                int indexClassId = indexClass.getClassId();
                Log.d("viewClass", String.valueOf(indexClassId));
                Intent intent = new Intent(getApplicationContext(), viewAssignments.class);
                intent.putExtra("CLASS_ID", indexClassId);
                intent.putExtra("USERNAME", username);
                intent.putExtra("isAdmin", isAdmin);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
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
    }

}