package com.ities45.mealplanner.favoritemeal.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ities45.mealplanner.R;
import com.ities45.mealplanner.model.pojo.Meal;

import java.util.List;

public class FavoriteMealsAdapter extends RecyclerView.Adapter<FavoriteMealsAdapter.ViewHolder> {
    private List<Meal> meals;
    private IOnFavoriteMealClickListener listener;

    public FavoriteMealsAdapter(List<Meal> meals, IOnFavoriteMealClickListener listener) {
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavoriteMealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.favorite_meal_item, parent, false);
        FavoriteMealsAdapter.ViewHolder viewHolder = new FavoriteMealsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMealsAdapter.ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.mealName.setText(meal.getStrMeal());
        Glide.with(holder.mealThumb.getContext()).load(meal.getStrMealThumb()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.mealThumb);
        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFavMealRemoveBtnClick(meal);
            }
        });
        holder.favMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFavMealCLick(meal);
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
        public Button removeBtn;
        public View favMeal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favMeal = itemView;
            mealThumb = favMeal.findViewById(R.id.iv_meal_thumbnail);
            mealName = favMeal.findViewById(R.id.tv_meal_name);
            removeBtn = favMeal.findViewById(R.id.btn_remove_favorite);
        }
    }
}
