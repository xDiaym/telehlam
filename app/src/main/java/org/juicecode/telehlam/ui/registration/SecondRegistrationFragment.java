package org.juicecode.telehlam.ui.registration;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.rest.RetrofitBuilder;
import org.juicecode.telehlam.rest.User;
import org.juicecode.telehlam.utils.Constant;
import org.juicecode.telehlam.utils.DrawerLocker;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;
import org.juicecode.telehlam.utils.KeyboardManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondRegistrationFragment extends Fragment {
    //UI elements
    private ImageButton goBackButton;
    private Button floatingActionButton;
    private EditText loginField;
    private EditText passwordField;
    private EditText repeatPassword;
    private Bundle gettingArguments;
    private SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.second_registration_fragment, container, false);

        floatingActionButton = view.findViewById(R.id.login_registration);
        final FragmentManagerSimplifier fragmentManagerSimplifier = (FragmentManagerSimplifier) view.getContext();

        //set UI elements
        sharedPreferences = getActivity().getSharedPreferences("org.juicecode.telehlam", Context.MODE_PRIVATE);
        floatingActionButton = view.findViewById(R.id.login_registration);
        loginField = view.findViewById(R.id.loginField);
        repeatPassword = view.findViewById(R.id.repeatPasswordField);
        passwordField = view.findViewById(R.id.passwordField);
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
                /*if (login.isEmpty()
                        && password.isEmpty()
                        && password.equals(repeatedPassword))
                {*/
                    if(login.isEmpty()){

                    }else{

                    RetrofitBuilder retrofit = new RetrofitBuilder();

                    //register user with all info
                    Call registerUser = retrofit.getAuthorisationAPI().registerUser(new User(login, password, name, surname));
                    registerUser.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            Log.i("responseCode", Integer.toString(response.code()));
                            if (response.body() != null) {
                                //TODO switch on SharedPreferences
                                Constant.setUserLogin(login);
                                //removing fragments
                                fragmentManagerSimplifier.remove("firstRegistrationFragment");
                                fragmentManagerSimplifier.remove("authorisation");
                                fragmentManagerSimplifier.remove("secondRegistrationFragment");
                                sharedPreferences.edit().putBoolean("isNotRegistered",false).putString("userLogin",login).commit();
                                drawerLocker.setDrawerLock(false);
                                //TODO switch on SharedPreferences
                                Constant.isRegistered = true;
                                Log.i("responseMessage", response.body().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Log.i("error", t.toString());
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
