package org.juicecode.telehlam.ui.registration;


import android.graphics.ImageDecoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import org.juicecode.telehlam.MainActivity;
import org.juicecode.telehlam.R;

public class AuthorisationFragment extends Fragment {
    Button goToRegistrationFragment;
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.authorisation_fragment, container, false);
        goToRegistrationFragment = view.findViewById(R.id.registration_button);
        goToRegistrationFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.addFragment(new RegistrationFragment());
            }
        });
        return view;
    }
}