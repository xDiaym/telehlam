package org.juicecode.telehlam.database.messages;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.juicecode.telehlam.core.contacts.ContactDao;
import org.juicecode.telehlam.core.contacts.User;
import org.juicecode.telehlam.database.AppDataBase;
import org.juicecode.telehlam.database.DBClient;

import java.util.List;

public class ContactDataBaseTask {

    private AppDataBase appDataBase;
    private ContactDao contactDao;

    public ContactDataBaseTask(Context context) {
        this.appDataBase = DBClient.getInstance(context).getAppDataBase();
        this.contactDao = appDataBase.contactDao();
    }
    public LiveData<List<User>> getAllUsers(final LifecycleOwner lifecycleOwner){
        final MutableLiveData<List<User>> users = new MutableLiveData<>();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
               contactDao.getAll().observe(lifecycleOwner, new Observer<List<User>>() {
                    @Override
                    public void onChanged(List<User> newUsers) {
                        users.setValue(newUsers);
                    }
                });
            }
        });
        return users;
    }
}
