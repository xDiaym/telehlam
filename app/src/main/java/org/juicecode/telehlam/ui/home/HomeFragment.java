package org.juicecode.telehlam.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.core.contacts.Contact;
import org.juicecode.telehlam.database.DataBaseTask;

import java.util.List;

public class HomeFragment extends Fragment {

    private Context context = getContext();
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
        DataBaseTask<List<Contact>> dataBaseTask = new DataBaseTask<>(getContext(), getViewLifecycleOwner(), chatListAdapter, chatList, DataBaseTask.Task.GetAllContacts);
        dataBaseTask.execute();
        return root;
    }


}