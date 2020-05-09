package org.juicecode.telehlam.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.core.contacts.User;
import org.juicecode.telehlam.database.DataBaseTask;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;

import java.util.List;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {
    private List<User> contacts;
    private Context context;
    private LifecycleOwner lifecycleOwner;
    public ChatListAdapter(List<User> contacts, LifecycleOwner lifecycleOwner) {
        this.contacts = contacts;
        this.lifecycleOwner = lifecycleOwner;
    }


    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.chat_list_item, parent, false);
        context = view.getContext();
        for(User u:contacts){
            Log.i("usersId", Long.toString(u.getId()));
        }
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        // TODO(all): set real name
        holder.bind(
                contacts.get(position).getLogin(),
                position,contacts.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

     class ChatViewHolder extends RecyclerView.ViewHolder {
        private TextView chatName;
        private TextView chatLastMessage;
        private int pos;
        private long id;
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
                    sendingChatName.putSerializable("user", contacts.get(pos));
                    simplifier.addWithArguments(R.id.chatFragment, sendingChatName);
                }
            });

        }

        void bind(String login, int pos,long id) {
            this.pos = pos;
            this.id = id;
            chatName.setText(login);
            //DataBaseTask<Message> dataBaseTask = new DataBaseTask(context, DataBaseTask.Task.GetLastMessage,id,chatLastMessage,lifecycleOwner);
            //dataBaseTask.execute();
        }
    }
}