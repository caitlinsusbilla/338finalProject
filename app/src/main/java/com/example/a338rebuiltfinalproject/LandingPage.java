package com.example.a338rebuiltfinalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a338rebuiltfinalproject.databinding.ActivityLandingPageBinding;

public class LandingPage extends AppCompatActivity {
    ActivityLandingPageBinding binding;

    Button adminButton;
    Button signOutButton;
    Button viewClassButton;
    Button addClassButton;
    Button updateClassButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adminButton = binding.adminButton;
        signOutButton = binding.signout;
        viewClassButton = binding.viewClass;
        addClassButton = binding.addClass;
        updateClassButton = binding.updateClass;

        int userId = getIntent().getIntExtra("USER_ID",0);
        Log.d("login", String.valueOf(userId));
        String username = getIntent().getStringExtra("USERNAME");
        displayUsername(username);


        boolean isAdmin = getIntent().getBooleanExtra("isAdmin", true);
        if(!isAdmin){
            adminButton.setVisibility(View.INVISIBLE);
        }else{
            adminButton.setVisibility(View.VISIBLE);
        }

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        viewClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), viewClass.class);
                intent.putExtra("USERNAME",username);
                intent.putExtra("isAdmin",isAdmin);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });

        updateClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), updateClass.class);
                intent.putExtra("USERNAME",username);
                intent.putExtra("isAdmin",isAdmin);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });

        addClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), addClass.class);
                intent.putExtra("USERNAME",username);
                intent.putExtra("isAdmin",isAdmin);
                intent.putExtra("USER_ID", userId);
                startActivity(intent);
            }
        });

        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), adminActivity.class);
                intent.putExtra("USERNAME",username);
                intent.putExtra("isAdmin",isAdmin);
                startActivity(intent);
            }
        });
    }

    private void displayUsername(final String username) {
        runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                TextView welcomeMessage = findViewById(R.id.welcomeMessage);
                welcomeMessage.setText("Welcome, " + username);
                welcomeMessage.setVisibility(View.VISIBLE);
            }
        });
    }
}
