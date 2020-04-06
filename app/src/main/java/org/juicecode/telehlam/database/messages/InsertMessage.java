package org.juicecode.telehlam.database.messages;

import android.os.AsyncTask;

import org.juicecode.telehlam.MainActivity;
import org.juicecode.telehlam.database.DBClient;

public class InsertMessage<T> extends AsyncTask<Void, Void, T> {
    private MainActivity activity;
    private Message message;

    public InsertMessage(MainActivity activity, Message message) {
        this.activity = activity;
        this.message = message;
    }

    @Override
    protected T doInBackground(Void... voids) {
        DBClient
                .getInstance(activity)
                .getAppDataBase()
                .messageDao()
                .insert(message);
        return null;
    }
}
