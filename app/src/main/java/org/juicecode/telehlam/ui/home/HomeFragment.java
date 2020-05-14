package org.juicecode.telehlam.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.database.messages.MessageViewModel;
import org.juicecode.telehlam.database.users.User;
import org.juicecode.telehlam.database.users.UserViewModel;

import java.util.List;

public class HomeFragment extends Fragment {
    private Context context = getContext();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView chatList = root.findViewById(R.id.chat_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        chatList.setLayoutManager(layoutManager);
        MessageViewModel viewModel = ViewModelProviders.of(this).get(MessageViewModel.class);
        final ChatListAdapter chatListAdapter = new ChatListAdapter(viewModel, getViewLifecycleOwner());
        chatList.setAdapter(chatListAdapter);
        chatList.setHasFixedSize(false);

        userViewModel.getAll().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                chatListAdapter.setUsers(users);
            }
        });

        return root;
    }

}
