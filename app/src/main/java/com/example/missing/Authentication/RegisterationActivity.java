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
import com.example.missing.MainActivity;
import com.example.missing.R;
import com.example.missing.Utilities.Utilities;

public class RegisterationActivity extends AppCompatActivity {
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_user_national_num)
    EditText et_user_national_num;
    @BindView(R.id.et_user_phone)
    EditText et_user_phone;
    @BindView(R.id.et_user_address)
    EditText et_user_address;
    String user_name,user_password,user_national_num,user_phone,user_address;
    MySharedPreference mprefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        ButterKnife.bind(this);
        mprefs = MySharedPreference.getInstance();
    }

    public void Back(View view) {
        onBackPressed();
    }

    public void Register(View view) {
        user_name = et_username.getText().toString();
        user_password = et_password.getText().toString();
        user_national_num = et_user_national_num.getText().toString();
        user_phone = et_user_phone.getText().toString();
        user_address = et_user_address.getText().toString();
        if(!TextUtils.isEmpty(user_name)&& user_password.length()>=6
        &&!TextUtils.isEmpty(user_national_num)&& user_phone.length() == 11
        &&!TextUtils.isEmpty(user_address)){
            RegisterUser(user_name,user_password,user_national_num,user_phone,user_address);
        }else {
            if(TextUtils.isEmpty(user_name)){
                et_username.setError(getResources().getString(R.string.validate_name));
            }else {
                et_username.setError(null);
            }
            if(TextUtils.isEmpty(user_national_num)){
                et_user_national_num.setError(getResources().getString(R.string.validate_national_num));
            }else {
                et_user_national_num.setError(null);
            }
            if(TextUtils.isEmpty(user_address)){
                et_user_address.setError(getResources().getString(R.string.validate_address));
            }else {
                et_user_address.setError(null);
            }
            if(user_password.length()<6){
                et_password.setError(getResources().getString(R.string.validate_password));
            }else {
                et_password.setError(null);
            }
            if(user_phone.length() != 11){
                et_user_phone.setError(getResources().getString(R.string.validate_phone));
            }else {
                et_password.setError(null);
            }
        }
    }

    private void RegisterUser(String user_name, String user_password, String user_national_num, String user_phone, String user_address) {
        if(Utilities.isNetworkAvailable(RegisterationActivity.this)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<User> call = getDataService.register_user(user_name,user_password,user_national_num,user_phone,user_address);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        if(response.body().getSuccess()==1){
                            mprefs.Create_Update_UserData(RegisterationActivity.this, response.body());
                            startActivity(new Intent(RegisterationActivity.this, CategoryActivity.class));
                            finish();
                            Toast.makeText(RegisterationActivity.this, "تمت تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RegisterationActivity.this, "لقد تم دخولك مسبقا", Toast.LENGTH_SHORT).show();
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
