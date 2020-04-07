package org.juicecode.telehlam.core.contacts;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contacts")
    LiveData<List<Contact>> getAll();
    @Query("SELECT * FROM contacts WHERE contactId = :id")
    Contact getById(long id);
    @Query("SELECT * FROM contacts WHERE phone=:phone")
    Contact getByPhone(String phone);
    @Query("SELECT COUNT(*) FROM contacts WHERE phone=:phone")
    int getNumberOfContactsByPhone(String phone);
    @Insert
    void insert (Contact contact);
    @Insert
    void insertMany (ArrayList<Contact> contacts);
    @Delete
    void delete(Contact contact);
    @Query("DELETE FROM contacts WHERE contactId = :id")
    void deleteById(long id);
    @Query("DELETE FROM contacts")
    void deleteAll();
    @Query("DELETE FROM contacts WHERE phone=:userPhone")
    void deleteByPhone(String userPhone);
    @Update
    void update (Contact contact);
}