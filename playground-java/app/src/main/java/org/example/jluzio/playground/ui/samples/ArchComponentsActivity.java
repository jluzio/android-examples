package org.example.jluzio.playground.ui.samples;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.example.jluzio.playground.R;
import org.example.jluzio.playground.data.local.dao.UserDao;
import org.example.jluzio.playground.data.remote.UserService;
import org.example.jluzio.playground.data.remote.response.User;
import org.example.jluzio.playground.data.viewModel.UserViewModel;
import org.example.jluzio.playground.injection.AppId;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArchComponentsActivity extends AppCompatActivity {
    private static final String TAG = "ArchComponentsActivity";
    private static final String SAVED_USER_ID = "saved-user";

    private TextView appIdText;
    private TextView userIdText;
    private TextView userNameText;
    private TextView userUsernameText;
    private TextView userEmailText;
    private TextView statusText;
    private Button getDataButton;
    private Button saveDataButton;
    private Button loadDataButton;
    private UserViewModel userViewModel;

    @Inject @AppId
    String appId;

    @Inject
    UserDao userDao;
    @Inject
    UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arch_components);

        Log.d(TAG, "onCreate: injection (start)");
        Log.d(TAG, "onCreate: appId: " + appId);
        Log.d(TAG, "onCreate: userDao: " + userDao);
        Log.d(TAG, "onCreate: userService: " + userService);
        Log.d(TAG, "onCreate: injection (end)");

//        userService = ServiceFactory.getUserService();

        appIdText = findViewById(R.id.appIdText);
        userIdText = findViewById(R.id.userIdText);
        userNameText = findViewById(R.id.userNameText);
        userUsernameText = findViewById(R.id.userUsernameText);
        userEmailText = findViewById(R.id.userEmailText);
        statusText = findViewById(R.id.statusText);
        getDataButton = findViewById(R.id.getDataButton);
        saveDataButton = findViewById(R.id.saveDataButton);
        loadDataButton = findViewById(R.id.loadDataButton);

        appIdText.setText(appId);

        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUser().observe(this, user -> {
            userIdText.setText(user.id);
            userNameText.setText(user.name);
            userUsernameText.setText(user.username);
            userEmailText.setText(user.email);
        });
        userViewModel.getStatus().observe(this, statusVal -> {
            statusText.setText(statusVal);
        });

        getDataButton.setOnClickListener(view -> {
            userViewModel.getStatus().setValue("Retrieving user...");
            userService.getUser(userViewModel.nextUserId()).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    userViewModel.getUser().setValue(user);
                    userViewModel.getStatus().setValue("Data OK");
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    User errorUser = new User("...", "...", "...", "...");
                    userViewModel.getUser().setValue(errorUser);
                    userViewModel.getStatus().setValue("Error getting data: " + t.getMessage());
                    statusText.setText("Error getting data: " + t.getMessage());
                }
            });
        });

        saveDataButton.setOnClickListener(view -> {
            Observable
                    .fromCallable(getSimpleCallable(this::saveData))
                    .subscribeOn(Schedulers.io())
                    .subscribe();
        });


        loadDataButton.setOnClickListener(view -> {
            Observable
                    .fromCallable(getSimpleCallable(this::loadData))
                    .subscribeOn(Schedulers.io())
                    .subscribe();
        });

//        loadDataButton.setOnClickListener(view -> {
//            getSimpleTask(() -> {loadData();}).execute(this);
//        });

    }

    private void saveData() {
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getStatus().postValue("Saving user...");

        User formUser = userViewModel.getUser().getValue();

        if (formUser != null) {
            org.example.jluzio.playground.data.local.entity.User savedUser =
                    new org.example.jluzio.playground.data.local.entity.User();
            savedUser.setId(SAVED_USER_ID);
            savedUser.setName(formUser.name);
            savedUser.setUsername(formUser.username);
            savedUser.setEmail(formUser.email);

            userDao.insertAll(savedUser);
        }

        userViewModel.getStatus().postValue("Saved user");
    }

    private void loadData() {
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getStatus().postValue("Loading user...");

        org.example.jluzio.playground.data.local.entity.User savedUser = userDao.get(SAVED_USER_ID);
        if (savedUser != null) {
            User formUser = new User();
            formUser.id = savedUser.getId();
            formUser.name = savedUser.getName();
            formUser.username = savedUser.getUsername();
            formUser.email = savedUser.getEmail();

            userViewModel.getUser().postValue(formUser);
        }

        userViewModel.getStatus().postValue("Loaded user");
    }

    private Callable<String> getSimpleCallable(Runnable runnable) {
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                runnable.run();
                return "dummy-result";
            }
        };
    }

    private AsyncTask<Context, Void, Void> getSimpleTask(Runnable runnable) {
        return new AsyncTask<Context, Void, Void>() {
            @Override
            protected Void doInBackground(Context... contexts) {
//                for (Context context: contexts) {
//                    runnable.run();
//                }
                runnable.run();

                return null;
            }
        };
    }

}
