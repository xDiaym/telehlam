package org.juicecode.telehlam.database.messages;

import android.app.Activity;
import android.os.AsyncTask;

import org.juicecode.telehlam.MainActivity;
import org.juicecode.telehlam.database.DBClient;

public class InsertMessage extends AsyncTask<Void, Void, Void> {
    private Activity activity;
    private Message message;

    public InsertMessage(Activity activity, Message message) {
        this.activity = activity;
        this.message = message;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        DBClient
                .getInstance(activity)
                .getAppDataBase()
                .messageDao()
                .insert(message);
        return null;
    }
}
