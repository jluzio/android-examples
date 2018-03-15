package org.example.jluzio.playground.ui.samples;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.example.jluzio.playground.R;
import org.example.jluzio.playground.data.remote.ServiceFactory;
import org.example.jluzio.playground.data.remote.UserService;
import org.example.jluzio.playground.data.remote.response.User;
import org.example.jluzio.playground.data.viewModel.UserViewModel;
import org.example.jluzio.playground.injection.AppId;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArchComponentsActivity extends AppCompatActivity {
    private static final String TAG = "ArchComponentsActivity";

    private UserService userService;
    private TextView appIdText;
    private TextView userIdText;
    private TextView userNameText;
    private TextView userUsernameText;
    private TextView userEmailText;
    private TextView statusText;
    private Button getDataButton;
    private UserViewModel userViewModel;

    @Inject @AppId
    String appId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arch_components);

        Log.d(TAG, "onCreate: injection (start)");
        Log.d(TAG, "onCreate: appId: " + appId);
        Log.d(TAG, "onCreate: injection (end)");

        userService = ServiceFactory.getUserService();

        appIdText = findViewById(R.id.appIdText);
        userIdText = findViewById(R.id.userIdText);
        userNameText = findViewById(R.id.userNameText);
        userUsernameText = findViewById(R.id.userUsernameText);
        userEmailText = findViewById(R.id.userEmailText);
        statusText = findViewById(R.id.statusText);
        getDataButton = findViewById(R.id.getDataButton);

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

    }
}
