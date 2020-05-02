package com.alatheer.missing.Categories;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alatheer.missing.Countries.CountriesActivity;
import com.alatheer.missing.R;
import com.squareup.picasso.Picasso;

public class AddMissing extends AppCompatActivity {
    @BindView(R.id.et_missing_name)
    EditText et_missing_name;
    @BindView(R.id.missing_img)
    ImageView missing_img;
    @BindView(R.id.btn_choose_img)
    Button btn_choose_img;
    @BindView(R.id.btn_next_one)
    Button btn_next_one;
    private final String read_permission = Manifest.permission.READ_EXTERNAL_STORAGE;
    int IMG = 1;
    Uri filepath;
    String type;
    int category_id;
    String missing_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_missing);
        ButterKnife.bind(this);
        getDataIntent();

    }

    private void getDataIntent() {
        type = getIntent().getStringExtra("type");
        category_id = getIntent().getIntExtra("category_id",0);
    }

    @OnClick(R.id.btn_choose_img)
    public void ChooseImage(View view) {
        Check_ReadPermission(IMG);
    }

    private void Check_ReadPermission(int img) {
        if (ContextCompat.checkSelfPermission(this, read_permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{read_permission}, img);
        } else {
            select_photo(img);
        }
    }

    private void select_photo(int img) {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT);

        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setType("image/*");
        startActivityForResult(intent, img);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            filepath = data.getData();
            //profile_photo.setVisibility(View.GONE);
            Picasso.get().load(filepath).into(missing_img);
            //profile_photo.setVisibility(View.VISIBLE);
            Toast.makeText(AddMissing.this, "image added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btn_next_one)
    public void DoNext(View view) {
      Validation();
    }

    private void Validation() {
        missing_name = et_missing_name.getText().toString();
        if(!TextUtils.isEmpty(missing_name)&& filepath!=null){
            Go_to_Next(missing_name,filepath,type,category_id);
        }else {
            if(TextUtils.isEmpty(missing_name)){
                et_missing_name.setError("ادخل اسم المفقود");
            }else {
                et_missing_name.setError(null);
            }
            if(filepath==null){
                Toast.makeText(this, "من فضلك ادخل صورة المفقود", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void Go_to_Next(String missing_name, Uri filepath, String type, int category_id) {
        Intent intent = new Intent(AddMissing.this, CountriesActivity.class);
        intent.putExtra("missing_name",missing_name);
        intent.putExtra("type",type);
        intent.putExtra("category_id",category_id);
        intent.putExtra("imagepath",filepath.toString());
        startActivity(intent);
    }

    public void Back(View view) {
        onBackPressed();
    }
}
