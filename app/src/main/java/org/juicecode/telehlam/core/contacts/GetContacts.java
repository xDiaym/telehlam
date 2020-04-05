package org.juicecode.telehlam.core.contacts;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.database.AppDataBase;
import org.juicecode.telehlam.database.DBClient;
import org.juicecode.telehlam.ui.home.ChatListAdapter;

import java.util.ArrayList;
import java.util.List;

public class GetContacts extends AsyncTask<Void, Void, List<Contact>> {
    Context context;
    LifecycleOwner lifecycleOwner;
    ChatListAdapter chatListAdapter;
    RecyclerView chatList;
    List<Contact> contacts = new ArrayList<>();
    AppDataBase appDataBase;
    ContactDao contactDao;

    public GetContacts(Context context, LifecycleOwner lifecycleOwner, ChatListAdapter chatListAdapter, RecyclerView chatList) {
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.chatListAdapter = chatListAdapter;
        this.chatList = chatList;
    }


    @Override
    protected List<Contact> doInBackground(Void... voids) {
        appDataBase = DBClient.getInstance(context).getAppDataBase();
        contactDao = appDataBase.contactDao();
        return null;
    }

    @Override
    protected void onPostExecute(List<Contact> contacts) {
        super.onPostExecute(contacts);
        contactDao.getAll().observe(lifecycleOwner, new Observer<List<Contact>>() {
            @Override
            public void onChanged(List<Contact> contacts) {


                chatListAdapter = new ChatListAdapter(context, contacts);
                chatList.setAdapter(chatListAdapter);
            }
        });
    }
}