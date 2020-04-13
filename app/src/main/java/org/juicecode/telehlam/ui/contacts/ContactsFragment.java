package org.juicecode.telehlam.ui.contacts;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.core.contacts.Contact;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;


import java.util.ArrayList;

public class ContactsFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {
    private RecyclerView contactsRecycler;
    private ImageButton goBackButton;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {

        View view = layoutInflater.inflate(R.layout.contacts_list, container, false);
        final FragmentManagerSimplifier fragmentManagerSimplifier = (FragmentManagerSimplifier) view.getContext();
        fragmentManagerSimplifier.lockDrawer();
        contactsRecycler = view.findViewById(R.id.listOfContacts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        contactsRecycler.setLayoutManager(layoutManager);
        ContactsAdapter contactsAdapter = new ContactsAdapter(getContacts());
        contactsRecycler.setAdapter(contactsAdapter);
        goBackButton = view.findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
                fragmentManagerSimplifier.unlockDrawer();
            }
        });
        return view;
    }

    public ArrayList<Contact> getContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();

        Activity activity = getActivity();
        if (activity == null) {
            return contacts;
        }

        Cursor phones = activity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        if (phones == null) {
            // TODO(all): return back to main_fragment
            Toast.makeText(getContext(), "Access denied", Toast.LENGTH_LONG).show();
            return contacts;
        }

        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contacts.add(new Contact(name, phoneNumber));
        }
        phones.close();

        return contacts;
    }

}