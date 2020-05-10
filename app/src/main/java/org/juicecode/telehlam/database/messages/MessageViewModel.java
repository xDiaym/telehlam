package org.juicecode.telehlam.database.messages;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MessageViewModel extends AndroidViewModel {
    private MessageRepository repository;
    private LiveData<List<Message>> messages;

    public MessageViewModel(@NonNull Application application) {
        super(application);
        repository = new MessageRepository(application);
        messages = repository.getMessages();
    }

    public void insert(Message message) {
        repository.insert(message);
    }

    public LiveData<List<Message>> getChatMessages(long receiveerId) {
        return repository.getChatMessages(receiveerId);
    }

    public LiveData<List<Message>> getMessages() {
        return messages;
    }
}
