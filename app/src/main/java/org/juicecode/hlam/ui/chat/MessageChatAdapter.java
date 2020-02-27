package org.juicecode.hlam.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.hlam.R;
import org.juicecode.hlam.ui.home.ChatListAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MessageChatAdapter extends RecyclerView.Adapter<MessageChatAdapter.ChatViewHolder>{
    List<Message> messages = new ArrayList<>();

    @NonNull

    public void setItem(Message message){

        this.messages.add(message);
        notifyDataSetChanged();
    }
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.incoming_message,parent,false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        private TextView message;


        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.message);
            String messageValue = messages.get(getItemCount()-1).getMessageString();
            message.setText(messageValue);
        }
    }
}
