package com.sugesh.notes.ui.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.sugesh.notes.R;
import com.sugesh.notes.entities.Notes;
import com.sugesh.notes.ui.viewmodel.NotesViewModel;
import com.sugesh.notes.ui.adapter.NotesAdapter;
import com.sugesh.notes.util.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private NotesViewModel notesViewModel;

    private String userID;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = this.getSharedPreferences(AppConstants.NOTES_PREFERENCE, Context.MODE_PRIVATE);
        userID = preferences.getString(AppConstants.USER_ID, null);

        if (userID == null){
           Intent intent = new Intent(this,LoginActivity.class);
           startActivity(intent);
           finish();
           return;
        }


        FloatingActionButton addNoteBtn = findViewById(R.id.add_note_btn);
        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, AppConstants.ADD_NOTE_REQUEST);

            }
        });
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NotesAdapter adapter = new NotesAdapter();
        recyclerView.setAdapter(adapter);
        notesViewModel = ViewModelProviders.of(this).get(NotesViewModel.class);
        notesViewModel.getAllNotes().observe(this, new Observer<List<Notes>>() {
            @Override
            public void onChanged(@Nullable List<Notes> notes) {
                if (notes != null) {
                    List<Notes> userNotes = new ArrayList<>();
                    for (int index = 0; index < notes.size(); index++) {
                        if (userID.equals(notes.get(index).getUserID())) {
                            userNotes.add(notes.get(index));
                        }
                    }
                    adapter.setNotes(userNotes);
                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void logout() {
        SharedPreferences.Editor e = preferences.edit();
        e.remove(AppConstants.USER_ID);
        e.commit();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstants.ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AppConstants.NOTES_TITLE);
            String description = data.getStringExtra(AppConstants.NOTES_DESC);
            String userKey = data.getStringExtra(AppConstants.USER_ID);
            Notes notes = new Notes(title, description, userKey);
            notesViewModel.insert(notes);

            Toast.makeText(this, "Notes saved successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Unable to save notes", Toast.LENGTH_SHORT).show();
        }
    }
}
