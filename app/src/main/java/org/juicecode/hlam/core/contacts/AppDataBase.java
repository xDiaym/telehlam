package org.juicecode.hlam.core.contacts;

import androidx.room.Database;
import androidx.room.RoomDatabase;
@Database(entities = {Contact.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract ContactDao contactDao();
}
