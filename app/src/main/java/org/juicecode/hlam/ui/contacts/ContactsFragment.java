package org.juicecode.hlam.ui.contacts;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.hlam.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ContactsFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {
    RecyclerView contactsRecycler;
    ArrayList<Contact> contactsArray = new ArrayList<>();

    private static final int READ_CONTACTS = 100;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.contacts_list, container, false);
        contactsArray.add(new Contact("DАун","это же я","AUE"));
        contactsRecycler = view.findViewById(R.id.listOfContacts);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        contactsRecycler.setLayoutManager(layoutManager);
        ContactsAdapter contactsAdapter = new ContactsAdapter(contactsArray);


        contactsRecycler.setAdapter(contactsAdapter);
        return view;
    }

    public void addContacts() {



        try {
            Cursor phones = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Contact contact = new Contact(name, "поебать", phoneNumber);
                Log.e("contact", contact.getName() + " " + contact.getPhone());
                this.contactsArray.add(contact);


            }
            phones.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}

