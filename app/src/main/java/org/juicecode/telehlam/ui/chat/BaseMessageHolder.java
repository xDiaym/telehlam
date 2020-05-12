package org.juicecode.telehlam.ui.chat;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.database.messages.Message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class BaseMessageHolder extends RecyclerView.ViewHolder {
    protected TextView text;

    public BaseMessageHolder(@NonNull View itemView) {
        super(itemView);
    }

    protected static String formatDate(long unixTime) {
        Date date = new Date(unixTime);
        return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(date);
    }

    public abstract void bind(Message message);
}
