package org.juicecode.telehlam;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.juicecode.telehlam.R;
import org.juicecode.telehlam.utils.FingerPrintChecker;
import org.juicecode.telehlam.utils.SharedPreferencesRepository;
import org.juicecode.telehlam.utils.SnackbarShower;

public class ConfirmScannerPrint extends Fragment {
    Button login;
    LinearLayout layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_fingerprint_auth, container, false);
        layout = view.findViewById(R.id.layout);
        login = view.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FingerPrintChecker checker = new FingerPrintChecker(FingerPrintChecker.IDENTIFYING_WITH_FINGERPRINT, getContext(),new SnackbarShower(layout));
                checker.checkAuth(new SharedPreferencesRepository(getContext()));
            }
        });
        return view;
    }

}
