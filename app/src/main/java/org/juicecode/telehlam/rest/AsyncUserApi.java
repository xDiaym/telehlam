package org.juicecode.telehlam.rest;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import org.juicecode.telehlam.utils.ApiCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AsyncUserApi {
    private static final String TAG = AsyncUserApi.class.getCanonicalName();

    private static UserApi userApi;

    public AsyncUserApi(@NonNull RetrofitBuilder retrofitBuilder) {
        userApi = retrofitBuilder.getUserApi();
    }

    public void registerUser(@NonNull final User user,
                             @NonNull final ApiCallback<AuthInfo> callback) {
        final Call<AuthInfo> call = userApi.registerUser(user);
        call.enqueue(new Callback<AuthInfo>() {
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        Log.i(TAG, "Incorrect server answer!");
                        return;
                    }
                    AuthInfo info = (AuthInfo) response.body();
                    callback.execute(info);
                    Log.i(TAG, "User successfully registered");
                } else {
                    //TODO make error handling
                    Log.e(TAG, "Unsuccessful response at registerUser");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG, String.format("Failure while sending response\n" +
                                         "Error: %s",
                                          t.getMessage()));
            }
        });
    }

    public void signIn(@NonNull final User user, @NonNull final ApiCallback<Token> callback) {
        Call<Token> signIn = userApi.signIn(user);
        signIn.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.i("responseBody", response.toString());
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        try {
                            String res = new Gson().toJson(response.body());
                            Log.e(TAG, res);
                            Token token = new Gson().fromJson(res, Token.class);
                            callback.execute(token);
                        } catch (JsonIOException exception) {
                            exception.printStackTrace();
                        }
                    }
                } else {
                    //TODO error handling
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
    }

}
