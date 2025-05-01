package com.ities45.mealplanner.home.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ities45.mealplanner.R;
import com.ities45.mealplanner.home.presenter.HomePresenterImpl;
import com.ities45.mealplanner.home.presenter.IHomePresenter;
import com.ities45.mealplanner.model.pojo.Area;
import com.ities45.mealplanner.model.pojo.Category;
import com.ities45.mealplanner.model.pojo.Meal;
import com.ities45.mealplanner.model.remote.areas.AreasRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.categories.CategoriesRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.ingredients.IngredientsRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.meals.MealsRemoteDataSourceImpl;
import com.ities45.mealplanner.model.repository.meals.MealsRepositoryImpl;

import java.util.List;

public class HomeFragment extends Fragment implements IHomeFragmentView{

    private RecyclerView rvPopMeals, rvCategories, rvAreas;
    private IHomePresenter presenter;
    private PopularMealsAdapter popularMealsAdapter;
    private CategoriesAdapter categoriesAdapter;
    private AreasAdapter areasAdapter;
    private Button mmBtn;
    private ImageView motdI;
    private TextView motdT;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPopMeals = view.findViewById(R.id.rvPopularMeals);
        rvCategories = view.findViewById(R.id.rvCategories);
        rvAreas = view.findViewById(R.id.rvAreas);
        mmBtn = view.findViewById(R.id.btnRandomMeal);
        motdI = view.findViewById(R.id.imgMealOfTheDay);
        motdT = view.findViewById(R.id.txtMealName);

        rvPopMeals.setHasFixedSize(true);
        rvPopMeals.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false));

        rvCategories.setHasFixedSize(true);
        rvCategories.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false));

        rvAreas.setHasFixedSize(true);
        rvAreas.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false));

        presenter = new HomePresenterImpl(this,
                MealsRepositoryImpl.getInstance(
                        MealsRemoteDataSourceImpl.getInstance(getContext()),
                        CategoriesRemoteDataSourceImpl.getInstance(getContext()),
                        AreasRemoteDataSourceImpl.getInstance(getContext()),
                        IngredientsRemoteDataSourceImpl.getInstance(getContext())
                ));

        presenter.getMealOfTheDay();
        presenter.getPopularMeals();
        presenter.getCategories();
        presenter.getAreas();

        mmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.MagicMealRoulette();
            }
        });
    }

    @Override
    public void showMealOfTheDay(Meal meal) {
        Log.d("HomeFragment", "New random meal: " + meal.getStrMeal());
        Glide.with(motdI.getContext()).load(meal.getStrMealThumb()).diskCacheStrategy(DiskCacheStrategy.ALL).into(motdI);
        motdT.setText(meal.getStrMeal());
    }

    @Override
    public void showPopularMeals(List<Meal> meals) {
        popularMealsAdapter = new PopularMealsAdapter(meals, new IOnPopularMealClickListener() {
            @Override
            public void onPopularMealClick(Meal meal) {

            }
        });
        rvPopMeals.setAdapter(popularMealsAdapter);
    }

    @Override
    public void showCategories(List<Category> categories) {
        categoriesAdapter = new CategoriesAdapter(categories, new IOnCategoryClickListener() {
            @Override
            public void onCategoryClick(Category category) {

            }
        });
        rvCategories.setAdapter(categoriesAdapter);
    }

    @Override
    public void showAreas(List<Area> areas) {
        areasAdapter = new AreasAdapter(areas, new IOnAreaClickListener() {
            @Override
            public void onAreaClick(Area area) {

            }
        });
        rvAreas.setAdapter(areasAdapter);
    }

    @Override
    public void showErrMsg(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}