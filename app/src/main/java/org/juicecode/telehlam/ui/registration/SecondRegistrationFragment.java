package org.juicecode.telehlam.ui.registration;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.rest.AsyncUserApi;
import org.juicecode.telehlam.rest.RetrofitBuilder;
import org.juicecode.telehlam.rest.AuthInfo;
import org.juicecode.telehlam.rest.User;
import org.juicecode.telehlam.utils.ApiCallback;
import org.juicecode.telehlam.utils.DrawerLocker;
import org.juicecode.telehlam.utils.FieldValidator;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;
import org.juicecode.telehlam.utils.KeyboardManager;
import org.juicecode.telehlam.utils.SharedPreferencesRepository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecondRegistrationFragment extends Fragment {
    //UI elements
    private ImageButton goBackButton;
    private Button floatingActionButton;
    private EditText loginField;
    private EditText passwordField;
    private EditText repeatPassword;
    private Bundle gettingArguments;
    private TextView loginError;
    private TextView passwordError;
    private TextView repeatPasswordError;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.second_registration_fragment, container, false);

        floatingActionButton = view.findViewById(R.id.login_registration);
        final FragmentManagerSimplifier fragmentManagerSimplifier = (FragmentManagerSimplifier) view.getContext();

        //set UI elements
        floatingActionButton = view.findViewById(R.id.login_registration);
        loginField = view.findViewById(R.id.loginField);
        repeatPassword = view.findViewById(R.id.repeatPasswordField);
        passwordField = view.findViewById(R.id.passwordField);
        loginError = view.findViewById(R.id.loginError);
        passwordError = view.findViewById(R.id.passwordError);
        repeatPasswordError = view.findViewById(R.id.repeatPasswordError);

        //arguments from first fragment
        gettingArguments = getArguments();
        final String[] values = gettingArguments.getStringArray("argumentsFromFirstRegistration");
        final DrawerLocker drawerLocker = (DrawerLocker) view.getContext();
        drawerLocker.setDrawerLock(true);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            //String value of fields
            private String name = values[0];
            private String surname = values[1];
            private String login;
            private String phone = values[2];
            private String password;
            private String repeatedPassword;

            @Override
            public void onClick(View v) {
                //getting values

                login = loginField.getText().toString();
                password = passwordField.getText().toString();
                repeatedPassword = repeatPassword.getText().toString();
                //check if user wrote all information

                if (login.isEmpty()) {
                    loginError.setText(R.string.loginError);
                    passwordError.setText("");
                    repeatPassword.setText("");
                } else if (!FieldValidator.validateLogin(login)) {
                    loginError.setText(R.string.wrongLogin);
                } else if (password.isEmpty()) {
                    passwordError.setText(R.string.passwordError);
                    repeatPasswordError.setText("");
                    loginError.setText("");
                } else if (!FieldValidator.validatePassword(password)) {
                    passwordError.setText(R.string.weakPassword);
                    repeatPasswordError.setText("");
                    loginError.setText("");
                } else if (!repeatedPassword.equals(password)) {
                    repeatPasswordError.setText(R.string.wrongRepeatedPassword);
                    loginError.setText("");
                    passwordError.setText("");
                } else {
                    final User user = new User(login, password, name, surname);
                    AsyncUserApi registerUser = new AsyncUserApi(new RetrofitBuilder());
                    registerUser.registerUser(user, new ApiCallback<AuthInfo>() {
                        @Override
                        public void execute(AuthInfo response) {
                            //removing fragments
                            fragmentManagerSimplifier.remove("firstRegistrationFragment");
                            fragmentManagerSimplifier.remove("secondRegistrationFragment");
                            fragmentManagerSimplifier.remove("authorisation");
                            //saving info
                            SharedPreferencesRepository repository = new SharedPreferencesRepository(getContext());
                            repository.saveToken(response.getToken());
                            drawerLocker.setDrawerLock(false);
                        }
                    });
                }
            }
        });

        goBackButton = view.findViewById(R.id.goBackButton);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardManager.hideKeyboard(getActivity());
                getActivity().onBackPressed();

            }
        });
        return view;
    }

}
