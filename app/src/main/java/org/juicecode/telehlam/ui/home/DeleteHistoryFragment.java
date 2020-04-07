package org.juicecode.telehlam.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.database.DataBaseTask;

public class DeleteHistoryFragment extends Fragment {
    Button yes;
    Button no;
    DataBaseTask dataBaseTask;

    public DeleteHistoryFragment(DataBaseTask dataBaseTask) {
        this.dataBaseTask = dataBaseTask;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.delete_all_messages_with_user, container, false);
        yes = view.findViewById(R.id.yes_btn);
        no = view.findViewById(R.id.no_btn);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBaseTask.execute();
            }
        });
        return view;
    }
}
