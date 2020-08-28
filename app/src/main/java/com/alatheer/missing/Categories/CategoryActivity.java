package com.alatheer.missing.Categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alatheer.missing.Data.Remote.GetDataService;
import com.alatheer.missing.Data.Remote.RetrofitClientInstance;
import com.alatheer.missing.Helper.LocaleHelper;
import com.alatheer.missing.R;
import com.alatheer.missing.Utilities.Utilities;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    @BindView(R.id.category_recycler)
    RecyclerView category_recycler;
    @BindView(R.id.radiogroup)
    RadioGroup radioGroup;
    @BindView(R.id.missing_radio)
    RadioButton missing_radio;
    @BindView(R.id.existing_radio)
    RadioButton existing_radio;
    @BindView(R.id.txt_category)
    TextView txt_category;
    GridLayoutManager layoutManager;
    CategoryAdapter categoryAdapter;
    List<Category> categoryList;
    RadioButton radioButton;
    String type = "1";
    int category_id;
    String language;
    boolean active;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        Paper.init(this);
        language = Paper.book().read("language");
        updateview(language);
        getCategory();
        LocalBroadcastManager.getInstance(this).registerReceiver(mhandler,new IntentFilter("com.alatheer.missing_FCM-MESSAGE"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        active = false;
    }

    private void updateview(String language) {
        Context context = LocaleHelper.setLocale(this,language);
        Resources resources = context.getResources();
        txt_category.setText(resources.getString(R.string.categories));
        missing_radio.setText(resources.getString(R.string.missing));
        existing_radio.setText(resources.getString(R.string.existing));
    }

    private void getCategory() {
        if(Utilities.isNetworkAvailable(this)){
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Category>> call = service.get_categories();
            call.enqueue(new Callback<List<Category>>() {
                @Override
                public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                    if(response.isSuccessful()){
                        categoryList = response.body();
                        initrecyclerview();
                    }
                }

                @Override
                public void onFailure(Call<List<Category>> call, Throwable t) {

                }
            });
        }
    }

    private void initrecyclerview() {
        layoutManager = new GridLayoutManager(this,3);
        category_recycler.setLayoutManager(layoutManager);
        categoryAdapter = new CategoryAdapter(categoryList,this);
        category_recycler.setAdapter(categoryAdapter);
        category_recycler.setHasFixedSize(true);
    }

    public void Back(View view) {
      onBackPressed();
    }

    public void check(View view) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        if (radioId == R.id.missing_radio){
            type = "1";
        }else if(radioId == R.id.existing_radio){
            type = "2";
        }
    }

    public void SendData(Integer id) {
        if(type != null){
            Intent intent = new Intent(CategoryActivity.this,AddMissing.class);
            intent.putExtra("type",type);
            intent.putExtra("category_id",id);
            startActivity(intent);
        }else {
            Toast.makeText(this, "اختار مفقود او موجود", Toast.LENGTH_SHORT).show();
        }
    }
    private BroadcastReceiver mhandler = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra("message");
            //message.setText(msg);
            if(active == true){
                Utilities.showNotificationInADialog(CategoryActivity.this,msg);
            }
        }
    };
}
