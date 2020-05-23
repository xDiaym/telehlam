package org.juicecode.telehlam.database.users;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.juicecode.telehlam.database.DBClient;

import java.util.List;

public class UserRepository {
    private UserDao dao;

    public UserRepository(Application application) {
        dao = DBClient.getInstance(application).getAppDataBase().userDao();
    }

    public LiveData<List<User>> getAllUsers() {
        return dao.getAll();
    }

    public void insert(User user) {
        new InsertAsyncTask(dao).execute(user);
    }

    public LiveData<List<Long>> getUsersIds() {
        return dao.getUsersIds();
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(dao).execute();
    }


    private static class InsertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao dao;

        InsertAsyncTask(UserDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(User... users) {
            if (users.length == 1) {
                if(dao.findById(users[0].getId())==0){
                    dao.insert(users[0]);
                }

            }
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao dao;

        DeleteAllAsyncTask(UserDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }
}
