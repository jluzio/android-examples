package org.example.jluzio.playground.injection.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import org.example.jluzio.playground.data.local.dao.UserDao;
import org.example.jluzio.playground.data.local.database.AppDatabase;
import org.example.jluzio.playground.data.remote.UserService;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jluzio on 22/03/2018.
 */

@Module
public class ServicesModule {

    @Singleton
    @Provides
    public AppDatabase provideDb(Application app) {
        return Room.databaseBuilder(app, AppDatabase.class, "app-db").build();
    }

    @Singleton
    @Provides
    public UserDao provideUserDao(AppDatabase appDatabase) {
        return appDatabase.getUserDao();
    }

    @Singleton
    @Provides
    public UserService provideRetrofitUserService() {
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
