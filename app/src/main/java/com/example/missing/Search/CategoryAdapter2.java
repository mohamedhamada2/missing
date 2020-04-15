package com.example.missing.Search;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.missing.Categories.Category;
import com.example.missing.Categories.CategoryActivity;
import com.example.missing.Categories.CategoryAdapter;
import com.example.missing.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter2 extends RecyclerView.Adapter<CategoryAdapter2.CategoryHolder> {
    List<Category> categoryList;
    Context context;
    CategoryActivity categoryActivity;
    public CategoryAdapter2(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }
    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item_search,parent,false);
        return new CategoryAdapter2.CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        holder.setData(categoryList.get(position));
        holder.category_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.category_img.setBackgroundColor(Color.parseColor("#C0C0C0"));
                holder.category_name.setTextColor(Color.parseColor("#C0C0C0"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.category_img)
        ImageView category_img;
        @BindView(R.id.category_name)
        TextView category_name;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(Category category) {
            category_name.setText(category.getName());
            Picasso.get().load("http://missing2.menustations.com/uploads/images/"+category.getImg()).into(category_img);
        }
    }
}
