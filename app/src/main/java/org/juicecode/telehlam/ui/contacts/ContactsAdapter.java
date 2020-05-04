package org.juicecode.telehlam.ui.contacts;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.core.contacts.Contact;
import org.juicecode.telehlam.rest.RetrofitBuilder;
import org.juicecode.telehlam.rest.user.AsyncUserApi;
import org.juicecode.telehlam.rest.user.User;
import org.juicecode.telehlam.ui.chat.ChatFragment;
import org.juicecode.telehlam.utils.ApiCallback;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>
    implements Filterable {
    private ArrayList<Contact> contacts;
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final List<User> users = new ArrayList<>();
            if (constraint != null && constraint.length() != 0) {
                AsyncUserApi api = new AsyncUserApi(new RetrofitBuilder());
                api.byLogin(constraint.toString(), new ApiCallback<List<User>>() {
                    @Override
                    public void execute(List<User> response) {
                        users.addAll(response);
                    }
                });
            }
            FilterResults results = new FilterResults();
            results.values = users;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contacts.clear();
            contacts.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    ContactsAdapter() {
        this.contacts = new ArrayList<>();
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.contact_list_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.bind(contact.getName(), contact.getPhone());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView contactName;
        String phoneNumber;

        ContactViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManagerSimplifier simplifier = (FragmentManagerSimplifier) v.getContext();
                    ChatFragment chatFragment = new ChatFragment();
                    //sending contact info to ChatFragment
                    Bundle sendingChatName = new Bundle();
                    String chatNameValue = (String) contactName.getText();
                    sendingChatName.putStringArray("information", new String[]{chatNameValue, phoneNumber});
                    chatFragment.setArguments(sendingChatName);

                    simplifier.replaceFragment(chatFragment, "chatFragment");
                }
            });
            contactName = itemView.findViewById(R.id.contact_name);
        }

        void bind(String name, String phone) {
            contactName.setText(name);
            phoneNumber = phone;
        }
    }

}
