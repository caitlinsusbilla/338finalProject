package com.example.a338rebuiltfinalproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class adminActivity extends AppCompatActivity {

    Button deleteUser;
    Button backButton;

    Button editUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        deleteUser = findViewById(R.id.deleteUser);
        backButton = findViewById(R.id.adminBackButton);
        editUserButton = findViewById(R.id.editUser);

        boolean isAdmin = getIntent().getBooleanExtra("isAdmin", true);
        String username = getIntent().getStringExtra("USERNAME");
        displayUsername(username);


        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), deleteUser.class);
                intent.putExtra("USERNAME",username);
                intent.putExtra("isAdmin",isAdmin);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LandingPage.class);
                intent.putExtra("USERNAME",username);
                intent.putExtra("isAdmin",isAdmin);
                startActivity(intent);
            }
        });

        editUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), editUser.class);
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
                TextView welcomeMessage = findViewById(R.id.welcomeAdmin);
                welcomeMessage.setText("Welcome, " + username);
                welcomeMessage.setVisibility(View.VISIBLE);
            }
        });
    }


}