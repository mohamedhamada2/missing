package com.alatheer.missing.Countries;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alatheer.missing.Data.Remote.GetDataService;
import com.alatheer.missing.Data.Remote.RetrofitClientInstance;
import com.alatheer.missing.R;
import com.alatheer.missing.Utilities.Utilities;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesHolder> {
    List<CountryModel>countryModelList;
    Context context;
    CountriesActivity countriesActivity;
    List<CityModel>citylist;
    RecyclerView city_recycler;
    Button btn_send,btn_cancel;
    int checkedPosition = -1;
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
             if (checkedPosition != holder.getAdapterPosition()) {
                 notifyItemChanged(checkedPosition);
                 checkedPosition = holder.getAdapterPosition();
                 CreateAlertDialog(countryModelList.get(position).getId());
             }
         }
     });

     }


    private void CreateAlertDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final View view = countriesActivity.getLayoutInflater().inflate(R.layout.city_item, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getWindow().setLayout(750, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        city_recycler = view.findViewById(R.id.city_recycler);
        btn_send = view.findViewById(R.id.btn_send);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        if(Utilities.isNetworkAvailable(context)){
            GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
            Call<List<CityModel>> call = service.get_cities(id);
            call.enqueue(new Callback<List<CityModel>>() {
                @Override
                public void onResponse(Call<List<CityModel>> call, Response<List<CityModel>> response) {
                    if(response.isSuccessful()){
                        citylist  = response.body();
                        initrecycler();
                    }
                }

                @Override
                public void onFailure(Call<List<CityModel>> call, Throwable t) {

                }
            });
        }
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData(id);
                dialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.dismiss();
            }
        });

    }

    private void sendData(int id) {
        countriesActivity.sendgovernid(id);
    }

    private void initrecycler() {
        CityAdapter cityAdapter = new CityAdapter(context,citylist);
        city_recycler.setAdapter(cityAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
        city_recycler.setLayoutManager(manager);
        city_recycler.setHasFixedSize(true);
    }


    @Override
    public int getItemCount() {
        return countryModelList.size();
    }

    class CountriesHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.country_name)
        TextView txt_country_name;
        @BindView(R.id.checkbox)
        RadioButton checkBox;
        public CountriesHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

        public void setData(CountryModel countryModel) {
            if (checkedPosition == -1) {
                checkBox.setChecked(false);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
            }
            txt_country_name.setText(countryModel.getTitle());
        }
    }
}
