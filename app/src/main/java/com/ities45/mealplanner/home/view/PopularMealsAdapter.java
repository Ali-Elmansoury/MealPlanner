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
import com.ities45.mealplanner.model.pojo.Meal;

import java.util.List;

public class PopularMealsAdapter extends RecyclerView.Adapter<PopularMealsAdapter.ViewHolder> {
    private List<Meal> meals;
    private IOnPopularMealClickListener listener;

    public PopularMealsAdapter(List<Meal> meals, IOnPopularMealClickListener listener) {
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PopularMealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.meal_item, parent, false);
        PopularMealsAdapter.ViewHolder viewHolder = new PopularMealsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PopularMealsAdapter.ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.mealName.setText(meals.get(position).getStrMeal());
        Glide.with(holder.mealThumb.getContext())
                .load(meals.get(position).getStrMealThumb())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.mealThumb);
        holder.meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPopularMealClick(meal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mealThumb;
        public TextView mealName;
        public View meal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            meal = itemView;
            mealThumb = meal.findViewById(R.id.imgMeal);
            mealName = meal.findViewById(R.id.txtMealName);
        }
    }
}
