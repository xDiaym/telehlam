package org.juicecode.hlam.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.hlam.R;
import org.juicecode.hlam.core.messaging.IncomingMessage;
import org.juicecode.hlam.core.messaging.Message;
import org.juicecode.hlam.core.messaging.OutgoingMessage;

import java.util.LinkedList;
import java.util.List;

public class MessageChatAdapter extends RecyclerView.Adapter<MessageChatAdapter.ChatViewHolder> {
    private List<Message> messages;

    public MessageChatAdapter() {
        messages = new LinkedList<>();
    }

    @NonNull
    public void setItem(Message message) {
        messages.add(message);
        notifyDataSetChanged();
    }

    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.outcoming_message, parent, false);
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
        private TextView outcomingMessage;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            outcomingMessage = itemView.findViewById(R.id.outcoming_message_field);
            String messageText = messages.get(getItemCount() - 1).getText();
            outcomingMessage.setText(messageText);
        }
    }
}