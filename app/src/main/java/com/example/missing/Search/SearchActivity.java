package com.example.missing.Search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.missing.Categories.Category;
import com.example.missing.Countries.CountryModel;
import com.example.missing.Data.Remote.GetDataService;
import com.example.missing.Data.Remote.RetrofitClientInstance;
import com.example.missing.R;
import com.example.missing.Utilities.Utilities;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.category_recycler)
    RecyclerView category_recycler;
    @BindView(R.id.spinner_govern_type)
    Spinner spinner_govern_type;
    @BindView(R.id.spinner_city_type)
    Spinner spinner_city_type;
    @BindView(R.id.items_recycler)
    RecyclerView items_recycler;
    CategoryAdapter2 categoryAdapter2;
    RecyclerView.LayoutManager layoutManager;
    List<Category> categoryList;
    List<CountryModel>countryModelList;
    List<String> countrynamesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        countrynamesList = new ArrayList<>();
        getCategories();
        getgoverns();
        getcities();
    }
    private void getgoverns() {
        GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<CountryModel>> call = getDataService.get_governs();
        call.enqueue(new Callback<List<CountryModel>>() {
            @Override
            public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
                if(response.isSuccessful()){
                    countryModelList = response.body();
                    for(CountryModel countryModel : countryModelList){
                       countrynamesList.add(countryModel.getTitle());
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(SearchActivity.this,R.layout.spinner_item,countrynamesList);
                    spinner_govern_type.setAdapter(arrayAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<CountryModel>> call, Throwable t) {

            }
        });
    }
    private void getcities() {

    }

    private void getCategories() {
        if(Utilities.isNetworkAvailable(this)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<List<Category>> call = getDataService.get_categories();
            call.enqueue(new Callback<List<Category>>() {
                @Override
                public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                    if(response.isSuccessful()){
                     categoryList = response.body();
                     initrecycler();
                    }
                }

                @Override
                public void onFailure(Call<List<Category>> call, Throwable t) {

                }
            });
        }
    }

    private void initrecycler() {
        category_recycler.setHasFixedSize(true);
        categoryAdapter2 = new CategoryAdapter2(categoryList,this);
        category_recycler.setAdapter(categoryAdapter2);
        layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        category_recycler.setLayoutManager(layoutManager);
    }

    public void Back(View view) {
        onBackPressed();
    }
    @OnClick(R.id.btn_missing)
    public void GetMissingItems(View view) {
    }
    @OnClick(R.id.btn_existing)
    public void GetExistingItems(View view) {
    }

}
