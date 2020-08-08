package com.alatheer.missing.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alatheer.missing.Data.Remote.Model.Items.Item;
import com.alatheer.missing.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemsHolder> {
    List<Item>itemList;
    Context context;
    SearchActivity searchActivity;
    public ItemsAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
        searchActivity = (SearchActivity) context;
    }
    @NonNull
    @Override
    public ItemsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_raw,parent,false);
        return new ItemsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsHolder holder, int position) {
       holder.setData(itemList.get(position));
       holder.txt_add_comment.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               searchActivity.add_comment(itemList.get(position).getId(),itemList.get(position).getType(),itemList.get(position).getUserId()
               ,itemList.get(position).getImg(),itemList.get(position).getName(),itemList.get(position).getCityTitle(),itemList.get(position).getGovernTitle(),itemList.get(position).getCreatedAt());
           }
       });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ItemsHolder extends  RecyclerView.ViewHolder{
        @BindView(R.id.category_name)
        TextView category_name;
        @BindView(R.id.category_img)
        ImageView category_img;
        @BindView(R.id.location_name)
        TextView location_name;
        @BindView(R.id.missing_date)
        TextView missing_date;
        @BindView(R.id.user_name)
        TextView user_name;
        @BindView(R.id.txt_add_comment)
        TextView txt_add_comment;
        public ItemsHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(Item item) {
            category_name.setText(item.getName());
            Picasso.get().load("https://mymissing.online/uploads/images/"+item.getImg()).into(category_img);
            location_name.setText(item.getCityTitle()+"-"+item.getGovernTitle());
            missing_date.setText(item.getCreatedAt());
            user_name.setText(item.getUName());
        }
    }

}
