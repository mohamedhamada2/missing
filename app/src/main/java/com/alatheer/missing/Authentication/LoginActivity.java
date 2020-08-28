package com.alatheer.missing.Authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.txt_login)
    TextView txt_login;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.txt_register)
    TextView txt_register;
    @BindView(R.id.txt_forget_password)
    TextView txt_forget_password;
    String language;
    String phone,password;
    MySharedPreference mprefs;
    Context context;
    Resources resources;
    String firebase_token;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase,"ar"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Paper.init(this);
        mprefs = MySharedPreference.getInstance();
        language = Paper.book().read("language");
        if(language == null){
            Paper.book().write("language","ar");
        }
        updateview(language);
        try {
            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if(task.isSuccessful()){
                        firebase_token = task.getResult().getToken();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateview(String language) {
        context = LocaleHelper.setLocale(this,language);
        resources = context.getResources();
        et_phone.setHint(resources.getString(R.string.phone));
        et_password.setHint(resources.getString(R.string.password));
        txt_login.setText(resources.getString(R.string.login));
        btn_login.setText(resources.getString(R.string.login));
        txt_register.setText(resources.getString(R.string.register_new_account));
        txt_forget_password.setText(resources.getString(R.string.forgetpassword));

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
        if (!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)) {
            LoginUser(phone, password);
        } else {
            if (TextUtils.isEmpty(phone)) {
                et_phone.setError(resources.getString(R.string.validate_phone_add));
            } else {
                et_phone.setError(null);
            }
            if (TextUtils.isEmpty(password)) {
                et_password.setError(resources.getString(R.string.validate_password_add));
            } else {
                et_password.setError(null);
            }
        }
    }

    private void LoginUser(String phone, String password) {
        if(Utilities.isNetworkAvailable(LoginActivity.this)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Log.e("hello",phone);
            Log.e("hello",firebase_token);
            Call<User> call = getDataService.login_user(phone,password,firebase_token);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if(response.isSuccessful()){

                            if(response.body().getSuccess()==1&& response.body().getActivate()==0) {
                               Utilities.CreateAlertDialog(LoginActivity.this,response.body().getPhone(),2);
                            }else if(response.body().getSuccess()==1 && response.body().getActivate()==1){
                                mprefs.Create_Update_UserData(LoginActivity.this, response.body());
                                startActivity(new Intent(LoginActivity.this, Home.class));
                                finish();
                                Toast.makeText(LoginActivity.this, resources.getString(R.string.login_successfully), Toast.LENGTH_SHORT).show();
                            }else if(response.body().getSuccess()== 0) {
                                Toast.makeText(LoginActivity.this, resources.getString(R.string.check_your_data), Toast.LENGTH_SHORT).show();
                            }
                        }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
    }

    public void showpopup(View view) {
        PopupMenu menu = new PopupMenu(this, view);
        menu.setOnMenuItemClickListener(this);
        menu.inflate(R.menu.language_menu);
        menu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ar_language :
                Paper.book().write("language","ar");
                updateview(Paper.book().read("language"));
                Toast.makeText(this, "ar-eEG", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.en_language :
                Paper.book().write("language","en");
                updateview(Paper.book().read("language"));
                Toast.makeText(this, "en", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.fr_language :
                Paper.book().write("language","fr");
                updateview(Paper.book().read("language"));
                Toast.makeText(this, "fr", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.sp_language :
                Paper.book().write("language","gl");
                updateview(Paper.book().read("language"));
                Toast.makeText(this, "gl-rES", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ch_language :
                Paper.book().write("language","zh");
                updateview(Paper.book().read("language"));
                Toast.makeText(this, "zh-rCN", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.ur_language:
                Paper.book().write("language","ur");
                updateview(Paper.book().read("language"));
                return true;
        }
        return true;
    }

    public void go_to_forgetPassword(View view) {
        startActivity(new Intent(this,ForgetPasswordActivity.class));
    }
}
