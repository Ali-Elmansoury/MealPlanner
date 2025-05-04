package com.ities45.mealplanner.itemdescription.view;

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
import com.ities45.mealplanner.model.pojo.IngredientMeasureItem;

import java.util.List;

public class ItemIngredientsAdapter extends RecyclerView.Adapter<ItemIngredientsAdapter.ViewHolder> {
    private final List<IngredientMeasureItem> items;

    public ItemIngredientsAdapter(List<IngredientMeasureItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemIngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredient_item, parent, false);
        ItemIngredientsAdapter.ViewHolder viewHolder = new ItemIngredientsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemIngredientsAdapter.ViewHolder holder, int position) {
        IngredientMeasureItem item = items.get(position);
        holder.ingredientName.setText(item.getName());
        holder.ingredientMeasure.setText(item.getMeasure());
        Glide.with(holder.ingredientThumb.getContext())
                .load(item.getThumbnailUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ingredientThumb);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ingredientThumb;
        public TextView ingredientName;
        public TextView ingredientMeasure;
        public View ingredient;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredient = itemView;
            ingredientName = ingredient.findViewById(R.id.ingredientNameTextView);
            ingredientMeasure = ingredient.findViewById(R.id.measureTextView);
            ingredientThumb = ingredient.findViewById(R.id.ingredientImageView);
        }
    }
}
