package org.juicecode.telehlam.ui.contacts;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.core.contacts.User;
import org.juicecode.telehlam.rest.RetrofitBuilder;
import org.juicecode.telehlam.rest.user.UserRepository;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>
        implements Filterable {
    private LifecycleOwner lifecycleOwner;
    private ArrayList<User> contacts;

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            LiveData<List<User>> users = null;
            if (constraint != null && constraint.length() != 0) {
                UserRepository api = new UserRepository(new RetrofitBuilder());
                users = api.byLogin(constraint.toString());
            }
            FilterResults results = new FilterResults();
            results.values = users;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results == null || results.values == null) {
                contacts.clear();
                notifyDataSetChanged();
            } else {
                ((LiveData<List<User>>) results.values).observe(lifecycleOwner, new Observer<List<User>>() {
                    @Override
                    public void onChanged(List<User> users) {
                        contacts.clear();
                        contacts.addAll(users);
                        notifyDataSetChanged();
                        for(User u:contacts){
                            Log.i("c", Long.toString(u.getId()));
                            Log.i("logins", u.getLogin());
                        }
                    }
                });
            }
        }
    };

    ContactsAdapter(LifecycleOwner owner) {
        this.lifecycleOwner = owner;
        this.contacts = new ArrayList<User>();
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
        User contact = contacts.get(position);
        holder.bind(contact.getLogin(),position);
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView contactName;
        private int pos;
        ContactViewHolder(@NonNull final View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManagerSimplifier simplifier = (FragmentManagerSimplifier) v.getContext();
                    //sending contact info to ChatFragment
                    Bundle sendingChatName = new Bundle();
                    String chatNameValue = (String) contactName.getText();
                    sendingChatName.putSerializable("user", contacts.get(pos));
                    simplifier.addWithArguments(R.id.chatFragment, sendingChatName);
                }
            });
            contactName = itemView.findViewById(R.id.contact_name);
        }

        void bind(String login, int pos) {
            contactName.setText(login);
            this.pos = pos;
        }
    }

}
