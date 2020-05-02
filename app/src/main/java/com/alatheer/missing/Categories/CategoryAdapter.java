package com.alatheer.missing.Categories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alatheer.missing.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    List<Category>categoryList;
    Context context;
    CategoryActivity categoryActivity;
    public CategoryAdapter(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
        categoryActivity = (CategoryActivity) context;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item,parent,false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
     holder.setData(categoryList.get(position));
     holder.itemView.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             categoryActivity.SendData(categoryList.get(position).getId());
         }
     });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class  CategoryHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.category_img)
        ImageView category_img;
        @BindView(R.id.category_txt)
        TextView category_txt;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        public void setData(Category category) {
            category_txt.setText(category.getName());
            Picasso.get().load("http://missing2.menustations.com/uploads/images/"+category.getImg()).into(category_img);
        }
    }
}
