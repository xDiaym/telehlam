package org.juicecode.hlam.ui.chat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.hlam.R;
import org.juicecode.hlam.core.messaging.IncomingMessage;
import org.juicecode.hlam.core.messaging.Message;

import java.text.Format;
import java.util.LinkedList;
import java.util.List;

public class MessageChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Message> messages;

    public MessageChatAdapter() {
        messages = new LinkedList<>();
    }

    public void addItem(Message message) {
        messages.add(message);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view;
        switch (viewType) {
            case Message.MESSAGE_INCOMING:
                view = inflater.inflate(R.layout.incoming_message, parent, false);
                return new IncomingMessageHolder(view);

            case Message.MESSAGE_OUTGOING:
                view = inflater.inflate(R.layout.outcoming_message, parent, false);
                return new OutgoingMessageHolder(view);

            default:
                throw new Error("Unknown message type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position) instanceof IncomingMessage
                ? Message.MESSAGE_INCOMING
                : Message.MESSAGE_OUTGOING;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class OutgoingMessageHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public OutgoingMessageHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.outcoming_message_field);
            String messageText = messages.get(getItemCount() - 1).getText();
            text.setText(messageText);
        }
    }

    class IncomingMessageHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public IncomingMessageHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.incoming_message_field);
            String messageText = messages.get(getItemCount() - 1).getText();
            text.setText(messageText);
        }
    }
}