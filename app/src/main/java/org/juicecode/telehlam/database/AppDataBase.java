package org.juicecode.telehlam.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.juicecode.telehlam.core.contacts.Contact;
import org.juicecode.telehlam.core.contacts.ContactDao;
import org.juicecode.telehlam.database.messages.Message;
import org.juicecode.telehlam.database.messages.MessageDao;

@Database(entities =  {Message.class, Contact.class}, version = 2)
public abstract class AppDataBase extends RoomDatabase {
    public abstract MessageDao messageDao();
    public abstract ContactDao contactDao();
}
