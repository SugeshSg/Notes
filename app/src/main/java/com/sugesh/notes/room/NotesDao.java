package com.sugesh.notes.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.sugesh.notes.entities.Notes;
import com.sugesh.notes.entities.Users;

import java.util.List;

@Dao
public interface NotesDao {

   @Insert
    void insert(Notes notes);

    @Insert
    void insertUser(Users users);

    @Query("SELECT * FROM notes_table")
    LiveData<List<Notes>> getNotes();

    @Query("SELECT * FROM user_table where email_id =:username and password =:password ")
    Users isLoggedUser(String username,String password);

    @Query("SELECT * FROM user_table")
    LiveData<List<Users>> getUsers();
}
