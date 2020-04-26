package org.juicecode.telehlam.rest;

import org.juicecode.telehlam.utils.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private static UserApi userApi;
    private static Retrofit retrofit;

    public RetrofitBuilder() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(Constant.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.userApi = retrofit.create(UserApi.class);
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public UserApi getUserApi() {
        return userApi;
    }
}
