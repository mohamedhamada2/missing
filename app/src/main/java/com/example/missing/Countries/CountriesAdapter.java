package com.example.missing.Countries;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.missing.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesHolder> {
    List<CountryModel>countryModelList;
    Context context;
    CountriesActivity countriesActivity;

    public CountriesAdapter(List<CountryModel> countryModelList, Context context) {
        this.countryModelList = countryModelList;
        this.context = context;
        countriesActivity = (CountriesActivity) context;
    }

    @NonNull
    @Override
    public CountriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_item,parent,false);
        return new CountriesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesHolder holder, final int position) {
     holder.setData(countryModelList.get(position));
     holder.checkBox.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             CreateAlertDialog(countryModelList.get(position).getCityList());
         }
     });

     }


    private void CreateAlertDialog(List<CityModel> cityList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = countriesActivity.getLayoutInflater().inflate(R.layout.city_item, null);
        RecyclerView city_recycler = view.findViewById(R.id.city_recycler);
        CityAdapter cityAdapter = new CityAdapter(context,cityList);
        city_recycler.setAdapter(cityAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        city_recycler.setLayoutManager(manager);
        city_recycler.setHasFixedSize(true);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setLayout(750, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public int getItemCount() {
        return countryModelList.size();
    }

    class CountriesHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.country_name)
        TextView txt_country_name;
        @BindView(R.id.checkbox)
        CheckBox checkBox;
        public CountriesHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

        public void setData(CountryModel countryModel) {
            txt_country_name.setText(countryModel.getName());
        }
    }
}
