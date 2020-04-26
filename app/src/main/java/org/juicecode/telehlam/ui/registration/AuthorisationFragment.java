package org.juicecode.telehlam.ui.registration;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.rest.RestUserClass;
import org.juicecode.telehlam.rest.RetrofitBuilder;
import org.juicecode.telehlam.rest.User;
import org.juicecode.telehlam.utils.DrawerLocker;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;


public class AuthorisationFragment extends Fragment {
    Button goToRegistrationFragment;
    Button loginButton;
    EditText loginField;
    EditText passwordField;
    TextView loginError;
    TextView passwordError;
    FragmentManagerSimplifier fragmentManagerSimplifier;
    DrawerLocker drawerLocker;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.authorisation_fragment, container, false);
        loginButton = view.findViewById(R.id.login_authorisation);
        loginField = view.findViewById(R.id.loginField);
        loginError = view.findViewById(R.id.loginError);
        passwordField = view.findViewById(R.id.passwordField);
        passwordError = view.findViewById(R.id.passwordError);
        fragmentManagerSimplifier = (FragmentManagerSimplifier) view.getContext();
        drawerLocker = (DrawerLocker) view.getContext();
        sharedPreferences = view.getContext().getSharedPreferences("org.juicecode.telehlam", Context.MODE_PRIVATE);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = loginField.getText().toString();
                String password = passwordField.getText().toString();
                if (checkFields(login, password)) {
                    RetrofitBuilder retrofitBuilder = new RetrofitBuilder();
                    RestUserClass signIn = new RestUserClass(fragmentManagerSimplifier, new User(login, password), drawerLocker, sharedPreferences, retrofitBuilder);
                    signIn.signIn();
                    fragmentManagerSimplifier.remove("authorisation");
                    drawerLocker.setDrawerLock(false);
                }


            }
        });

        goToRegistrationFragment = view.findViewById(R.id.registration_button);
        final DrawerLocker drawerLocker = (DrawerLocker) view.getContext();
        drawerLocker.setDrawerLock(true);

        goToRegistrationFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((FragmentManagerSimplifier) getContext()).addFragment(new FirstRegistrationFragment(), "firstRegistrationFragment");
            }
        });
        return view;
    }

    public boolean checkFields(String login, String password) {
        if (login.isEmpty()) {
            loginError.setText(R.string.loginError);
            passwordError.setText("");
            return false;
        } else if (password.isEmpty()) {
            passwordError.setText(R.string.passwordError);
            loginError.setText("");
            return false;
        } else {
            return true;
        }
    }
}