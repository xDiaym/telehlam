package org.juicecode.telehlam.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.snackbar.Snackbar;

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
    private Context context;
    private SnackbarShower snackbarShower;

    public FingerPrintChecker(int type, Context context, SnackbarShower snackbarShower) {
        this.type = type;
        this.context = context;
        this.snackbarShower = snackbarShower;
    }

    public void checkAuth(SharedPreferencesRepository repository) {
        executor = ContextCompat.getMainExecutor(context);
        fragmentManagerSimplifier = (FragmentManagerSimplifier) context;
        classRepository = repository;
        if (checkBiometricAvailable()) {
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
                    if (type == ADDING_FINGERPRINT) {
                        text = "You added fingerprint!";
                    } else {
                        text = "confirmed";
                    }
                    Toast.makeText(context, text,
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
                    .setTitle("TELEhlam")
                    .setSubtitle("COnfirm fingerprint to continue")
                    .setNegativeButtonText("Cancel")
                    .build();
            biometricPrompt.authenticate(promptInfo);
            // Prompt appears when user clicks "Log in".
            // Consider integrating with the keystore to unlock cryptographic operations,
            // if needed by your app.

        }
    }

    public boolean checkBiometricAvailable() {
        BiometricManager biometricManager = BiometricManager.from(context);

        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                return true;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                snackbarShower.showSnackbar("This feature is not available on your device");
                return false;

            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                snackbarShower.showSnackbar("You do not have any biometrics, add them in your device settings");
                return false;
        }
        return false;
    }
}
