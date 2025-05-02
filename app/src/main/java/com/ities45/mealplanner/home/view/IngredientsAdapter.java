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
import com.ities45.mealplanner.model.pojo.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private List<Ingredient> ingredients;
    private IOnIngredientClickListener listener;

    public IngredientsAdapter(List<Ingredient> ingredients, IOnIngredientClickListener listener) {
        this.ingredients = ingredients;
        this.listener = listener;
    }

    @NonNull
    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredient_home_item, parent, false);
        IngredientsAdapter.ViewHolder viewHolder = new IngredientsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.ingredientName.setText(ingredients.get(position).getStrIngredient());
        Glide.with(holder.ingredientThumb.getContext())
                .load(ingredient.getIngredientUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ingredientThumb);
        holder.ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onIngredientClick(ingredient);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ingredientThumb;
        public TextView ingredientName;
        public View ingredient;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredient = itemView;
            ingredientName = ingredient.findViewById(R.id.txtIngredientName);
            ingredientThumb = ingredient.findViewById(R.id.imgIngredient);
        }
    }
}
