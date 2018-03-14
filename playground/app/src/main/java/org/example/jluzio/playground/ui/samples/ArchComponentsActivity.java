package org.example.jluzio.playground.ui.samples;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import org.example.jluzio.playground.R;
import org.example.jluzio.playground.data.remote.ServiceFactory;
import org.example.jluzio.playground.data.remote.UserService;
import org.example.jluzio.playground.data.remote.response.User;
import org.example.jluzio.playground.data.viewModel.UserViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArchComponentsActivity extends AppCompatActivity {
    private UserService userService;
    private TextView userId;
    private TextView userName;
    private TextView userUsername;
    private TextView userEmail;
    private TextView status;
    private Button getDataButton;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arch_components);

        userService = ServiceFactory.getUserService();

        userId = findViewById(R.id.userId);
        userName = findViewById(R.id.userName);
        userUsername = findViewById(R.id.userUsername);
        userEmail = findViewById(R.id.userEmail);
        status = findViewById(R.id.status);
        getDataButton = findViewById(R.id.getDataButton);

        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getUser().observe(this, user -> {
            userId.setText(user.id);
            userName.setText(user.name);
            userUsername.setText(user.username);
            userEmail.setText(user.email);
        });
        userViewModel.getStatus().observe(this, statusVal -> {
            status.setText(statusVal);
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
                    status.setText("Error getting data: " + t.getMessage());
                }
            });
        });

    }
}
