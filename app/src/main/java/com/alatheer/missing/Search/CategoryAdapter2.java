package com.alatheer.missing.Search;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alatheer.missing.Categories.Category;
import com.alatheer.missing.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter2 extends RecyclerView.Adapter<CategoryAdapter2.CategoryHolder> {
    List<Category> categoryList;
    Context context;
    SearchActivity searchActivity;
    private int selectedItem;
    public CategoryAdapter2(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
        searchActivity = (SearchActivity) context;
        selectedItem = 0;
    }
    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item_search,parent,false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        holder.setData(categoryList.get(position));
        holder.parent.setBackgroundResource(R.drawable.category_item_background);
        if (selectedItem == position) {
            holder.parent.setBackgroundResource(R.drawable.cateory_item_background2);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int previousItem = selectedItem;
                selectedItem = position;
                notifyItemChanged(previousItem);
                notifyItemChanged(position);
                searchActivity.sendData(categoryList.get(position).getId());


            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder{
        //@BindView(R.id.parent)
        RelativeLayout parent;
        //@BindView(R.id.category_img)
        ImageView category_img;
        //@BindView(R.id.category_name)
        TextView category_name;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            category_img = itemView.findViewById(R.id.category_img);
            category_name = itemView.findViewById(R.id.category_name);
            // ButterKnife.bind(this,itemView);
        }

        public void setData(Category category) {
            category_name.setText(category.getName());
            Picasso.get().load("https://mymissing.online/uploads/images/"+category.getImg()).into(category_img);
        }
    }
}
