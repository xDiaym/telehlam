package org.juicecode.telehlam.database.messages;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.core.contacts.Contact;
import org.juicecode.telehlam.core.contacts.ContactDao;
import org.juicecode.telehlam.database.AppDataBase;
import org.juicecode.telehlam.database.DBClient;
import org.juicecode.telehlam.ui.chat.MessageChatAdapter;
import org.juicecode.telehlam.ui.home.ChatListAdapter;

import java.util.ArrayList;
import java.util.List;

public class GetMessages extends AsyncTask<Void, Void, List<Message>> {
    Context context;
    LifecycleOwner lifecycleOwner;
    MessageChatAdapter messageChatAdapter;
    RecyclerView chat;
    List<Message> messages = new ArrayList<>();
    AppDataBase appDataBase;
    MessageDao messageDao;
    String author;
    String receiver;

    public GetMessages(Context context, LifecycleOwner lifecycleOwner, MessageChatAdapter messageChatAdapter, RecyclerView chat,String receiver) {
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.messageChatAdapter = messageChatAdapter;
        this.chat = chat;
        this.receiver = receiver;
        this.author = author;
    }

    @Override
    protected List<Message> doInBackground(Void... voids) {
        appDataBase = DBClient.getInstance(context).getAppDataBase();
         messageDao= appDataBase.messageDao();
        return null;
    }

    @Override
    protected void onPostExecute(List<Message> messages) {
        super.onPostExecute(messages);
        messageDao.getAllByPhones(receiver).observe(lifecycleOwner, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                messageChatAdapter.addItems(messages);
            }
        });
    }
}

