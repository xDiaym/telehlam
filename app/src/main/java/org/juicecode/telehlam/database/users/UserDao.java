package org.juicecode.telehlam.database.users;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM contacts")
    LiveData<List<User>> getAll();

    @Query("SELECT id FROM contacts")
    LiveData<List<Long>> getUsersIds();

    @Insert
    void insert(User User);

    @Query("DELETE FROM contacts")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM contacts WHERE login = :login")
    Integer findByLogin(String login);
}