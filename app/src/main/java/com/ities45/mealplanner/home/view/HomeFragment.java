package com.ities45.mealplanner.home.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.ities45.mealplanner.mainactivity.view.IHomeCommunicator;
import com.ities45.mealplanner.model.local.db.MealsLocalDataSourceImpl;
import com.ities45.mealplanner.model.local.networklistener.INetworkStatusListener;
import com.ities45.mealplanner.model.local.networklistener.NetworkManager;
import com.ities45.mealplanner.model.pojo.Area;
import com.ities45.mealplanner.model.pojo.Category;
import com.ities45.mealplanner.model.pojo.Ingredient;
import com.ities45.mealplanner.model.pojo.Meal;
import com.ities45.mealplanner.model.remote.areas.AreasRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.categories.CategoriesRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.ingredients.IngredientsRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.meals.MealsRemoteDataSourceImpl;
import com.ities45.mealplanner.model.repository.meals.MealsRepositoryImpl;

import java.util.List;

public class HomeFragment extends Fragment implements IHomeFragmentView, INetworkStatusListener {

    private RecyclerView rvPopMeals, rvCategories, rvAreas, rvIngredients;
    private IHomePresenter presenter;
    private PopularMealsAdapter popularMealsAdapter;
    private CategoriesAdapter categoriesAdapter;
    private AreasAdapter areasAdapter;
    private IngredientsAdapter ingredientsAdapter;
    private Button mmBtn;
    private ImageView motdI;
    private TextView motdT;
    private View homeContentLayout;
    private View noInternetLayout;
    private Button btnRetry, btnGoToFavorites, btnGoToPlanned;
    private IHomeCommunicator communicator;
    private Meal motd = new Meal();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_no_net_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);
        setupRecyclerViews();
        setupPresenter();
        setupListeners();

        presenter.checkConnectionAndUpdateUI();
    }

    private void initializeViews(View view) {
        rvPopMeals = view.findViewById(R.id.rvPopularMeals);
        rvCategories = view.findViewById(R.id.rvCategories);
        rvAreas = view.findViewById(R.id.rvAreas);
        rvIngredients = view.findViewById(R.id.rvIngredients);
        mmBtn = view.findViewById(R.id.btnRandomMeal);
        motdI = view.findViewById(R.id.imgMealOfTheDay);
        motdT = view.findViewById(R.id.txtMealName);
        homeContentLayout = view.findViewById(R.id.homeContentLayout);
        noInternetLayout = view.findViewById(R.id.noInternetLayout);
        btnRetry = view.findViewById(R.id.btnRetry);
        btnGoToFavorites = view.findViewById(R.id.btnFavMeals);
        btnGoToPlanned = view.findViewById(R.id.btnPlannedMeals);
    }

    private void setupRecyclerViews() {
        rvPopMeals.setHasFixedSize(true);
        rvPopMeals.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false));

        rvCategories.setHasFixedSize(true);
        rvCategories.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false));

        rvAreas.setHasFixedSize(true);
        rvAreas.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false));

        rvIngredients.setHasFixedSize(true);
        rvIngredients.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void setupPresenter() {
        presenter = new HomePresenterImpl(this,
                MealsRepositoryImpl.getInstance(
                        MealsRemoteDataSourceImpl.getInstance(getContext()),
                        CategoriesRemoteDataSourceImpl.getInstance(getContext()),
                        AreasRemoteDataSourceImpl.getInstance(getContext()),
                        IngredientsRemoteDataSourceImpl.getInstance(getContext()),
                        NetworkManager.getInstance(getContext(), this),
                        MealsLocalDataSourceImpl.getInstance(getContext())
                ), communicator);
    }

    private void setupListeners() {
        btnRetry.setOnClickListener(v -> presenter.checkConnectionAndUpdateUI());
        mmBtn.setOnClickListener(v -> presenter.MagicMealRoulette());
        motdI.setOnClickListener(v -> presenter.onMealClicked(motd));
        // btnGoToFavorites.setOnClickListener(v -> navigateToFragment(new FavoritesFragment()));
        // btnGoToPlanned.setOnClickListener(v -> navigateToFragment(new PlannedMealsFragment()));
    }

    @Override
    public void showMealOfTheDay(Meal meal) {
        Glide.with(motdI.getContext()).load(meal.getStrMealThumb())
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(motdI);
        motdT.setText(meal.getStrMeal());
        motd = meal;
    }

    @Override
    public void showPopularMeals(List<Meal> meals) {
        popularMealsAdapter = new PopularMealsAdapter(meals, new IOnPopularMealClickListener() {
            @Override
            public void onPopularMealClick(String id) {
                presenter.onMealIdClicked(id);
            }
        });
        rvPopMeals.setAdapter(popularMealsAdapter);
    }

    @Override
    public void showCategories(List<Category> categories) {
        categoriesAdapter = new CategoriesAdapter(categories, new IOnCategoryClickListener() {
            @Override
            public void onCategoryClick(Category category) {
                // Handle click event
            }
        });
        rvCategories.setAdapter(categoriesAdapter);
    }

    @Override
    public void showAreas(List<Area> areas) {
        areasAdapter = new AreasAdapter(areas, new IOnAreaClickListener() {
            @Override
            public void onAreaClick(Area area) {
                // Handle click event
            }
        });
        rvAreas.setAdapter(areasAdapter);
    }

    @Override
    public void showIngredients(List<Ingredient> ingredients) {
        ingredientsAdapter = new IngredientsAdapter(ingredients, new IOnIngredientClickListener() {
            @Override
            public void onIngredientClick(Ingredient ingredient) {
                // Handle click event
            }
        });
        rvIngredients.setAdapter(ingredientsAdapter);
    }

    @Override
    public void showErrMsg(String errorMsg) {
        //Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMainContent() {
        homeContentLayout.setVisibility(View.VISIBLE);
        noInternetLayout.setVisibility(View.GONE);
    }

    @Override
    public void showNoInternetLayout() {
        homeContentLayout.setVisibility(View.GONE);
        noInternetLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    private void navigateToFragment(Fragment fragment) {
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onNetworkAvailable() {
        presenter.checkConnectionAndUpdateUI();
    }

    @Override
    public void onNetworkLost() {
        presenter.checkConnectionAndUpdateUI();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        communicator = (IHomeCommunicator) context;
    }
}