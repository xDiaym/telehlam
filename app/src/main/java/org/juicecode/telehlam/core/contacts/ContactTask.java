package org.juicecode.telehlam.core.contacts;

import android.content.Context;
import android.os.AsyncTask;
import android.service.voice.VoiceInteractionService;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.database.AppDataBase;
import org.juicecode.telehlam.database.DBClient;
import org.juicecode.telehlam.ui.home.ChatListAdapter;

import java.util.ArrayList;
import java.util.List;

import static org.juicecode.telehlam.core.contacts.ContactTask.Task.Insert;

public class ContactTask<T> extends AsyncTask<Void, Void, T> {
    private Context context;
    private Task task;
    private Contact contact;
    private AppDataBase appDataBase;
    private ContactDao contactDao;
    private LifecycleOwner lifecycleOwner;
    private ChatListAdapter chatListAdapter;
    private RecyclerView chatList;
    private List<Contact> contacts = new ArrayList<>();

    public enum Task {
        Insert, GetAll
    }

    public ContactTask(Context context, Contact contact, Task task) {
        this.task = task;
        this.context = context;
        this.contact = contact;
    }

    ;


    public ContactTask(Context context, LifecycleOwner lifecycleOwner, ChatListAdapter chatListAdapter, RecyclerView chatList, Task task) {
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
        this.chatListAdapter = chatListAdapter;
        this.chatList = chatList;
        this.task = task;
    }

    @Override
    protected T doInBackground(Void... voids) {
        appDataBase = DBClient.getInstance(context).getAppDataBase();
        contactDao = appDataBase.contactDao();
        switch (task) {
            case Insert:
                if (contactDao.getNumberOfContactsByPhone(contact.getPhone()) > 0) {
                } else {
                    contactDao.insert(contact);
                }
                break;

            case GetAll:
                //  а ничего он не делает тут кстати, только получение базы и Dao  так что можно убрать
                break;
        }
        return null;
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        switch (task){
            case GetAll:
                contactDao.getAll().observe(lifecycleOwner, new Observer<List<Contact>>() {
                    @Override
                    public void onChanged(List<Contact> contacts) {
                        chatListAdapter = new ChatListAdapter(context, contacts);
                        chatList.setAdapter(chatListAdapter);
                    }
                });

                break;
        }
    }
}
