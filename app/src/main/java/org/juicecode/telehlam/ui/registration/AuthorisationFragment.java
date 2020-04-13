package org.juicecode.telehlam.ui.registration;


import android.graphics.ImageDecoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.juicecode.telehlam.MainActivity;
import org.juicecode.telehlam.R;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;


public class AuthorisationFragment extends Fragment {
    Button goToRegistrationFragment;
    ExtendedFloatingActionButton floatingActionButton;
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.authorisation_fragment, container, false);
        floatingActionButton = view.findViewById(R.id.login_authorisation);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManagerSimplifier fragmentManagerSimplifier = (FragmentManagerSimplifier)v.getContext();
                fragmentManagerSimplifier.remove("authorisation");
                fragmentManagerSimplifier.unlockDrawer();
            }
        });
        goToRegistrationFragment = view.findViewById(R.id.registration_button);
       final FragmentManagerSimplifier fragmentManagerSimplifier = (FragmentManagerSimplifier)view.getContext();
        fragmentManagerSimplifier.lockDrawer();
        goToRegistrationFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManagerSimplifier.unlockDrawer();
                fragmentManagerSimplifier.addFragment(new RegistrationFragment(),"registration");
            }
        });
        return view;
    }
}