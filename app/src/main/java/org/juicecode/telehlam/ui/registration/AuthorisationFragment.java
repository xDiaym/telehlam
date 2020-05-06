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
import org.juicecode.telehlam.rest.user.AsyncUserApi;
import org.juicecode.telehlam.rest.RetrofitBuilder;
import org.juicecode.telehlam.rest.user.AuthInfo;
import org.juicecode.telehlam.rest.user.LoginInfo;
import org.juicecode.telehlam.utils.ApiCallback;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;
import org.juicecode.telehlam.utils.SharedPreferencesRepository;


public class AuthorisationFragment extends Fragment {
    Button goToRegistrationFragment;
    Button loginButton;
    EditText loginField;
    EditText passwordField;
    TextView loginError;
    TextView passwordError;
    FragmentManagerSimplifier fragmentManagerSimplifier;
    SharedPreferences sharedPreferences;
    Context context;
    @Override
    public View onCreateView(LayoutInflater layoutInflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.authorisation_fragment, container, false);
        context = getContext();
        loginButton = view.findViewById(R.id.login_authorisation);
        loginField = view.findViewById(R.id.loginField);
        loginError = view.findViewById(R.id.loginError);
        passwordField = view.findViewById(R.id.passwordField);
        passwordError = view.findViewById(R.id.passwordError);

        fragmentManagerSimplifier = (FragmentManagerSimplifier) view.getContext();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String login = loginField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();

                if (checkFields(login, password)) {
                    AsyncUserApi api = new AsyncUserApi(new RetrofitBuilder());
                    api.signIn(new LoginInfo(login, password), new ApiCallback<AuthInfo>() {
                        @Override
                        public void execute(AuthInfo response) {
                            //if user is found saving token and login of user in SharedPreferences
                            SharedPreferencesRepository repository = new SharedPreferencesRepository(context);
                            repository.saveToken(response.getToken());
                            repository.saveLogin(login);
                            fragmentManagerSimplifier.addFragment(R.id.nav_home);
                        }
                    });

                }


            }
        });

        goToRegistrationFragment = view.findViewById(R.id.registration_button);
        goToRegistrationFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentManagerSimplifier) getContext()).addFragment(R.id.firstRegistrationFragment);
            }
        });
        return view;
    }

    private boolean checkFields(String login, String password) {
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