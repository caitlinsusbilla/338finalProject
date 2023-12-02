package com.example.a338rebuiltfinalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class deleteUser extends AppCompatActivity {

    Button delUserBackButton;

    private List<User> userList = new ArrayList<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        ListView listView = findViewById(R.id.listUser);
        delUserBackButton = findViewById(R.id.delUserBackButton);

        String username = getIntent().getStringExtra("USERNAME");
        boolean isAdmin = getIntent().getBooleanExtra("isAdmin", true);

        // retrieve users
        new Thread(new Runnable() {
            @Override
            public void run() {
                userList = AppDatabase.getDatabase(deleteUser.this).userDao().getAllUsers();
                ArrayAdapter adapter = new ArrayAdapter<>(deleteUser.this,android.R.layout.simple_list_item_1, userList);
                listView.setAdapter(adapter);

            }
        }).start();

        // populate the list that the user can click
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User indexUser = userList.get(position);
                Log.d("mytag", indexUser.toString());
                int indexUserId = indexUser.getLogId();

                // confirm to delete user
                showDeleteConfirmationDialog(indexUserId, indexUser);
            }
        });

        delUserBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LandingPage.class);
                intent.putExtra("USERNAME",username);
                intent.putExtra("isAdmin",isAdmin);
                startActivity(intent);
            }
        });
    }

    private void showDeleteConfirmationDialog(int userId, User deleteUser) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this user?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked "Yes", proceed with deletion

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                AppDatabase database = AppDatabase.getDatabase(getApplicationContext());
                                UserDAO userDao = database.userDao();

                                userDao.delete(deleteUser);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(deleteUser.this, deleteUser.getUsername() + " had been deleted! ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).start();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked "No", do nothing
                        dialog.dismiss();
                    }
                });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    }
