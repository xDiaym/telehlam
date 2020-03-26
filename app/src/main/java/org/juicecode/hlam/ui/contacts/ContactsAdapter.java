package org.juicecode.hlam.ui.contacts;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.hlam.MainActivity;
import org.juicecode.hlam.R;
import org.juicecode.hlam.core.contacts.Contact;
import org.juicecode.hlam.ui.chat.ChatFragment;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {
    private List<Contact> contacts;

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.contact_list_item, parent, false);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        return contactViewHolder;
    }

    public ContactsAdapter(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        holder.bind(contacts.get(position).getName(), contacts.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView contactName;
        String phoneNumber;

        public ContactViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity mainActivity = (MainActivity) v.getContext();
                    ChatFragment chatFragment = new ChatFragment();
                    Bundle sendingChatName = new Bundle();
                    String chatNameValue = (String) contactName.getText();
                    sendingChatName.putStringArray("information", new String[]{chatNameValue, phoneNumber});
                    chatFragment.setArguments(sendingChatName);
                    mainActivity.showFragment(chatFragment);
                }
            });
            contactName = itemView.findViewById(R.id.contact_name);
        }

        public void bind(String name,String phone) {
            contactName.setText(name);
            this.phoneNumber = phone;
        }
    }

}
