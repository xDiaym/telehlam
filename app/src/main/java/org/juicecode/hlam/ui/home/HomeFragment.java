package org.juicecode.hlam.ui.home;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.hlam.R;
import org.juicecode.hlam.core.DBClient;
import org.juicecode.hlam.core.contacts.AppDataBase;
import org.juicecode.hlam.core.contacts.Contact;
import org.juicecode.hlam.core.contacts.ContactDao;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    private ArrayList<Contact> contacts;
    private HomeViewModel homeViewModel;
    private RecyclerView chatList;
    private ChatListAdapter chatListAdapter;
    Context context = getContext();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        chatList = root.findViewById(R.id.chat_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        chatList.setLayoutManager(layoutManager);
        chatList.setHasFixedSize(true);



        return root;
    }
    private void getContacts(){
        class GetContacts extends AsyncTask<Void,Void,ArrayList<Contact>>{
            @Override
            protected ArrayList<Contact> doInBackground(Void... voids) {
                AppDataBase appDataBase = DBClient.getInstance(getContext()).getAppDatabase();
                ContactDao contactDao = appDataBase.contactDao();
                ArrayList<Contact> contacts = new ArrayList<>();
                contacts = contactDao.getAll();
                return contacts;
            }

            @Override
            protected void onPostExecute(ArrayList<Contact> contacts) {
                super.onPostExecute(contacts);
                chatListAdapter = new ChatListAdapter(context, contacts);
                chatList.setAdapter(chatListAdapter);
            }
        }
        GetContacts getContacts = new GetContacts();
        getContacts.execute();
    }
}