package org.juicecode.telehlam.database;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.core.contacts.Contact;
import org.juicecode.telehlam.core.contacts.ContactDao;
import org.juicecode.telehlam.database.messages.Message;
import org.juicecode.telehlam.database.messages.MessageDao;
import org.juicecode.telehlam.ui.chat.MessageChatAdapter;
import org.juicecode.telehlam.ui.home.ChatListAdapter;

import java.util.ArrayList;
import java.util.List;

public class DataBaseTask<T> extends AsyncTask<Void, Void, T> {
    private Context context;
    private Task task;
    private Contact contact;
    private AppDataBase appDataBase;
    private ContactDao contactDao;
    private LifecycleOwner lifecycleOwner;
    private ChatListAdapter chatListAdapter;
    private RecyclerView chatList;
    private MessageDao messageDao;
    private List<Contact> contacts = new ArrayList<>();
    private MessageChatAdapter messageChatAdapter;
    private Message message;
    private List<Message> messages = new ArrayList<>();
    private long receiver;
    private RecyclerView chat;
    private TextView lastMessageField;

    //inserting messages
    public DataBaseTask(Context context, Contact contact, Message message, Task task) {
        this.task = task;
        this.context = context;
        this.contact = contact;
        this.message = message;
    }

    //getting contacts
    public DataBaseTask(Context context, LifecycleOwner lifecycleOwner, ChatListAdapter chatListAdapter, RecyclerView chatList, Task task) {
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.chatListAdapter = chatListAdapter;
        this.chatList = chatList;
        this.task = task;
    }

    //getting messages
    public DataBaseTask(Context context, LifecycleOwner lifecycleOwner, MessageChatAdapter messageChatAdapter, RecyclerView chat, long receiver, Task task) {
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.messageChatAdapter = messageChatAdapter;
        this.chat = chat;
        this.receiver = receiver;
        this.task = task;
    }

    //deleting history
    public DataBaseTask(Task task,Context context){
        this.context = context;
        this.task = task;
    }

    @Override
    protected T doInBackground(Void... voids) {
        appDataBase = DBClient.getInstance(context).getAppDataBase();
        contactDao = appDataBase.contactDao();
        messageDao = appDataBase.messageDao();
        switch (task) {
            case InsertMessage:
                if (contactDao.getNumberOfContactsBylogin(contact.getLogin()) > 0) {
                    messageDao.insert(message);
                } else {
                    contactDao.insert(contact);
                    messageDao.insert(message);
                }
                break;

            case GetAllContacts:
                //  а ничего он не делает тут кстати, только получение базы и Dao  так что можно убрать
                break;

            case DeleteAllMessageHistory:
                contactDao.deleteAll();
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
                contactDao.getAll().observe(lifecycleOwner, new Observer<List<Contact>>() {
                    @Override
                    public void onChanged(List<Contact> contacts) {
                        chatListAdapter = new ChatListAdapter(context, contacts);
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
        }
    }

    public enum Task {
        GetAllContacts, InsertMessage, GetAllMessages, DeleteAllMessageHistory
    }
}
