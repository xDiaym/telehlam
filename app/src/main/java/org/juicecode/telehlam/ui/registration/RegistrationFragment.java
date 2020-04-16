package org.juicecode.telehlam.ui.registration;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.rest.RetrofitBuilder;
import org.juicecode.telehlam.rest.User;
import org.juicecode.telehlam.utils.Constant;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;
import org.juicecode.telehlam.utils.KeyboardManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegistrationFragment  extends Fragment {
    private ImageButton goBackButton;
    private ExtendedFloatingActionButton floatingActionButton;
    private EditText loginField;
    private EditText passwordField;
    private EditText nameField;
    private EditText surnameField;
    private EditText phoneField;
    private EditText emailField;
    private EditText repeatPassword;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.registration_fragment, container, false);
        final FragmentManagerSimplifier fragmentManagerSimplifier = (FragmentManagerSimplifier)view.getContext();
        fragmentManagerSimplifier.lockDrawer();

        floatingActionButton = view.findViewById(R.id.login_registration);
        loginField = (EditText)view.findViewById(R.id.loginField);
        repeatPassword =(EditText)view.findViewById(R.id.repeatPasswordField);
        passwordField =(EditText) view.findViewById(R.id.passwordField);
        nameField =(EditText) view.findViewById(R.id.nameField);
        surnameField = (EditText)view.findViewById(R.id.surnameField);
        emailField =(EditText) view.findViewById(R.id.emailField);
        phoneField = (EditText)view.findViewById(R.id.phoneField);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            private String name =  nameField.getText().toString();
            private String surname = surnameField.getText().toString();
            private String login = loginField.getText().toString();
            private String phone = phoneField.getText().toString();
            private String password = passwordField.getText().toString();
            private String repeatedPassword = repeatPassword.getText().toString();
            private String email = emailField.getText().toString();

            @Override
            public void onClick(View v) {
                if(!name.isEmpty() &&!surname.isEmpty() &&!login.isEmpty()&&!password.isEmpty()&&passwordField.equals(repeatedPassword)){
                    if(email.isEmpty()){
                        //Snack bar
                    }

                    Call registerUser = RetrofitBuilder.getAuthorisationAPI().registerUser(new User(login,password,name,surname));
                    registerUser.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
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
