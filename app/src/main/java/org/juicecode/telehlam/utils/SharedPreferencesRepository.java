package org.juicecode.telehlam.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SharedPreferencesRepository {
    private static final String NAME = "org.juicecode.telehlam";
    private static final String TOKEN = "token";
    private static final String LOGIN = "userLogin";

    private SharedPreferences preferences;


    public SharedPreferencesRepository(@NonNull Context context) {
        preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public void saveToken(@NonNull String token) {
        preferences
                .edit()
                .putString(TOKEN, token)
                .apply();
    }
    public void saveLogin(@NonNull String login){
        preferences
                .edit()
                .putString(LOGIN, login)
                .apply();
    }
    public String getLogin(){ return preferences.getString(LOGIN, null);}

    public @Nullable String getToken() {
        return preferences.getString(TOKEN, null);
    }
}
