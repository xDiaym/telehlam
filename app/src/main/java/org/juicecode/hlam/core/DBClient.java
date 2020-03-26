package org.juicecode.hlam.core;

import android.content.Context;

import androidx.room.Room;

import org.juicecode.hlam.core.contacts.AppDataBase;

public class DBClient {
    private Context context;
    private static DBClient instance;

    private AppDataBase appDatabase;

    private DBClient(Context context) {
        this.context = context;
        appDatabase = Room.databaseBuilder(context, AppDataBase.class, "DataBase").build();
    }

    public static DBClient getInstance(Context mCtx) {
        if (instance == null) {
            instance = new DBClient(mCtx);
        }
        return instance;
    }

    public AppDataBase getAppDatabase() {
        return appDatabase;
    }
}

