package org.juicecode.telehlam.ui.registration;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.rest.RetrofitBuilder;
import org.juicecode.telehlam.utils.Constant;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;
import org.juicecode.telehlam.utils.KeyboardManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationFragment  extends Fragment {
    private ImageButton goBackButton;
    private Button floatingActionButton;
    private EditText loginField;
    private EditText passwordField;
    private EditText nameField;
    private EditText surnameField;
    private EditText phoneField;
    private EditText repeatPassword;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.registration_fragment, container, false);

        floatingActionButton = view.findViewById(R.id.login_registration);
        final FragmentManagerSimplifier fragmentManagerSimplifier = (FragmentManagerSimplifier)view.getContext();
        fragmentManagerSimplifier.lockDrawer();

        floatingActionButton = view.findViewById(R.id.login_registration);
        loginField = view.findViewById(R.id.loginField);
        repeatPassword =view.findViewById(R.id.repeatPasswordField);
        passwordField = view.findViewById(R.id.passwordField);
        nameField = view.findViewById(R.id.nameField);
        surnameField = view.findViewById(R.id.surnameField);
        phoneField = view.findViewById(R.id.phoneField);
        final DrawerLocker drawerLocker = (DrawerLocker) view.getContext();
        drawerLocker.setDrawerLock(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            private String name;
            private String surname;
            private String login;
            private String phone ;
            private String password;
            private String repeatedPassword;


            @Override
            public void onClick(View v) {
                name = nameField.getText().toString();
                surname = surnameField.getText().toString();
                login = loginField.getText().toString();
                phone = phoneField.getText().toString();
                password = passwordField.getText().toString();
                repeatedPassword = repeatPassword.getText().toString();

                if(!(name.isEmpty()
                        &&surname.isEmpty()
                        &&login.isEmpty()
                        &&password.isEmpty())
                        &&password.equals(repeatedPassword)
                       ){

                    RetrofitBuilder retrofit = new RetrofitBuilder();


                    Call registerUser = retrofit.getAuthorisationAPI().registerUser(new User(login,password,name,surname));
                    registerUser.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            Log.i("responseBody", Integer.toString(response.code()));
                            if(response.body()!=null){

                                Constant.setUserLogin(login);
                                fragmentManagerSimplifier.remove("registration");
                                fragmentManagerSimplifier.remove("authorisation");
                                fragmentManagerSimplifier.unlockDrawer();
                                Constant.isRegistered = true;
                                Log.i("responseMessage",response.body().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {
                            Log.i("error", t.toString());
                        }
                    });

                }



                fragmentManagerSimplifier.remove("registration");
                fragmentManagerSimplifier.remove("authorisation");
                drawerLocker.setDrawerLock(false);
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
