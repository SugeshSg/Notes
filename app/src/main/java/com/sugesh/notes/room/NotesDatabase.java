package com.sugesh.notes.room;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.sugesh.notes.entities.Notes;
import com.sugesh.notes.entities.Users;
import com.sugesh.notes.util.AppConstants;

@Database(entities = {Notes.class, Users.class},version = 1)
public abstract class NotesDatabase extends RoomDatabase {

    private static NotesDatabase instance ;

    abstract NotesDao notesDao();

    static synchronized NotesDatabase getInstanc(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),NotesDatabase.class, AppConstants.NOTES_DATABASE)
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

}
