package org.juicecode.hlam.core;

import android.content.Context;

import androidx.room.Room;

import org.juicecode.hlam.core.contacts.AppDataBase;

public class DBClient {
    private Context mCtx;
    private static DBClient mInstance;

    private AppDataBase appDatabase;

    private DBClient(Context mCtx) {
        this.mCtx = mCtx;
        //Создание БД - MyToDos
        appDatabase = Room.databaseBuilder(mCtx, AppDataBase.class, "DataBase").build();
    }

    public static DBClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DBClient(mCtx);
        }
        return mInstance;
    }

    public AppDataBase getAppDatabase() {
        return appDatabase;
    }
}

