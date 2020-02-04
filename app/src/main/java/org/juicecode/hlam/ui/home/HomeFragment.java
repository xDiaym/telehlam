package org.juicecode.hlam.ui.home;

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

import org.juicecode.hlam.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView chatList;
    private ChatListAdapter chatListAdapter;

    // TODO(all): delete temp var
    private static final int EXAMPLE_USER_COUNT = 42;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Context context = getContext();

        chatList = root.findViewById(R.id.chat_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        chatList.setLayoutManager(layoutManager);
        chatList.setHasFixedSize(true);

        chatListAdapter = new ChatListAdapter(context, EXAMPLE_USER_COUNT);
        chatList.setAdapter(chatListAdapter);

        return root;
    }
}