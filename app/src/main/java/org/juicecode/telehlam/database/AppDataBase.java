package org.juicecode.telehlam.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.juicecode.telehlam.database.users.UserDao;
import org.juicecode.telehlam.database.users.User;
import org.juicecode.telehlam.database.messages.Message;
import org.juicecode.telehlam.database.messages.MessageDao;

@Database(entities = {Message.class, User.class}, version = 7)
public abstract class AppDataBase extends RoomDatabase {
    public abstract MessageDao messageDao();

    public abstract UserDao userDao();
}
