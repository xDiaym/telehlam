package org.juicecode.telehlam.ui.contacts;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.MainActivity;
import org.juicecode.telehlam.R;
import org.juicecode.telehlam.core.contacts.Contact;
import org.juicecode.telehlam.ui.chat.ChatFragment;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {
    private ArrayList<Contact> contacts;

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
                    FragmentManagerSimplifier simplifier = (FragmentManagerSimplifier) v.getContext();
                    ChatFragment chatFragment = new ChatFragment();
                    Bundle sendingChatName = new Bundle();
                    String chatNameValue = (String) contactName.getText();
                    sendingChatName.putStringArray("information", new String[]{chatNameValue, phoneNumber});
                    chatFragment.setArguments(sendingChatName);

                    simplifier.replaceFragment(chatFragment);
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
