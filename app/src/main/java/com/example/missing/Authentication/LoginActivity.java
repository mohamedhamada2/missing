package com.example.missing.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.missing.Categories.Category;
import com.example.missing.Categories.CategoryActivity;
import com.example.missing.Data.Local.MySharedPreference;
import com.example.missing.Data.Remote.GetDataService;
import com.example.missing.Data.Remote.Model.Authentication.User;
import com.example.missing.Data.Remote.RetrofitClientInstance;
import com.example.missing.Home.Home;
import com.example.missing.MainActivity;
import com.example.missing.R;
import com.example.missing.Utilities.Utilities;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_password)
    EditText et_password;
    String phone,password;
    MySharedPreference mprefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mprefs = MySharedPreference.getInstance();
    }

    public void Back(View view) {
        onBackPressed();
    }

    public void go_to_register(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterationActivity.class));
    }

    public void Login(View view) {
        phone = et_phone.getText().toString();
        password = et_password.getText().toString();
        if(phone.length()== 11 && password.length()>=6){
            LoginUser(phone,password);
        }
    }

    private void LoginUser(String phone, String password) {
        if(Utilities.isNetworkAvailable(LoginActivity.this)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<User> call = getDataService.login_user(phone,password);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){

                            if(response.body().getSuccess()==1){
                                mprefs.Create_Update_UserData(LoginActivity.this, response.body());
                                startActivity(new Intent(LoginActivity.this, Home.class));
                                finish();
                                Toast.makeText(LoginActivity.this, "تمت تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                            }else if(response.body().getSuccess()== 0) {
                                Toast.makeText(LoginActivity.this, "تاكد من بياناتك", Toast.LENGTH_SHORT).show();
                            }
                        }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
    }

}
