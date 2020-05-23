package org.juicecode.telehlam.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.database.messages.Message;
import org.juicecode.telehlam.database.messages.MessageViewModel;
import org.juicecode.telehlam.database.users.User;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {
    private List<User> users = new ArrayList<>();
    private LifecycleOwner lifecycleOwner;
    private MessageViewModel messageViewModel;

    public ChatListAdapter(MessageViewModel viewModel, LifecycleOwner owner) {
        messageViewModel = viewModel;
        lifecycleOwner = owner;

    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat_list_item, parent, false);

        return new ChatViewHolder(view, messageViewModel, lifecycleOwner);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    public void setUsers(List<User> newUsers) {
        users.clear();
        users.addAll(newUsers);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        private ImageView chatAvatar;
        private TextView chatName;
        private TextView chatLastMessage;
        private TextView numberOfUnreadMessages;
        private LifecycleOwner lifecycleOwner;
        private MessageViewModel messageViewModel;
        private User user;

        ChatViewHolder(@NonNull View itemView, MessageViewModel model, LifecycleOwner owner) {
            super(itemView);
            numberOfUnreadMessages = itemView.findViewById(R.id.numberOfUnReadMessages);
            lifecycleOwner = owner;
            messageViewModel = model;
            chatAvatar = itemView.findViewById(R.id.contact_avatar);
            chatName = itemView.findViewById(R.id.chat_name);
            chatLastMessage = itemView.findViewById(R.id.chat_last_message);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    FragmentManagerSimplifier simplifier = (FragmentManagerSimplifier) v.getContext();
                    Bundle sendingChatName = new Bundle();
                    sendingChatName.putSerializable("user", user);
                    simplifier.addWithArguments(R.id.chatFragment, sendingChatName);
                }
            });
        }

        void bind(final User user) {
            this.user = user;
            chatName.setText(String.format("%s %s", user.getName(), user.getSurname()));
            messageViewModel.getChatLastMessage(user.getId()).observe(lifecycleOwner, new Observer<Message>() {
                @Override
                public void onChanged(Message message) {
                    if (message != null) {
                        if (message.getAuthorId() == user.getId()) {
                            chatLastMessage.setText(message.getText());
                        } else {
                            // FIXME: get string from resources
                            chatLastMessage.setText(String.format("%s: %s", "You", message.getText()));
                        }

                    }

                }
            });

            messageViewModel.getUnreadMessagesCount(user.getId()).observe(lifecycleOwner, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if (integer != 0) {
                        numberOfUnreadMessages.setVisibility(View.VISIBLE);
                        numberOfUnreadMessages.setText(String.format(Locale.getDefault(), "%d", integer));
                    } else {
                        numberOfUnreadMessages.setVisibility(View.GONE);
                    }
                }
            });
        }
    }
}