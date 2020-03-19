package org.juicecode.hlam.ui.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.hlam.R;
import org.juicecode.hlam.core.messaging.IncomingMessage;
import org.juicecode.hlam.core.messaging.OutgoingMessage;

import java.util.ArrayList;
import java.util.List;

public class MessageChatAdapter extends RecyclerView.Adapter<MessageChatAdapter.ChatViewHolder> {
    List<IncomingMessage> incomingMessages = new ArrayList<>();
    List<OutgoingMessage> outcomingMessages = new ArrayList<>();

    @NonNull

    public void setItem(IncomingMessage incomingMessage, OutgoingMessage outcomingMessage) {

        this.incomingMessages.add(incomingMessage);
        this.outcomingMessages.add(outcomingMessage);
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
        return incomingMessages.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        private TextView outcomingMessage;
        private TextView incomingMessage;


        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);

            outcomingMessage = itemView.findViewById(R.id.outcoming_message_field);
            String OutcomingMessageValue = outcomingMessages.get(getItemCount() - 1).getMessage();
            outcomingMessage.setText(OutcomingMessageValue);

            if (OutcomingMessageValue.isEmpty()) {
                outcomingMessage.setVisibility(View.INVISIBLE);
            }
        }
    }
}