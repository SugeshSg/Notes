package com.sugesh.notes.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.sugesh.notes.entities.Notes;
import com.sugesh.notes.entities.Users;
import com.sugesh.notes.util.Callback;

import java.util.List;

public class NotesRepository {

    private NotesDao notesDao;
    private Users user;

    private LiveData<List<Notes>> allNotes;
    private LiveData<List<Users>> allUsers;

    public NotesRepository(Application application){
        NotesDatabase database = NotesDatabase.getInstanc(application);
        notesDao = database.notesDao();
        allNotes = notesDao.getNotes();
        allUsers = notesDao.getUsers();

    }

    public void insert(Notes notes) {
        new InsertNotesAsyncTask(notesDao).execute(notes);
    }

    public void insertUser(Users users) {
        new InsertUserAsyncTask(notesDao).execute(users);
    }

    public void isLoggedUser(Users users, Callback<Users> callback) {
//       return notesDao.isLoggedUser(users.email_id,users.password);
        new CheckLoggedUserAsyncTask(notesDao,callback).execute(users,users);

    }

    public LiveData<List<Users>> getAllUsers() {
        return allUsers;
    }

    public LiveData<List<Notes>> getAllNotes() {
        return allNotes;
    }

    private  static class InsertNotesAsyncTask extends AsyncTask<Notes,Void,Void>{

        private NotesDao notesDao;

        private InsertNotesAsyncTask(NotesDao notesDao){
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Notes... notes) {
            notesDao.insert(notes[0]);
            return null;
        }
    }

    private  static class InsertUserAsyncTask extends AsyncTask<Users,Void,Void>{

        private NotesDao notesDao;

        private InsertUserAsyncTask(NotesDao notesDao){
            this.notesDao = notesDao;
        }

        @Override
        protected Void doInBackground(Users... users) {
            notesDao.insertUser(users[0]);
            return null;
        }
    }
    private  static class CheckLoggedUserAsyncTask extends AsyncTask<Users,Void,Users>{

        private NotesDao notesDao;
        private Callback<Users> callback;

        private CheckLoggedUserAsyncTask(NotesDao notesDao,Callback<Users> callback){
            this.notesDao = notesDao;
            this.callback = callback;
        }

        @Override
        protected Users doInBackground(Users... users) {

            return notesDao.isLoggedUser(users[0].email_id,users[0].password);
        }

        @Override
        protected void onPostExecute(Users result) {
            super.onPostExecute(result);
            callback.onComplete(result);
        }
    }


}
