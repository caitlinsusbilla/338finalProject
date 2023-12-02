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

import com.example.a338rebuiltfinalproject.databinding.ActivityLoginBinding;
public class Login extends AppCompatActivity {
    Button login;
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        login = binding.loginButton;

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText usernameInput = findViewById(R.id.username_login);
                EditText passwordInput = findViewById(R.id.password_login);

                // convert inputs to string
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();

                SharedPreferences.Editor editor = getSharedPreferences("myapp", MODE_PRIVATE).edit();
                editor.putString("Username", username);
                editor.apply();

                // background task for User user
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                            User user = AppDatabase.getDatabase(Login.this).userDao().getUser(username, password);

                            // check if user exists or is admin
                            if (user != null) {
                                boolean isAdmin = false;
                                if (user.getUsername().equals("admin2") && user.getPassword().equals("admin2")) {
                                    isAdmin = true;
                                }
                                Intent intent = new Intent(getApplicationContext(), LandingPage.class); // send to landing page
                                intent.putExtra("USERNAME", user.getUsername()); //pass to LandingPage
                                intent.putExtra("isAdmin", isAdmin); //pass to LandingPage
                                Log.d("login", String.valueOf(user.getLogId()));
                                intent.putExtra("USER_ID", user.getLogId());
                                startActivity(intent);
                            } else { // login failed
                                runOnUiThread(new Runnable() { // show on main thread
                                    @Override
                                    public void run() {
                                        Toast.makeText(Login.this, "Invalid username/password", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                    }
                }).start();

            }
        });
    }




}