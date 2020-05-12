package org.juicecode.telehlam.database.messages;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM messages " +
            "WHERE (type = 1 AND receiverId = :userId) " +  // Outgoing and user is receiver
            "OR (type = 0 AND authorId = :userId)")
        // Incoming and user is author
    LiveData<List<Message>> getChatMessages(long userId);

    @Insert
    void insert(Message message);

    @Query("DELETE FROM messages")
    void deleteAll();

    @Query("SELECT * FROM messages WHERE (type=1 and receiverId=:user OR type = 0 and authorId=:user) ORDER BY timestamp DESC LIMIT 1")
    LiveData<Message> getChatLastMessage(long user);
}
