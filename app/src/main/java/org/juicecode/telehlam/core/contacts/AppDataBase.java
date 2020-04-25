package org.juicecode.telehlam.core.contacts;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 3)
public abstract class AppDataBase extends RoomDatabase {
    public abstract ContactDao contactDao();
}
