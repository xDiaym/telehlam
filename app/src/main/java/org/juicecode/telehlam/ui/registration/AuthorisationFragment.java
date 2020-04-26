package org.juicecode.telehlam.ui.registration;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import org.juicecode.telehlam.MainActivity;
import org.juicecode.telehlam.R;
import org.juicecode.telehlam.utils.DrawerLocker;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;


public class AuthorisationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater layoutInflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.authorisation_fragment, container, false);

        final MainActivity activity = (MainActivity) view.getContext();
        activity.setDrawerLock(true);

        Button logIn = view.findViewById(R.id.log_in_button);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.remove("authorisation");
                activity.setDrawerLock(false);
            }
        });

        Button forgetPassword = view.findViewById(R.id.forget_password);
        forgetPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO(matthew.nekirov@gmail.com): За 20+ дней мы восстановление вряд ли успеем
                //  но попробывать нужно
                Snackbar
                        .make(v, "Soon...", BaseTransientBottomBar.LENGTH_SHORT)
                        .show();
            }
        });

        Button signUp = view.findViewById(R.id.sign_up);
        signUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                activity.remove("authorisation");
                activity.addFragment(new SignUpFragment(), "signUpFragment");
                activity.setDrawerLock(false);
            }
        });

        return view;
    }
}