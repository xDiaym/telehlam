package org.juicecode.telehlam.ui.registration;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.juicecode.telehlam.MainActivity;
import org.juicecode.telehlam.R;
import org.juicecode.telehlam.utils.DrawerLocker;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;


public class AuthorisationFragment extends Fragment {
    Button goToRegistrationFragment;
    ExtendedFloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.authorisation_fragment, container, false);
        floatingActionButton = view.findViewById(R.id.login_authorisation);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) v.getContext();
                activity.remove("authorisation");
                activity.setDrawerLock(true);
            }
        });

        goToRegistrationFragment = view.findViewById(R.id.registration_button);
        final MainActivity activity = (MainActivity) view.getContext();
        activity.setDrawerLock(true);

        goToRegistrationFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.setDrawerLock(false);
                activity.addFragment(new RegistrationFragment(), "registration");
            }
        });
        return view;
    }
}