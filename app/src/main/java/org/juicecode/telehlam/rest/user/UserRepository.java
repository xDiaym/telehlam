package org.juicecode.telehlam.rest.user;

import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonIOException;

import org.juicecode.telehlam.database.users.User;
import org.juicecode.telehlam.rest.RetrofitBuilder;
import org.juicecode.telehlam.utils.SnackbarShower;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserRepository {
    private static final String TAG = UserRepository.class.getCanonicalName();
    private LinearLayout layout;
    private static UserApi userApi;

    public UserRepository(@NonNull RetrofitBuilder retrofitBuilder, LinearLayout layout) {
        userApi = retrofitBuilder.getUserApi();
        this.layout = layout;
    }
    public UserRepository(@NonNull RetrofitBuilder retrofitBuilder) {
        userApi = retrofitBuilder.getUserApi();
    }

    public LiveData<AuthInfo> registerUser(@NonNull final LoginInfo loginInfo) {
        final Call<AuthInfo> call = userApi.registerUser(loginInfo);
        final MutableLiveData<AuthInfo> info = new MutableLiveData<>();

        call.enqueue(new Callback<AuthInfo>() {
            @Override
            public void onResponse(Call<AuthInfo> call, Response<AuthInfo> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        Log.i(TAG, "Incorrect server answer!");
                        return;
                    }
                    info.setValue(response.body());
                    Log.i(TAG, "User successfully registered");
                } else {
                    Log.e(TAG, "Unsuccessful response at registerUser");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG, String.format("Failure while sending response\n" +
                                "Error: %s", t.getMessage()));

                SnackbarShower snackbarShower = new SnackbarShower(layout);
                snackbarShower.showSnackbar("Make sure you have an Internet connection");
            }

        });

        return info;
    }

    public LiveData<AuthInfo> signIn(@NonNull final LoginInfo loginInfo) {
        final Call<AuthInfo> signIn = userApi.signIn(loginInfo);
        final MutableLiveData<AuthInfo> info = new MutableLiveData<>();

        signIn.enqueue(new Callback<AuthInfo>() {
            @Override
            public void onResponse(Call<AuthInfo> call, Response<AuthInfo> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        try {
                            info.setValue(response.body());
                        } catch (JsonIOException exception) {
                            exception.printStackTrace();
                        }
                    }
                } else {
                    //TODO error handling
                    SnackbarShower snackbarShower = new SnackbarShower(layout);
                    snackbarShower.showSnackbar("Wrong user login or password");
                    Log.e(TAG, "Unsuccessful response at signIn");
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG, String.format("Failure while sending response\n" +
                                "Error: %s",
                        t.getMessage()));
                SnackbarShower snackbarShower = new SnackbarShower(layout);
                snackbarShower.showSnackbar("Make sure you have an Internet connection");
            }
        });

        return info;
    }

    public LiveData<List<User>> byLogin(@NonNull String login) {
        Call<List<User>> call = userApi.byLogin(login);
        final MutableLiveData<List<User>> users = new MutableLiveData<>();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    users.setValue(response.body());
                } else {
                    //TODO error handling
                    Log.e(TAG, "Unsuccessful response at signIn");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e(TAG, String.format("Failure while sending response\n" +
                                "Error: %s",
                        t.getMessage()));
            }
        });

        return users;
    }

    public LiveData<User> byId(long id) {
        Call<User> call = userApi.byId(id);
        final MutableLiveData<User> user = new MutableLiveData<>();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    user.setValue(response.body());
                } else {
                    //TODO error handling
                    Log.e(TAG, "Unsuccessful response at signIn");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e(TAG, String.format("Failure while sending response\n" +
                                "Error: %s",
                        t.getMessage()));
            }
        });

        return user;
    }

}
