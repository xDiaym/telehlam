package org.juicecode.telehlam.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.juicecode.telehlam.database.messages.Message;
import org.juicecode.telehlam.database.messages.MessageDao;

@Database(entities =  {Message.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract MessageDao messageDao();
}
