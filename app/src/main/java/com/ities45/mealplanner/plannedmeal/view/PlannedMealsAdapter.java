package com.ities45.mealplanner.plannedmeal.view;

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

public class PlannedMealsAdapter extends RecyclerView.Adapter<PlannedMealsAdapter.ViewHolder> {

    private List<Meal> meals;
    private IOnPlannedMealClickListener listener;

    public PlannedMealsAdapter(List<Meal> meals, IOnPlannedMealClickListener listener) {
        this.meals = meals;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlannedMealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.planned_meal_item, parent, false);
        PlannedMealsAdapter.ViewHolder viewHolder = new PlannedMealsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlannedMealsAdapter.ViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.mealName.setText(meal.getStrMeal());
        holder.mealDate.setText(meal.getPlannedDate());
        Glide.with(holder.mealThumb).load(meal.getStrMealThumb()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.mealThumb);
        holder.plannedMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPlannedMealClick(meal);
            }
        });
        holder.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPlannedMealRemoveBtnClick(meal);
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
        public TextView mealDate;
        public Button removeBtn;
        public View plannedMeal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            plannedMeal = itemView;
            mealThumb = plannedMeal.findViewById(R.id.iv_meal_thumbnail);
            mealName = plannedMeal.findViewById(R.id.tv_meal_name);
            mealDate = plannedMeal.findViewById(R.id.plannedDate);
            removeBtn = plannedMeal.findViewById(R.id.btn_remove_planned);
        }
    }
}
