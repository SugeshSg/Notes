package com.sugesh.notes.ui.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class SignUpActivity extends AppCompatActivity {
    private NotesViewModel notesViewModel;
    private Button signUpButton;
    private EditText userEmailView;
    private EditText passwordView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("Sign Up");
        userEmailView =  findViewById(R.id.userEmailEd);
        passwordView =  findViewById(R.id.userPasswordEd);
        signUpButton = findViewById(R.id.submitBtn);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userEmail  = userEmailView.getText().toString().trim();
                String password =  passwordView.getText().toString().trim();
                if (!userEmail.isEmpty() && !password.isEmpty()) {
                    Users users = new Users(userEmail, password, userEmail+password);
                    notesViewModel = ViewModelProviders.of(SignUpActivity.this).get(NotesViewModel.class);
                    notesViewModel.insertUser(users);
                    Toast.makeText(SignUpActivity.this, "Signup success", Toast.LENGTH_SHORT).show();
                    userEmailView.setText("");
                    passwordView.setText("");
                    finish();
                }else if (userEmail.isEmpty())
                {
                    Toast.makeText(SignUpActivity.this, "Email should not be empty", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(SignUpActivity.this, "Password should not be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
