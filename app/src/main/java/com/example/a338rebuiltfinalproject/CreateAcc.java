package com.example.a338rebuiltfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateAcc extends AppCompatActivity {

    Button createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        createAccount = findViewById(R.id.createFinalAccount);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText createUsername = findViewById(R.id.new_username);
                EditText createPassword = findViewById(R.id.new_password);
                EditText verifyPassword = findViewById(R.id.verify_password);

                String newUsername = createUsername.getText().toString();
                String newPassword = createPassword.getText().toString();
                String verifyNewPassword = verifyPassword.getText().toString();

                // Check for empty fields
                if (newUsername.isEmpty() || newPassword.isEmpty() || verifyNewPassword.isEmpty()) {
                    Toast.makeText(CreateAcc.this, "All fields must be filled.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if passwords match
                if (!newPassword.equals(verifyNewPassword)) {
                    Toast.makeText(CreateAcc.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (newPassword.length() < 5) {
                    Toast.makeText(CreateAcc.this, "Password must be more than 5 charaters", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check for other conditions, such as username availability, if needed.

                // add to USER_TABLE
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase database = AppDatabase.getDatabase(getApplicationContext());
                        UserDAO userDao = database.userDao();
                        User newUser = new User(newUsername, newPassword);
                        userDao.insert(newUser);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(CreateAcc.this, newUsername + " is added! ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).start();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class); // send to MainActivity
                startActivity(intent);
            }
        });
    }


}