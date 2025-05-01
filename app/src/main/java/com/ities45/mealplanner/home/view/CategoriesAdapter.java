package com.ities45.mealplanner.home.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ities45.mealplanner.R;
import com.ities45.mealplanner.model.pojo.Category;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private List<Category> categories;
    private IOnCategoryClickListener listener;

    public CategoriesAdapter(List<Category> categories, IOnCategoryClickListener listener) {
        this.categories = categories;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category_item, parent, false);
        CategoriesAdapter.ViewHolder viewHolder = new CategoriesAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesAdapter.ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryName.setText(categories.get(position).getStrCategory());
        Glide.with(holder.categoryThumb.getContext())
                .load(categories.get(position).getStrCategoryThumb())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.categoryThumb);
        holder.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCategoryClick(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView categoryThumb;
        public TextView categoryName;
        public View category;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView;
            categoryName = category.findViewById(R.id.txtCategoryName);
            categoryThumb = category.findViewById(R.id.imgCategory);
        }
    }
}
