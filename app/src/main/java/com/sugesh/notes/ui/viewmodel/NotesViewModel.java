package com.sugesh.notes.ui.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.sugesh.notes.entities.Notes;
import com.sugesh.notes.entities.Users;
import com.sugesh.notes.room.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {
    private NotesRepository repository;
    private LiveData<List<Notes>> allNotes;
    private LiveData<List<Users>> allUsers;

    public NotesViewModel(@NonNull Application application) {
        super(application);
        repository = new NotesRepository(application);
        allNotes = repository.getAllNotes();
        allUsers =  repository.getAllUsers();

    }

    public void insert(Notes notes) {
        repository.insert(notes);

    }

    public void insertUser(Users users) {
        repository.insertUser(users);

    }

    public LiveData<List<Users>> getAllUsers() {
        return allUsers;
    }

    public LiveData<List<Notes>> getAllNotes() {
        return allNotes;
    }
}
