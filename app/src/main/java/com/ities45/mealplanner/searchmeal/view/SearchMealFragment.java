package com.ities45.mealplanner.searchmeal.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ities45.mealplanner.R;
import com.ities45.mealplanner.home.view.IOnPopularMealClickListener;
import com.ities45.mealplanner.home.view.PopularMealsAdapter;
import com.ities45.mealplanner.mainactivity.view.ISearchMealCommunicator;
import com.ities45.mealplanner.model.local.db.MealsLocalDataSourceImpl;
import com.ities45.mealplanner.model.local.networklistener.NetworkManager;
import com.ities45.mealplanner.model.pojo.Meal;
import com.ities45.mealplanner.model.remote.areas.AreasRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.categories.CategoriesRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.firebase.firestore.FirestoreClient;
import com.ities45.mealplanner.model.remote.ingredients.IngredientsRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.meals.MealsRemoteDataSourceImpl;
import com.ities45.mealplanner.model.repository.meals.MealsRepositoryImpl;
import com.ities45.mealplanner.searchmeal.presenter.ISearchMealPresenter;
import com.ities45.mealplanner.searchmeal.presenter.SearchMealPresenterImpl;

import java.util.ArrayList;
import java.util.List;

public class SearchMealFragment extends Fragment implements ISearchMealFragmentView{

    private RecyclerView rvSearchMeals;
    private MealsAdapter mealsAdapter;
    private ISearchMealPresenter presenter;
    private ISearchMealCommunicator communicator;
    private SearchView searchView;
    private TextView tvTitle;

    // Add pending item fields
    private String pendingItemName;
    private String pendingItemType;

    private List<Meal> allMeals = new ArrayList<>(); // Keep a reference to all meals

    public SearchMealFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        communicator = (ISearchMealCommunicator) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SearchMealPresenterImpl(this, MealsRepositoryImpl.getInstance(
                MealsRemoteDataSourceImpl.getInstance(getContext()),
                CategoriesRemoteDataSourceImpl.getInstance(getContext()),
                AreasRemoteDataSourceImpl.getInstance(getContext()),
                IngredientsRemoteDataSourceImpl.getInstance(getContext()),
                NetworkManager.getInstance(getContext(), null),
                MealsLocalDataSourceImpl.getInstance(getContext()),
                FirestoreClient.getInstance()), communicator);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSearchMeals = view.findViewById(R.id.rvSearchMeals);
        searchView = view.findViewById(R.id.searchView);
        tvTitle = view.findViewById(R.id.titleTextView);

        searchView.onActionViewExpanded();
        searchView.setQueryHint("Search by Meals");

        rvSearchMeals.setHasFixedSize(true);
        rvSearchMeals.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        // Process pending items after initializing presenter
        if (pendingItemName != null && pendingItemType != null) {
            presenter.processReceivedMealItem(pendingItemName, pendingItemType);
            pendingItemName = null;
            pendingItemType = null;
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

    }

    public void onMealItemReceived(String itemName, String itemType) {
        if (presenter != null) {
            presenter.processReceivedMealItem(itemName, itemType);
        } else {
            // Cache the parameters if presenter isn't ready
            pendingItemName = itemName;
            pendingItemType = itemType;
        }
    }

    @Override
    public void showMeals(List<Meal> meals) {
        allMeals = meals; // Save the full list
        mealsAdapter = new MealsAdapter(allMeals, new IOnMealClickListener() {
            @Override
            public void onMealClick(Meal meal) {
                presenter.onMealClicked(meal);
            }
        });
        rvSearchMeals.setAdapter(mealsAdapter);
        mealsAdapter.notifyDataSetChanged();
    }

    // Filter meals based on search query
    private void filter(String text) {
        List<Meal> filteredList = new ArrayList<>();
        for (Meal meal : allMeals) {
            if (meal.getStrMeal().toLowerCase().contains(text.toLowerCase().trim())) {
                filteredList.add(meal);
            }
        }
        mealsAdapter = new MealsAdapter(filteredList, new IOnMealClickListener() {
            @Override
            public void onMealClick(Meal meal) {
                presenter.onMealClicked(meal);
            }
        });
        rvSearchMeals.setAdapter(mealsAdapter);
        mealsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showErrMsg(String errMsg) {
        Toast.makeText(getContext(), errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setTitle(String title) {
        if (tvTitle != null) {
            tvTitle.setText(title + " Meals"); // Added space for formatting
        }
    }
}