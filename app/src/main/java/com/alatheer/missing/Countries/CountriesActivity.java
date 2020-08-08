package com.alatheer.missing.Countries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alatheer.missing.Data.Local.MySharedPreference;
import com.alatheer.missing.Data.Remote.GetDataService;
import com.alatheer.missing.Data.Remote.Model.Success.Success;
import com.alatheer.missing.Data.Remote.RetrofitClientInstance;
import com.alatheer.missing.Helper.LocaleHelper;
import com.alatheer.missing.R;
import com.alatheer.missing.Utilities.Utilities;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class CountriesActivity extends AppCompatActivity {
    @BindView(R.id.all_countries_recycler)
    RecyclerView all_countries;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.btn_add)
    Button btn_add;
    @BindView(R.id.txt_govern)
    TextView txt_govern;
    List<CountryModel>countryModelList;
    List<CityModel>cityModelList1,cityModelList2,cityModelList3;
    CountriesAdapter countriesAdapter;
    RecyclerView.LayoutManager manager;
    int govern_id,city_id;
    String missing_name,missing_image,type,language;
    int category_id;
    Uri imagepath;
    MySharedPreference mySharedPreference;
    int user_id;
    Context context;
    Resources resources;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        ButterKnife.bind(this);
        Paper.init(this);
        language = Paper.book().read("language");
        updateview(language);
        mySharedPreference = MySharedPreference.getInstance();
        user_id = mySharedPreference.Get_UserData(this).getId();
        getDataIntent();
        get_governs();
    }

    private void updateview(String language) {
        context = LocaleHelper.setLocale(this,language);
        resources = context.getResources();
        txt_govern.setText(resources.getString(R.string.choosegovern));
        btn_add.setText(resources.getString(R.string.add));
    }

    private void getDataIntent() {
        type = getIntent().getStringExtra("type");
        category_id = getIntent().getIntExtra("category_id",0);
        missing_name = getIntent().getStringExtra("missing_name");
        missing_image = getIntent().getStringExtra("imagepath");
        imagepath = Uri.parse(missing_image);
    }

    private void get_governs() {
        if(Utilities.isNetworkAvailable(this)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<List<CountryModel>> call = getDataService.get_governs();
            call.enqueue(new Callback<List<CountryModel>>() {
                @Override
                public void onResponse(Call<List<CountryModel>> call, Response<List<CountryModel>> response) {
                    if(response.isSuccessful()){
                        countryModelList = response.body();
                        initRecycler();
                    }
                }

                @Override
                public void onFailure(Call<List<CountryModel>> call, Throwable t) {

                }
            });

        }
    }

    private void initRecycler() {
        countriesAdapter = new CountriesAdapter(countryModelList,this);
        all_countries.setAdapter(countriesAdapter);
        manager = new LinearLayoutManager(this);
        all_countries.setLayoutManager(manager);
        all_countries.setHasFixedSize(true);
    }


    public void Back(View view) {
      onBackPressed();
    }

    public void sendgovernid(int id) {
        govern_id = id;
    }

    public void sendcityid(Integer id) {
        city_id = id;
    }
    @OnClick(R.id.btn_add)
    public void AddMissingItem(View view) {
        Validation();
    }

    private void Validation() {
        if(govern_id != 0 && city_id != 0 && imagepath != null && missing_name != null){
            AddItem(user_id,type,missing_name,category_id,govern_id,city_id,imagepath);
            Log.e("user_id",user_id+"");
            Log.e("user_phone",mySharedPreference.Get_UserData(this).getPhone());
        }
    }

    private void AddItem(int user_id, String type, String missing_name, int category_id, int govern_id, int city_id, Uri imagepath) {
        progressBar.setVisibility(View.VISIBLE);
        RequestBody rb_user_id = Utilities.getRequestBodyText(user_id+"");
        RequestBody rb_type = Utilities.getRequestBodyText(type);
        RequestBody rb_category_id = Utilities.getRequestBodyText(category_id+ "");
        RequestBody rb_missing_name = Utilities.getRequestBodyText(missing_name);
        RequestBody rb_govern_id = Utilities.getRequestBodyText(govern_id+"");
        RequestBody rb_city_id= Utilities.getRequestBodyText(city_id+"");
        MultipartBody.Part img = Utilities.getMultiPart(this, imagepath, "img");
        if(Utilities.isNetworkAvailable(this)){
            GetDataService getDataService = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<Success> call = getDataService.add_item(rb_user_id,rb_type,rb_missing_name,rb_category_id,rb_govern_id,rb_city_id,img);
            call.enqueue(new Callback<Success>() {
                @Override
                public void onResponse(Call<Success> call, Response<Success> response) {
                    if(response.isSuccessful()){
                        if(response.body().getSuccess()==1){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(CountriesActivity.this, "تمت الاضافة بنجاح", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Success> call, Throwable t) {

                }
            });
        }

    }
}
