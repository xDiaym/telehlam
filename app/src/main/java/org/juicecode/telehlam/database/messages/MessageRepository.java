package org.juicecode.telehlam.database.messages;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.juicecode.telehlam.database.DBClient;

import java.util.List;

public class MessageRepository {
    private MessageDao dao;
    private LiveData<List<Message>> messages;

    public MessageRepository(Application application) {
        dao = DBClient.getInstance(application).getAppDataBase().messageDao();
        // messages = dao.getAll();
    }

    public void insert(Message message) {
        new InsertAsyncTask(dao).execute(message);
    }

    public void update(Message message) {
        new UpdateAllAsyncTask(dao).execute(message);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(dao).execute();
    }

    public LiveData<List<Message>> getChatMessages(long receiverId) {
        return dao.getChatMessages(receiverId);
    }

    public LiveData<Message> getChatLastMessage(long receiverId) {
        return dao.getChatLastMessage(receiverId);
    }

    public LiveData<List<Message>> getUnreadMessages(long receiverId) {
        return dao.getUnreadMessages(receiverId);
    }

    public LiveData<Integer> getUnreadMessagesCount(long receiverId) {
        return dao.getUnreadMessagesCount(receiverId);
    }

    public LiveData<List<Message>> getMessages() {
        return messages;
    }

    private static class InsertAsyncTask extends AsyncTask<Message, Void, Void> {
        private MessageDao dao;

        InsertAsyncTask(MessageDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Message... messages) {
            if (messages.length != 0) {
                dao.insert(messages[0]);
            }
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private MessageDao dao;

        DeleteAllAsyncTask(MessageDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAll();
            return null;
        }
    }

    private static class UpdateAllAsyncTask extends AsyncTask<Message, Void, Void> {
        private MessageDao dao;

        UpdateAllAsyncTask(MessageDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Message... messages) {
            if (messages.length != 0) {
                dao.updateMessage(messages[0]);
            }
            return null;
        }

        ;
    }

}
