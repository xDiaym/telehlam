package org.juicecode.telehlam.rest;

import java.net.UnknownServiceException;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthorisationAPI {
    @POST("/user/signup")
    Call<User> registerUser(@Body User user);
}
