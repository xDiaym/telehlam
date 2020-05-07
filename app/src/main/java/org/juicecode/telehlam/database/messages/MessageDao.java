package org.juicecode.telehlam.database.messages;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM messages WHERE type=1 and receiverId=:userId OR type = 0 and authorId=:userId")
    LiveData<List<Message>> getAllById(long userId);

    @Insert
    void insert(Message message);
    @Query("DELETE FROM messages")
    void deleteAll();

    @Query("SELECT * FROM messages WHERE id = ((SELECT MAX(ID)  FROM messages)) and receiverId=:user or authorId=:user")
    Message getLastMessage(long user);
}
