package org.juicecode.hlam.ui.home;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.hlam.R;
import org.juicecode.hlam.core.DBClient;
import org.juicecode.hlam.core.contacts.AppDataBase;
import org.juicecode.hlam.core.contacts.Contact;
import org.juicecode.hlam.core.contacts.ContactDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class HomeFragment extends Fragment {

    private Context context = getContext();;
    private List<Contact> contacts;
    private HomeViewModel homeViewModel;
    private RecyclerView chatList;
    private ChatListAdapter chatListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        chatList = root.findViewById(R.id.chat_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        chatList.setLayoutManager(layoutManager);
        chatList.setHasFixedSize(true);
        getContacts();


        return root;
    }
    private void getContacts(){
        class GetContacts extends AsyncTask<Void,Void,List<Contact>>  {
            List<Contact> contacts = new ArrayList<>();
            AppDataBase appDataBase;
            ContactDao contactDao;
            @Override
            protected List<Contact> doInBackground(Void... voids) {
                appDataBase = DBClient.getInstance(getContext()).getAppDatabase();
                contactDao = appDataBase.contactDao();
                return null;
            }

            @Override
            protected void onPostExecute(List<Contact> contacts) {
                    super.onPostExecute(contacts);
                    contactDao.getAll().observe( getViewLifecycleOwner(), new Observer<List<Contact>>() {
                        @Override
                        public void onChanged(List<Contact> contacts) {


                           
                            chatListAdapter = new ChatListAdapter(context, contacts);
                            chatList.setAdapter(chatListAdapter);
                        }
                    });
            }



        }
        GetContacts getContacts = new GetContacts();


        getContacts.execute();
    }
}