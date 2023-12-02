package com.example.a338rebuiltfinalproject;

import android.annotation.SuppressLint;
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

public class editUser extends AppCompatActivity {
    private Spinner userSpinner;
    private EditText updateUsername;
    private EditText updatePassword;
    private Button updateUserButton;
    private Button backButton;
    private List<User> userList;
    private ArrayAdapter<User> spinnerAdapter;

    private String username;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean isAdmin = getIntent().getBooleanExtra("isAdmin", true);
        username = getIntent().getStringExtra("USERNAME");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        userSpinner = findViewById(R.id.userSpinner);
        updateUsername = findViewById(R.id.updateUsername);
        updatePassword = findViewById(R.id.updatePassword);
        updateUserButton = findViewById(R.id.editUserButton);
        backButton = findViewById(R.id.editBackButton);

        // Retrieve the list of classes from the database
        userList = retrieveUserListFromDatabase();

        // Create an ArrayAdapter for the Spinner
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set the adapter to the Spinner
        userSpinner.setAdapter(spinnerAdapter);

        // Add the logic to update the class when the user clicks the update button
        updateUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the selected class from the Spinner
                User selectedUser = (User)userSpinner.getSelectedItem();

                // Retrieve the updated information from the EditText fields
                String updatedUsername = updateUsername.getText().toString();
                String updatedPassword = updatePassword.getText().toString();

                // Update the selected class with the new information
                selectedUser.setUsername(updatedUsername);
                selectedUser.setPassword(updatedPassword);

                // Update the class in the database
                updateUserInDatabase(selectedUser);

                // Notify the user that the class is updated
                Toast.makeText(editUser.this, "User Updated!", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), adminActivity.class);
                intent.putExtra("USERNAME",username);
                intent.putExtra("isAdmin",isAdmin);
                startActivity(intent);
            }
        });

    }

    private List<User> retrieveUserListFromDatabase() {
        final List<User>[] result = new List[]{null};

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //Retrieve the list of users from the database
                AppDatabase database = AppDatabase.getDatabase(getApplicationContext());
                UserDAO userDao = database.userDao();
                result[0] = userDao.getAllUsers();
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

    private void updateUserInDatabase(final User updatedUser) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AppDatabase database = AppDatabase.getDatabase(getApplicationContext());
                UserDAO userDao = database.userDao();
                userDao.updateUser(updatedUser);

                // Notify the user that the class is updated on the main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(editUser.this, "User updated!", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), adminActivity.class); // send to landing page
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