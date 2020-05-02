package com.alatheer.missing.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.alatheer.missing.Data.Local.MySharedPreference;
import com.alatheer.missing.Data.Remote.Model.Authentication.User;
import com.alatheer.missing.Home.Home;
import com.alatheer.missing.R;

public class Splash extends AppCompatActivity {
    MySharedPreference mprefs;
    User user;
    int SPLASH_DISPLAY_LENGTH = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mprefs = MySharedPreference.getInstance();
        user = mprefs.Get_UserData(this);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                //Create an Intent that will start the Menu-Activity.
                if(user != null){
                    Intent mainIntent = new Intent(Splash.this, Home.class);
                    startActivity(mainIntent);
                    finish();
                }else {
                    Intent mainIntent = new Intent(Splash.this,LoginActivity.class);
                    startActivity(mainIntent);
                    finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
