package org.juicecode.telehlam.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.core.contacts.User;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;

import java.util.List;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {
    private List<User> contacts;


    public ChatListAdapter(List<User> contacts) {
        this.contacts = contacts;
    }


    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat_list_item, parent, false);

        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        // TODO(all): set real name
        holder.bind(
                contacts.get(position).getLogin(),
                "Hello world!");
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        private TextView chatName;
        private TextView chatLastMessage;

        ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            ImageView chatAvatar = itemView.findViewById(R.id.contact_avatar);
            chatName = itemView.findViewById(R.id.chat_name);
            chatLastMessage = itemView.findViewById(R.id.chat_last_message);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    FragmentManagerSimplifier simplifier = (FragmentManagerSimplifier) v.getContext();
                    Bundle sendingChatName = new Bundle();
                    String chatNameValue = (String) chatName.getText();
                    sendingChatName.putStringArray("information", new String[]{chatNameValue});
                    simplifier.addWithArguments(R.id.chatFragment, sendingChatName);
                }
            });

        }

        void bind(String login, String lastMessage) {
            chatName.setText(login);
            chatLastMessage.setText(lastMessage);
        }
    }
}