package com.alatheer.missing.Categories;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alatheer.missing.Data.Remote.GetDataService;
import com.alatheer.missing.Data.Remote.RetrofitClientInstance;
import com.alatheer.missing.R;
import com.alatheer.missing.Utilities.Utilities;

import java.util.List;

public class CategoryActivity extends AppCompatActivity {
    @BindView(R.id.category_recycler)
    RecyclerView category_recycler;
    @BindView(R.id.radiogroup)
    RadioGroup radioGroup;
    GridLayoutManager layoutManager;
    CategoryAdapter categoryAdapter;
    List<Category> categoryList;
    RadioButton radioButton;
    String type = "1";
    int category_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);
        getCategory();
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
}
