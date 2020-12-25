package com.alatheer.missing.Search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alatheer.missing.Data.Remote.Model.Items.Item;
import com.alatheer.missing.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
       holder.img_share.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent shareIntent = new Intent(Intent.ACTION_SEND);
               shareIntent.setType("text/plain");
               shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Missing");
               String shareMessage= "\nLet me recommend you this application\n\n";
               shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=com.alatheer.missing";
               shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
               context.startActivity(Intent.createChooser(shareIntent, "choose one"));
           }
       });
       holder.category_img.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               CreateCartAlertDialog(itemList.get(position));
           }
       });
    }

    private void CreateCartAlertDialog(Item item) {
        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .create();

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_image, null);
        FrameLayout fl_cancel = view.findViewById(R.id.fl_cancel);

        PhotoView image = view.findViewById(R.id.image);
        Picasso.get().load("https://mymissing.online/uploads/images/"+item.getImg()).into(image);
        fl_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(view);
        dialog.show();
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
        @BindView(R.id.txt_loc_details)
        TextView txt_loc_details;
        @BindView(R.id.img_share)
        ImageView img_share;
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
            txt_loc_details.setText(item.getAdress());
        }
    }

}
