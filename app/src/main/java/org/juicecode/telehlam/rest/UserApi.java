package org.juicecode.telehlam.rest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface UserApi {
    @POST("/user/signup")
    Call<User> registerUser(@Body User user);

    // TODO(matthew.nekirov@gmail.com): add methods
}
