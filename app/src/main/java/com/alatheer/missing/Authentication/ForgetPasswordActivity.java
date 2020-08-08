package com.alatheer.missing.Authentication;

import androidx.appcompat.app.AlertDialog;
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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ForgetPasswordActivity extends AppCompatActivity {
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.btn_forget_password)
    Button btn_forget_password;
    @BindView(R.id.txt_forget_password)
    TextView txt_forget_password;
    String email, language;
    Context context;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activate_code);
        ButterKnife.bind(this);
        Paper.init(this);
        language = Paper.book().read("language");
        updateview(language);
    }

    private void updateview(String language) {
        context = LocaleHelper.setLocale(this, language);
        resources = context.getResources();
        btn_forget_password.setText(resources.getString(R.string.sendcode));
        et_email.setHint(resources.getString(R.string.email));
        txt_forget_password.setText(resources.getString(R.string.forgetpassword));
    }

    public void send_code(View view) {
        Validation();
    }

    private void Validation() {
        email = et_email.getText().toString();

        if (!TextUtils.isEmpty(email)) {
            SendCode(email);
        } else {
            if (TextUtils.isEmpty(email)) {
                et_email.setError(resources.getString(R.string.validate_email));
            } else {
                et_email.setError(null);
            }
        }
    }

    private void SendCode(String email) {
        GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<User> call = getDataService.send_code(email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    if (response.body().getSuccess() == 1) {
                        Toast.makeText(ForgetPasswordActivity.this, "code send successfully", Toast.LENGTH_SHORT).show();
                        CreatealertDialog(email);
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    private void CreatealertDialog(String email) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.reset_password_item, null);
        EditText et_code = view.findViewById(R.id.et_code);
        EditText et_password = view.findViewById(R.id.et_new_password);
        //txt_code.setText(code);
        Button btn_ok = view.findViewById(R.id.btn_ok);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = et_code.getText().toString();
                String password = et_password.getText().toString();
                MySharedPreference mprefs = MySharedPreference.getInstance();
                if (!TextUtils.isEmpty(code)) {
                    GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                    Call<User> call = getDataService.reset_password(email, code,password);
                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getSuccess() == 1) {
                                    Log.e("success1", "1");
                                    //Toast.makeText(context, "تمت تسجيل الدخول بنجاح", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ForgetPasswordActivity.this,LoginActivity.class));
                                    dialog.dismiss();
                                } else {
                                    et_code.setError("الكود غير صحيح");
                                    //dialog.dismiss();
                                }
                            } else {
                                et_code.setError("الكود غير صحيح");
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.e("failure", t.getMessage());
                        }
                    });
                } else {
                    et_code.setError("برجاء ادخال الكود الخاص بك");
                }

            }
        });
        dialog.show();
        dialog.getWindow().setLayout(750, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void Back(View view) {
        onBackPressed();
    }
}
