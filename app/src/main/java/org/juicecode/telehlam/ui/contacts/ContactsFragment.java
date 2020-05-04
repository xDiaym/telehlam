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
import org.juicecode.telehlam.utils.DrawerLocker;

import java.util.ArrayList;

public class ContactsFragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback {
    private RecyclerView contactsRecycler;
    private ImageButton goBackButton;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {

        View view = layoutInflater.inflate(R.layout.contacts_list, container, false);
        final DrawerLocker drawerLocker = (DrawerLocker) view.getContext();
        drawerLocker.setDrawerLock(true);
        contactsRecycler = view.findViewById(R.id.listOfContacts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        contactsRecycler.setLayoutManager(layoutManager);
        ContactsAdapter contactsAdapter = new ContactsAdapter();
        contactsRecycler.setAdapter(contactsAdapter);
        goBackButton = view.findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
                drawerLocker.setDrawerLock(false);
            }
        });
        return view;
    }



}