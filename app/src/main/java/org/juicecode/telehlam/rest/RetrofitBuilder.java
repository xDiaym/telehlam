package org.juicecode.telehlam.rest;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;

import org.juicecode.telehlam.utils.Constant;
import org.juicecode.telehlam.utils.DrawerLocker;
import org.juicecode.telehlam.utils.FragmentManagerSimplifier;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private static UserApi userApi;
    private static Retrofit retrofit;
    public RetrofitBuilder(){
        this.retrofit = new Retrofit.Builder().baseUrl(Constant.getBaseURL()).addConverterFactory(GsonConverterFactory.create()).build();
        this.userApi = retrofit.create(UserApi.class);
    }
    public Retrofit getRetrofit() {
        return retrofit;
    }
    public UserApi getUserApi(){
        return userApi;
    }
}
