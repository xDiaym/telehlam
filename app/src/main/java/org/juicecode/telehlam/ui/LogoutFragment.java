package org.juicecode.telehlam.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.database.messages.MessageViewModel;
import org.juicecode.telehlam.database.users.UserViewModel;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;
import org.juicecode.telehlam.utils.SharedPreferencesRepository;

public class LogoutFragment extends Fragment {
    private MessageViewModel messageViewModel;
    private UserViewModel userViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logout_fragment, container, false);

        messageViewModel = ViewModelProviders.of(this).get(MessageViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        Button logout = view.findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return view;
    }

    private void logout() {
        SharedPreferencesRepository repository = new SharedPreferencesRepository(getContext());
        repository.deleteInfo();

        messageViewModel.deleteAll();
        userViewModel.deleteAll();

        FragmentManagerSimplifier fragmentManagerSimplifier = (FragmentManagerSimplifier) getActivity();
        fragmentManagerSimplifier.addFragment(R.id.authorisationFragment);
    }

}
