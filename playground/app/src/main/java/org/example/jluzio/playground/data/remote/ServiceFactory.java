package org.example.jluzio.playground.data.remote;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jluzio on 14/03/2018.
 */

public class ServiceFactory {

    /**
     * TODO: do proper instancing - solve memory issues
     * @return
     */
    public static UserService getUserService() {
        int timeoutSecs = 5;
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(timeoutSecs, TimeUnit.SECONDS)
                .connectTimeout(timeoutSecs, TimeUnit.SECONDS)
                .writeTimeout(timeoutSecs, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                //.client(client)
                .build();
        return retrofit.create(UserService.class);
    }
}
