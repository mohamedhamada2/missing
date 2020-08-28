package com.alatheer.missing.Authentication;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alatheer.missing.Data.Local.MySharedPreference;
import com.alatheer.missing.Data.Remote.GetDataService;
import com.alatheer.missing.Data.Remote.Model.Authentication.User;
import com.alatheer.missing.Data.Remote.RetrofitClientInstance;
import com.alatheer.missing.Helper.LocaleHelper;
import com.alatheer.missing.Home.Home;
import com.alatheer.missing.R;
import com.alatheer.missing.Utilities.Utilities;

public class RegisterationActivity extends AppCompatActivity {
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_user_phone)
    EditText et_user_phone;
    @BindView(R.id.et_user_address)
    EditText et_user_address;
    @BindView(R.id.txt_register)
    TextView txt_register;
    @BindView(R.id.btn_register)
    Button btn_register;
    String user_name,user_password,user_email,user_phone,user_address;
    MySharedPreference mprefs;
    String language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        ButterKnife.bind(this);
        Paper.init(this);
        mprefs = MySharedPreference.getInstance();
        language = Paper.book().read("language");
        updateview(language);
    }

    private void updateview(String language) {
        Context context = LocaleHelper.setLocale(this,language);
        Resources resources = context.getResources();
        et_username.setHint(resources.getString(R.string.username));
        et_password.setHint(resources.getString(R.string.password));
        et_email.setHint(resources.getString(R.string.email));
        et_user_phone.setHint(resources.getString(R.string.phone));
        et_user_address.setHint(resources.getString(R.string.confirm_password));
        txt_register.setText(resources.getString(R.string.register));
        btn_register.setText(resources.getString(R.string.register));
    }
    public void Back(View view) {
        onBackPressed();
    }

    public void Register(View view) {
        Context context = LocaleHelper.setLocale(this,language);
        Resources resources = context.getResources();
        user_name = et_username.getText().toString();
        user_password = et_password.getText().toString();
        user_email = et_email.getText().toString();
        user_phone = et_user_phone.getText().toString();
        user_address = et_user_address.getText().toString();
        if(!TextUtils.isEmpty(user_name)&& user_password.length()>=6
        &&!TextUtils.isEmpty(user_email)&& user_phone.length() == 10
        &&user_address.equals(user_password)){
            RegisterUser(user_name,user_password,user_email,user_phone,user_address);
        }else {
            if(TextUtils.isEmpty(user_name)){
                et_username.setError(resources.getString(R.string.validate_name));
            }else {
                et_username.setError(null);
            }
            if(TextUtils.isEmpty(user_email)){
                et_email.setError(resources.getString(R.string.validate_email));
            }else {
                et_email.setError(null);
            }
            if(!user_address.equals(user_password)){
                et_user_address.setError(resources.getString(R.string.validate_address));
            }else {
                et_user_address.setError(null);
            }
            if(user_password.length()<6){
                et_password.setError(resources.getString(R.string.validate_password_weak));
            }else {
                et_password.setError(null);
            }
            if(user_phone.length() != 10){
                et_user_phone.setError(resources.getString(R.string.validate_phone_incorrect));
            }else {
                et_user_phone.setError(null);
            }
        }
    }

    private void RegisterUser(String user_name, String user_password, String user_email, String user_phone, String user_address) {
        Log.e("lllll","mmmmm");
        if(Utilities.isNetworkAvailable(RegisterationActivity.this)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<User> call = getDataService.register_user(user_name,user_password,user_email,user_phone);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){
                        Log.e("ssss","mmmmm");
                        if(response.body().getSuccess()==1 && response.body().getActivate()== 0){
                            Utilities.CreateAlertDialog(RegisterationActivity.this,response.body().getPhone(),1);
                            /*mprefs.Create_Update_UserData(RegisterationActivity.this, response.body());
                             startActivity(new Intent(RegisterationActivity.this, Home.class));
                            finish();
                            Toast.makeText(RegisterationActivity.this, "تمت تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();*/
                        }else if(response.body().getSuccess()== 0) {
                            Log.e("hello","0000");
                            Toast.makeText(RegisterationActivity.this, "لقد تم دخولك مسبقا", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Log.e("response",response.message()+"");
                        Toast.makeText(RegisterationActivity.this, "لقد تم دخولك مسبقا", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e("hello",t.getMessage());
                }
            });
        }


    }

}
