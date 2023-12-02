package com.example.a338rebuiltfinalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a338rebuiltfinalproject.databinding.ActivityAddClassBinding;


public class addClass extends AppCompatActivity {

    Button addClass;

   // String username = getIntent().getStringExtra("username");
    ActivityAddClassBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        binding = ActivityAddClassBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addClass = binding.addClassButton;

        String username = getIntent().getStringExtra("USERNAME");
        int userId = getIntent().getIntExtra("USER_ID",0);

        addClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText addClassNameInput = findViewById(R.id.add_class_name);
                EditText addGradeInput = findViewById(R.id.grade_add_class);

                // convert inputs to string
                String className = addClassNameInput.getText().toString();
                String addGrade = addGradeInput.getText().toString();

                SharedPreferences.Editor editor = getSharedPreferences("myapp", MODE_PRIVATE).edit();

                editor.putString("addedClass", className);
                editor.putString("addedGrade", addGrade);
                editor.apply();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase database = AppDatabase.getDatabase(getApplicationContext());
                        SchoolClassDAO classDao = database.schoolclassDao();

                        SchoolClass newClass = new SchoolClass(className, addGrade);
                        newClass.setUserId(userId); //save it to the specific user

                        classDao.insert(newClass);
                        Log.d("mytab", newClass.getClassName());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(addClass.this, className + " is added! ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();

                Intent intent = new Intent(getApplicationContext(), LandingPage.class); // send to landing page
                intent.putExtra("USERNAME", username);
                intent.putExtra("USER_ID", userId);
                boolean isAdmin = getIntent().getBooleanExtra("isAdmin", true);
                intent.putExtra("isAdmin", isAdmin); //pass to LandingPage
                startActivity(intent);
            }
        });
    }
}