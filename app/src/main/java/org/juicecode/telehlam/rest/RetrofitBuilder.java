package org.juicecode.telehlam.rest;

import android.graphics.Typeface;

import org.juicecode.telehlam.utils.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBuilder {
    private static AuthorisationAPI authorisationAPI;
    private static Retrofit retrofit;



    public  Retrofit getRetrofit() {
        return retrofit;
    }

    public static AuthorisationAPI getAuthorisationAPI() {
        authorisationAPI = retrofit.create(AuthorisationAPI.class);
        return authorisationAPI;
    }

    public RetrofitBuilder() {
        this.retrofit = new Retrofit.Builder().baseUrl(Constant.getBaseURL()).addConverterFactory(GsonConverterFactory.create()).build();
    }
}
