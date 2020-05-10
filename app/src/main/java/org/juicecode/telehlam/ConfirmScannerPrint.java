package org.juicecode.telehlam;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.juicecode.telehlam.utils.FingerPrintChecker;
import org.juicecode.telehlam.utils.SharedPreferencesRepository;

public class ConfirmScannerPrint extends Fragment {
    Button login;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fingerprint_auth, container, false);
        login = view.findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FingerPrintChecker checker = new FingerPrintChecker(FingerPrintChecker.IDENTIFYING_WITH_FINGERPRINT);
                checker.checkAuth(getContext(), new SharedPreferencesRepository(getContext()));
            }
        });
        return view;
    }

}
