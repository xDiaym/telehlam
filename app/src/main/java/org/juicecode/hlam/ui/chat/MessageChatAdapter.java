package org.juicecode.hlam.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.hlam.R;
import org.juicecode.hlam.core.messaging.Message;
import org.juicecode.hlam.core.messaging.MessageType;

import java.util.LinkedList;

import static org.juicecode.hlam.core.messaging.MessageType.SEND;


public class MessageChatAdapter extends RecyclerView.Adapter<MessageHolder>{

    // Because this structure has dynamic size
    private LinkedList<Message> messages;

    public MessageChatAdapter(LinkedList<Message> messages) {
        this.messages = messages;
    }

    public MessageChatAdapter() {
        messages = new LinkedList<>();
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        switch (viewType) {
            case SEND:
                view = inflater.inflate(R.layout.incoming_message, parent, false);
                return null;

            case MessageType.RECIVED:
                view = inflater.inflate(R.layout.incoming_message, parent, false);
                return new ReceivedMessageHolder(view, );

            default:
                throw new RuntimeException("Unknown message type");
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        switch (getMessageTypeByPosition(position)) {
            case SEND:
                holder =
        }

    }

    public void addMessage() {

    }

    private MessageType getMessageTypeByPosition(int position) {
        return messages.get(position).getMessageType();
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }
}