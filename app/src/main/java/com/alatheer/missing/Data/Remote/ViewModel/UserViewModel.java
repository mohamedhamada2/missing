package com.alatheer.missing.Data.Remote.ViewModel;

import android.content.Context;

import com.alatheer.missing.Data.Remote.Model.Authentication.User;

import java.util.Observable;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends ViewModel {
    public MutableLiveData<String>phone = new MutableLiveData<>();
    public MutableLiveData<String>password = new MutableLiveData<>();
    public MutableLiveData<String>username = new MutableLiveData<>();
    public MutableLiveData<String>nationalnum = new MutableLiveData<>();
    public MutableLiveData<String>address = new MutableLiveData<>();
    Context context;
    User user;

    public UserViewModel(Context context, User user) {
        this.context = context;
        this.user = user;
    }
}