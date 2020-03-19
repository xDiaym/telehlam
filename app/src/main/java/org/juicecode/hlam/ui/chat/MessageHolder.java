package org.juicecode.hlam.ui.chat;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class MessageHolder extends RecyclerView.ViewHolder {

    protected TextView message;
    protected int type;

    public MessageHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bind(String text) {
        message.setText(text);
    }

}
