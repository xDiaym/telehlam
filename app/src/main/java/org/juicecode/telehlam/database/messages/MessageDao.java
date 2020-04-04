package org.juicecode.telehlam.database.messages;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM messages")
    List<Message> getAll();

    @Insert
    void insert(Message message);
}
