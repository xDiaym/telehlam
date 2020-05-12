package org.juicecode.telehlam.ui.fingerprint;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.utils.FingerPrintChecker;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;
import org.juicecode.telehlam.utils.SharedPreferencesRepository;
import org.juicecode.telehlam.utils.SnackbarShower;
import org.w3c.dom.Text;

import java.util.concurrent.Executor;


public class RequestFingerPrintFragment extends Fragment {
    private Button yesButton;
    private Button noButton;
    private Button deleteFingerPrint;
    private FragmentManagerSimplifier fragmentManagerSimplifier;
    private TextView warning;
    private LinearLayout layout;
    private SharedPreferencesRepository repository;
    public RequestFingerPrintFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_request_finger_print, container, false);
        layout = view.findViewById(R.id.layout);
        yesButton = view.findViewById(R.id.yesButton);
        noButton = view.findViewById(R.id.noButton);
        deleteFingerPrint = view.findViewById(R.id.deleteFingerPrint);
        deleteFingerPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FingerPrintChecker checker = new FingerPrintChecker(FingerPrintChecker.DELETING_FINGERPRINT, getContext(), new SnackbarShower(layout));
                checker.checkAuth(repository);
            }
        });
        deleteFingerPrint.setVisibility(View.GONE);
        warning = view.findViewById(R.id.warning);
        fragmentManagerSimplifier = (FragmentManagerSimplifier)view.getContext();
        repository = new SharedPreferencesRepository(getContext());
        if(repository.getFingerPrint()){
            warning.setText(R.string.hasFingerprint);
            yesButton.setVisibility(View.GONE);
            noButton.setVisibility(View.GONE);
            deleteFingerPrint.setVisibility(View.VISIBLE);

        }

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FingerPrintChecker checker = new FingerPrintChecker(FingerPrintChecker.ADDING_FINGERPRINT, getContext(), new SnackbarShower(layout));
                checker.checkAuth(repository);
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentManagerSimplifier.addFragment(R.id.nav_home);
            }
        });




        return view;
    }

}
