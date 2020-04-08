package org.juicecode.telehlam.database;

import android.content.Context;

import static androidx.room.Room.databaseBuilder;

public class DBClient {
    private AppDataBase appDataBase;

    private static DBClient instance;

    private DBClient(Context context) {
        appDataBase = databaseBuilder(context, AppDataBase.class, "DataBase").fallbackToDestructiveMigration().build();
    }

    public static DBClient getInstance(Context context) {
        if (instance == null) {
            instance = new DBClient(context);
        }
        return instance;
    }

    public AppDataBase getAppDataBase() {
        return appDataBase;
    }

}