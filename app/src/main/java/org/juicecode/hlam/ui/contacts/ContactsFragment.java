package org.juicecode.hlam.ui.contacts;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.hlam.R;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {
    RecyclerView contactsRecycler;
    List<Contact> contacts = new ArrayList<>();
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        View view  = layoutInflater.inflate(R.layout.contacts_list, container,false);
        contactsRecycler = view.findViewById(R.id.listOfContacts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        contactsRecycler.setLayoutManager(layoutManager);
        ContactsAdapter contactsAdapter = new ContactsAdapter();
        contacts.addAll(addContacts());
        contactsAdapter.setItem(contacts);
        contactsRecycler.setAdapter(contactsAdapter);
        return view;
    }
    public ArrayList<Contact> addContacts(){
        ArrayList<Contact> contacts = new ArrayList<>();

        try {
            Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Contact contact = new Contact(name,"поебать",phoneNumber);
                Log.e("contact",contact.getName()+" "+contact.getPhone());
                contacts.add(contact);


            }
            phones.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return contacts;
    }
}
