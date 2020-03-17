package org.juicecode.hlam.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.hlam.MainActivity;
import org.juicecode.hlam.R;
import org.juicecode.hlam.ui.chat.ChatFragment;

import java.util.Locale;
import java.util.Random;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {

    private static int chatItemCount = 0;
    private Context parent;

    ChatListAdapter(Context context, int chatCount) {
        parent = context;
        chatItemCount = chatCount;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat_list_item, parent, false);

        ChatViewHolder viewHolder = new ChatViewHolder(view);
        viewHolder.chatName.setText("WTF");
        // chatItemCount++;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        // TODO(all): set real name
        holder.bind("John Doe", "Hello world!");
    }

    @Override
    public int getItemCount() {
        return chatItemCount;
    }

    class ChatViewHolder extends RecyclerView.ViewHolder {

        private ImageView chatAvatar;
        private TextView chatName;
        private TextView chatLastMessage;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            chatAvatar = itemView.findViewById(R.id.chat_avatar);
            chatName = itemView.findViewById(R.id.chat_name);
            chatLastMessage = itemView.findViewById(R.id.chat_last_message);

            // TODO(all):make new class for listener
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    MainActivity mainActivity = (MainActivity) v.getContext();
                    FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    ChatFragment chatFragment = new ChatFragment();
                    Bundle sendingChatName = new Bundle();
                    String chatNameValue = (String) chatName.getText();
                    sendingChatName.putString("chatName", chatNameValue);
                    chatFragment.setArguments(sendingChatName);
                    fragmentTransaction.replace(R.id.drawer_layout, chatFragment, "tag").addToBackStack(null).commit();

                    Toast.makeText(parent, chatName.getText(), Toast.LENGTH_LONG).show();
                }
            });
        }

        public void bind(String name, String lastMessage) {
            chatName.setText(name);
            chatLastMessage.setText(lastMessage);
        }
    }
}
