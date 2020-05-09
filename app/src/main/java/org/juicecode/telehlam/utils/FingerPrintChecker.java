package org.juicecode.telehlam.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import org.juicecode.telehlam.R;

import java.util.concurrent.Executor;

public class FingerPrintChecker {
    private FragmentManagerSimplifier fragmentManagerSimplifier;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    private SharedPreferencesRepository classRepository;
    public static final int ADDING_FINGERPRINT = 0;
    public static final int IDENTIFYING_WITH_FINGERPRINT = 1;
    private int type;

    public FingerPrintChecker(int type) {
        this.type = type;
    }

    public void checkAuth(final Context context, SharedPreferencesRepository repository){
        executor = ContextCompat.getMainExecutor(context);
        fragmentManagerSimplifier = (FragmentManagerSimplifier)context;
        classRepository = repository;
        biometricPrompt = new BiometricPrompt((FragmentActivity) context,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);

            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                classRepository.saveFingerPrint();
                fragmentManagerSimplifier.addFragment(R.id.nav_home);
                String text;
                if(type==ADDING_FINGERPRINT){
                    text = "You added fingerprint!";
                } else {
                    text = "confirmed";
                }
                Toast.makeText(context,text ,
                        Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(context, "Authentication failed",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login")
                .setSubtitle("Log in using your biometric")
                .setNegativeButtonText("go back")
                .build();
        biometricPrompt.authenticate(promptInfo);
        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.

    }
}
