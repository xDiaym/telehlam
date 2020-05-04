package org.juicecode.telehlam.ui.contacts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.utils.DrawerLocker;

public class ContactsFragment extends Fragment
        implements ActivityCompat.OnRequestPermissionsResultCallback {

    ContactsAdapter adapter;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {

        View view = layoutInflater.inflate(R.layout.contacts_list, container, false);
        setHasOptionsMenu(true);

        final DrawerLocker drawerLocker = (DrawerLocker) view.getContext();
        drawerLocker.setDrawerLock(true);

        RecyclerView contactsRecycler = view.findViewById(R.id.listOfContacts);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        contactsRecycler.setLayoutManager(layoutManager);
        adapter = new ContactsAdapter();
        contactsRecycler.setAdapter(adapter);

        ImageButton goBackButton = view.findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
                drawerLocker.setDrawerLock(false);
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        Log.e("TAG", "HER");
        inflater.inflate(R.menu.serach_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView view = (SearchView) item.getActionView();

        view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }
}