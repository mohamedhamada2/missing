package com.example.missing.Countries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;
import android.view.View;

import com.example.missing.R;

import java.util.ArrayList;
import java.util.List;

public class CountriesActivity extends AppCompatActivity {
    @BindView(R.id.all_countries_recycler)
    RecyclerView all_countries;
    List<CountryModel>countryModelList;
    List<CityModel>cityModelList1,cityModelList2,cityModelList3;
    CountriesAdapter countriesAdapter;
    RecyclerView.LayoutManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countries);
        countryModelList = new ArrayList<>();
        cityModelList1 = new ArrayList<>();
        cityModelList2 = new ArrayList<>();
        cityModelList3 = new ArrayList<>();
        ButterKnife.bind(this);
        initRecycler();
    }

    private void initRecycler() {
        CityModel cityModel0 = new CityModel("الرياض");
        CityModel cityModel1 = new CityModel("جدة");
        CityModel cityModel2 = new CityModel("القصيم");
        CityModel cityModel3 = new CityModel("مكة");
        CityModel cityModel4 = new CityModel("الدمام");
        cityModelList1.add(cityModel0);
        cityModelList1.add(cityModel1);
        cityModelList1.add(cityModel2);
        cityModelList1.add(cityModel3);
        cityModelList1.add(cityModel4);
        CityModel cityModel5 = new CityModel("القاهرة");
        CityModel cityModel6 = new CityModel("الجيزة");
        CityModel cityModel7 = new CityModel("الاسكندرية");
        cityModelList2.add(cityModel5);
        cityModelList2.add(cityModel6);
        cityModelList2.add(cityModel7);
        CountryModel countryModel1 = new CountryModel("السعودية",cityModelList1);
        countryModelList.add(countryModel1);
        CountryModel countryModel2 = new CountryModel("مصر",cityModelList2);
        countryModelList.add(countryModel2);
        countriesAdapter = new CountriesAdapter(countryModelList,this);
        all_countries.setAdapter(countriesAdapter);
        manager = new LinearLayoutManager(this);
        all_countries.setLayoutManager(manager);
        all_countries.setHasFixedSize(true);
    }


    public void Back(View view) {

    }
}
