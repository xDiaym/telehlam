package org.juicecode.telehlam.rest.user;

import org.juicecode.telehlam.core.contacts.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface UserApi {
    @POST("/user/signUp")
    Call<AuthInfo> registerUser(@Body LoginInfo loginInfo);

    @POST("/user/signIn")
    Call<AuthInfo> signIn(@Body LoginInfo loginInfo);

    @GET("/user/byLogin")
    Call<List<User>> byLogin(@Query("login") String login);
    // TODO(matthew.nekirov@gmail.com): add methods
}
