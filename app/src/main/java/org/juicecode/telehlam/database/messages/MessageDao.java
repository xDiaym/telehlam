package org.juicecode.telehlam.database.messages;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM messages")
    LiveData<List<Message>> getAll();

    @Query("SELECT * FROM messages WHERE (type = 1 AND receiverId = :userId) OR (type = 0 AND authorId = :userId)")
    LiveData<List<Message>> getAllById(long userId);

    @Insert
    void insert(Message message);

    @Query("DELETE FROM messages")
    void deleteAll();

    @Query("SELECT * FROM messages WHERE (type=1 and receiverId=:user OR type = 0 and authorId=:user) ORDER BY id DESC LIMIT 1")
    LiveData<Message> getLastMessage(long user);
}
