package com.alatheer.missing.Countries;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alatheer.missing.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityHolder> {
    Context context;
    List<CityModel> cityModelList;
    CountriesActivity countriesActivity;
    int checkedPosition = -1;

    public CityAdapter(Context context, List<CityModel> cityModelList) {
        this.context = context;
        this.cityModelList = cityModelList;
        countriesActivity = (CountriesActivity) context;
    }

    @NonNull
    @Override
    public CityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_item, parent, false);
        return new CityAdapter.CityHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityHolder holder, int position) {
        holder.setData(cityModelList.get(position));
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkedPosition != holder.getAdapterPosition()) {
                    notifyItemChanged(checkedPosition);
                    checkedPosition = holder.getAdapterPosition();
                }
                countriesActivity.sendcityid(cityModelList.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityModelList.size();
    }

    class CityHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.country_name)
        TextView txt_country_name;
        @BindView(R.id.checkbox)
        RadioButton checkBox;
        public CityHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(CityModel cityModel) {
            if (checkedPosition == -1) {
                checkBox.setChecked(false);
            } else {
                if (checkedPosition == getAdapterPosition()) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }
            }
            txt_country_name.setText(cityModel.getTitle());
        }
    }
}
