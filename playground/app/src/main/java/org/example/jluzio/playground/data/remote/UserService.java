package org.example.jluzio.playground.data.remote;

import org.example.jluzio.playground.data.remote.response.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by jluzio on 14/03/2018.
 */

public interface UserService {

    @GET("users/{user}")
    Call<User> getUser(@Path("user") Integer user);

}
