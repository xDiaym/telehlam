package org.juicecode.telehlam.ui.registration;

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
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;
import org.juicecode.telehlam.utils.KeyboardManager;


public class FirstRegistrationFragment extends Fragment {
    //UI elements
    private ImageButton goBackButton;
    private Button floatingActionButton;
    private EditText nameField;
    private EditText surnameField;
    private TextView nameError;
    private TextView surnameError;


    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.registration_fragment, container, false);


        final FragmentManagerSimplifier fragmentManagerSimplifier = (FragmentManagerSimplifier) view.getContext();

        //set UI elements
        floatingActionButton = view.findViewById(R.id.next_button);
        nameField = view.findViewById(R.id.nameField);
        surnameField = view.findViewById(R.id.surnameField);
        nameError = view.findViewById(R.id.nameError);
        surnameError = view.findViewById(R.id.surnameError);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = nameField.getText().toString();
                String surname = surnameField.getText().toString();

                //checking if user wrote the whole info
                if (name.isEmpty()) {
                    nameError.setText(R.string.nameError);
                    surnameError.setText("");
                } else if (surname.isEmpty()) {
                    surnameError.setText(R.string.surnameError);
                    nameError.setText("");
                } else {
                    //sending arguments and open second fragment
                    Bundle arguments = new Bundle();
                    arguments.putStringArray("argumentsFromFirstRegistration", new String[]{name, surname});
                    fragmentManagerSimplifier.addWithArguments(R.id.secondRegistrationFragment, arguments);
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
