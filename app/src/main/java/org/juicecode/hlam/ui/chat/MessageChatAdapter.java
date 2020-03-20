package org.juicecode.hlam.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.hlam.R;
import org.juicecode.hlam.core.messaging.IncomingMessage;
import org.juicecode.hlam.core.messaging.Message;

import java.util.LinkedList;
import java.util.List;

public class MessageChatAdapter extends RecyclerView.Adapter<BaseMessageHolder> {
    private List<Message> messages;

    public MessageChatAdapter() {
        messages = new LinkedList<>();
    }

    public void addItem(Message message) {
        messages.add(message);
        notifyDataSetChanged();
    }

    @Override
    public BaseMessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
    public void onBindViewHolder(@NonNull BaseMessageHolder holder, int position) {
        switch (getItemViewType(position)) {
            case Message.MESSAGE_INCOMING:

            case Message.MESSAGE_OUTGOING:
                holder.bind(messages.get(position));
                break;

            default:
                // TODO(all): create custom class for error
                throw new Error("Unknown message type");
        }
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

    static class OutgoingMessageHolder extends BaseMessageHolder {

        public OutgoingMessageHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.outcoming_message_field);
        }

        @Override
        public void bind(Message message) {
            text.setText(message.getText());
        }
    }

    static class IncomingMessageHolder extends BaseMessageHolder {

        public IncomingMessageHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.incoming_message_field);
        }

        @Override
        public void bind(Message message) {
            text.setText(message.getText());
        }
    }
}