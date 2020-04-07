package org.juicecode.telehlam.database.messages;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM messages WHERE type=1 and receiverPhone=:user OR type = 0 and authorPhone=:user" )
    LiveData<List<Message>> getAllByPhones(String user);
    @Query("DELETE  FROM messages where authorPhone=:user OR receiverPhone=:user")
    void DeleteAllMessagesWithUser(String user);
    @Insert
    void insert(Message message);
}
