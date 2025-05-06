package com.ities45.mealplanner.searchmeal.view;

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

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {

    private List<Meal> meals;
    private IOnMealClickListener listener;

    public MealsAdapter(List<Meal> meals, IOnMealClickListener listener) {
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_meal_item, parent, false);
        MealsAdapter.ViewHolder viewHolder = new MealsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MealsAdapter.ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.mealName.setText(meal.getStrMeal());
        Glide.with(holder.mealThumb.getContext()).load(meal.getStrMealThumb()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.mealThumb);
        holder.meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMealClick(meal);
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
            mealThumb = meal.findViewById(R.id.imgSearchMeal);
            mealName = meal.findViewById(R.id.txtSearchMealName);
        }
    }
}
