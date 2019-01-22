package com.sugesh.notes.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.sugesh.notes.R;
import com.sugesh.notes.util.AppConstants;

public class AddNoteActivity extends AppCompatActivity {

    private EditText titleView;
    private EditText descriptionView;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        SharedPreferences preferences = this.getSharedPreferences(AppConstants.NOTES_PREFERENCE, Context.MODE_PRIVATE);
        userID = preferences.getString(AppConstants.USER_ID,null);
        titleView = findViewById(R.id.edit_text_title);
        descriptionView = findViewById(R.id.edit_text_description);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Note");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return  true;
                default:
                    return super.onOptionsItemSelected(item);
        }

    }
    private void saveNote(){
        String title = titleView.getText().toString();
        String description = descriptionView.getText().toString();

        if (title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this,"Please enter Title/Description",Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(AppConstants.NOTES_TITLE, title);
        data.putExtra(AppConstants.NOTES_DESC, description);
        data.putExtra(AppConstants.USER_ID, userID);
        setResult(RESULT_OK, data);
        finish();
    }
}
