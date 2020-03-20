package org.juicecode.hlam.ui.chat;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.hlam.core.messaging.Message;

public abstract class BaseMessageHolder extends RecyclerView.ViewHolder {
    protected TextView text;

    public BaseMessageHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(Message message);
}
