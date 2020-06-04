package org.juicecode.telehlam.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.juicecode.telehlam.R;


public class SettingsFragment extends Fragment {
    private RecyclerView settingsView;
    private SettingsAdapter adapter;
    private LinearLayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        layoutManager = new LinearLayoutManager(getContext());
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        settingsView = view.findViewById(R.id.settingsRecycler);
        adapter = new SettingsAdapter();
        settingsView.setAdapter(adapter);
        settingsView.setLayoutManager(layoutManager);
        return view;
    }
}
