package org.juicecode.telehlam.database.messages;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM messages " +
            "WHERE (type = 1 AND receiverId = :userId) " +  // Outgoing and user is receiver
            "OR (type = 0 AND authorId = :userId)"  // Incoming and user is author
    )
    LiveData<List<Message>> getChatMessages(long userId);

    @Insert
    void insert(Message message);

    @Query("DELETE FROM messages")
    void deleteAll();

    @Query("SELECT  * FROM messages WHERE (type=1 and receiverId=:user OR type = 0 and authorId=:user) ORDER BY timestamp DESC LIMIT 1")
    LiveData<Message> getChatLastMessage(long user);

    @Update
    void updateMessage(Message message);

    @Query("SELECT * FROM messages WHERE (type = 0 and authorId = :userId) AND isRead = 0")
    LiveData<List<Message>> getUnreadMessages(long userId);

    @Query("SELECT COUNT(*) FROM messages WHERE (type = 0 and authorId = :userId) AND isRead = 0")
    LiveData<Integer> getUnreadMessagesCount(long userId);
}
