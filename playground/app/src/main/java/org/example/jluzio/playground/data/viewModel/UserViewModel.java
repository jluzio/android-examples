package org.example.jluzio.playground.data.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import org.example.jluzio.playground.data.remote.response.User;

/**
 * Created by jluzio on 14/03/2018.
 */

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<String> status = new MutableLiveData<>();
    private Integer userId = 0;

    public UserViewModel() {
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<String> getStatus() {
        return status;
    }

    public Integer nextUserId() {
        return ++userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
