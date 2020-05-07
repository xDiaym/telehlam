package org.juicecode.telehlam.ui;

import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.database.DataBaseTask;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;
import org.juicecode.telehlam.utils.SharedPreferencesRepository;

public class logoutFragment extends Fragment {

    Button logout;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logout_fragment, container, false);
        logout = view.findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        return view;
    }

   public void logout(){
       SharedPreferencesRepository repository = new SharedPreferencesRepository(getContext());
       repository.deleteInfo();
       DataBaseTask<Void> dataBaseTask = new DataBaseTask(getContext(), DataBaseTask.Task.DeleteAllMessageHistory);
       dataBaseTask.execute();
       FragmentManagerSimplifier fragmentManagerSimplifier = (FragmentManagerSimplifier)getActivity();
       fragmentManagerSimplifier.addFragment(R.id.authorisationFragment);
   }

}
