package com.sugesh.notes.ui.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sugesh.notes.R;
import com.sugesh.notes.entities.Users;
import com.sugesh.notes.ui.viewmodel.NotesViewModel;
import com.sugesh.notes.util.AppConstants;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Users users;
    private NotesViewModel notesViewModel;
    private Button signUpButton;
    private Button loginButton;
    private EditText userEmailView;
    private EditText passwordView;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle("Login");

        userEmailView = findViewById(R.id.userEmailEd);
        passwordView = findViewById(R.id.userPasswordEd);
        signUpButton = findViewById(R.id.signUpBtn);
        loginButton = findViewById(R.id.loginBtn);
        preferences = this.getSharedPreferences(AppConstants.NOTES_PREFERENCE, Context.MODE_PRIVATE);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String userEmail = userEmailView.getText().toString().trim();
                final String password = passwordView.getText().toString().trim();
                notesViewModel = ViewModelProviders.of(LoginActivity.this).get(NotesViewModel.class);
                notesViewModel.getAllUsers().observe(LoginActivity.this, new Observer<List<Users>>() {
                    @Override
                    public void onChanged(@Nullable List<Users> userslist) {
                        boolean login_status = false;
                        String userID = null;
                        if (userslist != null && !userEmail.isEmpty() && !password.isEmpty())
                        {
                            for (Users users : userslist)
                            {
                                if (users.getEmail_id().equals(userEmail) && users.getPassword().equals(password)) {
                                    userID = users.getUserID();
                                    login_status = true;
                                    break;
                                }
                            }
                        }
                        if (login_status)
                        {
                            Toast.makeText(LoginActivity.this, "Login success", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(AppConstants.USER_ID, userID);
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else
                        {
                            Toast.makeText(LoginActivity.this, "Invalid email/password", Toast.LENGTH_SHORT).show();
                            passwordView.setText("");
                        }
                    }
                    });


                }
            });
        }
    }
