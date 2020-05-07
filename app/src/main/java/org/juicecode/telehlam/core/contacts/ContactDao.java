package org.juicecode.telehlam.core.contacts;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM contacts")
    LiveData<List<User>> getAll();

    @Query("SELECT * FROM contacts WHERE id = :id")
    User getById(long id);

    @Query("SELECT * FROM contacts WHERE login = :login")
    User getByLogin(String login);

    @Query("SELECT COUNT(*) FROM contacts WHERE id = :id")
    int getNumberOfContactsById(long id);

    @Insert
    void insert(User User);

    @Update
    void update(User User);
}