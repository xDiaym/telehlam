package org.juicecode.telehlam.database;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.database.users.UserDao;
import org.juicecode.telehlam.database.users.User;
import org.juicecode.telehlam.database.messages.Message;
import org.juicecode.telehlam.database.messages.MessageDao;
import org.juicecode.telehlam.ui.chat.MessageChatAdapter;
import org.juicecode.telehlam.ui.home.ChatListAdapter;

import java.util.ArrayList;
import java.util.List;

public class DataBaseTask<T> extends AsyncTask<Void, Void, T> {
    private Context context;
    private Task task;
    private User user;
    private AppDataBase appDataBase;
    private UserDao userDao;
    private LifecycleOwner lifecycleOwner;
    private ChatListAdapter chatListAdapter;
    private RecyclerView chatList;
    private MessageDao messageDao;
    private List<User> users = new ArrayList<>();
    private String messageText;
    private MessageChatAdapter messageChatAdapter;
    private Message message;
    private List<Message> messages = new ArrayList<>();
    private long receiver;
    private RecyclerView chat;
    private TextView lastMessageField;

    //добавление сообщения
    public DataBaseTask(Context context, User user, Message message, Task task) {
        this.task = task;
        this.context = context;
        this.user = user;
        this.message = message;
    }

    //получение контактов
    public DataBaseTask(Context context, LifecycleOwner lifecycleOwner, ChatListAdapter chatListAdapter, RecyclerView chatList, Task task) {
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.chatListAdapter = chatListAdapter;
        this.chatList = chatList;
        this.task = task;
    }

    //получение сообщений
    public DataBaseTask(Context context, LifecycleOwner lifecycleOwner, MessageChatAdapter messageChatAdapter, RecyclerView chat, long receiver, Task task) {
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.messageChatAdapter = messageChatAdapter;
        this.chat = chat;
        this.receiver = receiver;
        this.task = task;
    }

    //удаление переписки
    public DataBaseTask(Context context,Task task) {
        this.chatList = chatList;
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.chatListAdapter = chatListAdapter;
        this.receiver = receiver;
        this.task = task;
    }
    //getting last message
    public DataBaseTask(Context context, Task task, long id, TextView textView,LifecycleOwner lifecycleOwner){
        this.context = context;
        this.task = task;
        this.lifecycleOwner = lifecycleOwner;
        this.receiver = id;
        this.lastMessageField = textView;
    }

    @Override
    protected T doInBackground(Void... voids) {
        appDataBase = DBClient.getInstance(context).getAppDataBase();
        userDao = appDataBase.contactDao();
        messageDao = appDataBase.messageDao();
        switch (task) {
            case InsertMessage:
                if (userDao.getNumberOfContactsById(user.getId()) > 0) {
                    messageDao.insert(message);
                } else {
                    userDao.insert(user);
                    messageDao.insert(message);
                }
                break;

            case GetAllContacts:
                //  а ничего он не делает тут кстати, только получение базы и Dao  так что можно убрать
                break;

                case GetLastMessage:
                break;

            case DeleteAllMessageHistory:
                userDao.deleteAll();
                messageDao.deleteAll();
                break;
        }
        return null;
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        switch (task) {
            case GetAllContacts:
                userDao.getAll().observe(lifecycleOwner, new Observer<List<User>>() {
                    @Override
                    public void onChanged(List<User> users) {
                        chatListAdapter = new ChatListAdapter(users,lifecycleOwner);
                        chatList.setAdapter(chatListAdapter);
                    }
                });
                break;

            case GetAllMessages:
                super.onPostExecute(t);
                messageDao.getAllById(receiver).observe(lifecycleOwner, new Observer<List<Message>>() {
                    @Override
                    public void onChanged(List<Message> messages) {
                        messageChatAdapter.addItems(messages);
                        chat.scrollToPosition(messageChatAdapter.getItemCount()-1);
                    }
                });
                break;

            case GetLastMessage:
               messageDao.getLastMessage(receiver).observe(lifecycleOwner, new Observer<Message>() {
                   @Override
                   public void onChanged(Message message) {
                       if(lastMessageField != null){
                           if(message.getAuthorId()==receiver){
                               lastMessageField.setText(message.getText());
                           } else {
                               lastMessageField.setText("You: "+message.getText());
                           }

                       }
                   }
               });
               break;

        }
    }

    public enum Task {
        GetAllContacts, InsertMessage, GetAllMessages, DeleteAllMessageHistory, GetLastMessage
    }
}
