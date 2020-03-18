package org.juicecode.hlam.ui.chat;

import android.view.View;

import androidx.annotation.NonNull;

import org.juicecode.hlam.R;
import org.juicecode.hlam.core.messaging.Message;


public class ReceivedMessageHolder extends MessageHolder {

    public ReceivedMessageHolder(@NonNull View itemView, Message message) {
        super(itemView);

        this.message = itemView.findViewById(R.id.incoming_message_field);
        this.message.setText(message.getMessage());
    }

}
