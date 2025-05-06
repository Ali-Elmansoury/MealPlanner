package com.ities45.mealplanner.search.view;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.ities45.mealplanner.R;
import com.ities45.mealplanner.home.view.AreasAdapter;
import com.ities45.mealplanner.home.view.CategoriesAdapter;
import com.ities45.mealplanner.home.view.IOnAreaClickListener;
import com.ities45.mealplanner.home.view.IOnCategoryClickListener;
import com.ities45.mealplanner.home.view.IOnIngredientClickListener;
import com.ities45.mealplanner.home.view.IngredientsAdapter;
import com.ities45.mealplanner.mainactivity.view.ISearchCommunicator;
import com.ities45.mealplanner.model.local.db.MealsLocalDataSourceImpl;
import com.ities45.mealplanner.model.local.networklistener.NetworkManager;
import com.ities45.mealplanner.model.pojo.Area;
import com.ities45.mealplanner.model.pojo.Category;
import com.ities45.mealplanner.model.pojo.Ingredient;
import com.ities45.mealplanner.model.remote.areas.AreasRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.categories.CategoriesRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.ingredients.IngredientsRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.meals.MealsRemoteDataSourceImpl;
import com.ities45.mealplanner.model.repository.meals.MealsRepositoryImpl;
import com.ities45.mealplanner.search.presenter.ISearchPresenter;
import com.ities45.mealplanner.search.presenter.SearchPresenterImpl;
import java.util.List;

public class SearchFragment extends Fragment implements ISearchFragmentView {
    private RecyclerView rvSearch;
    private CategoriesAdapter categoriesAdapter;
    private AreasAdapter areasAdapter;
    private IngredientsAdapter ingredientsAdapter;
    private ISearchPresenter presenter;
    private ISearchCommunicator communicator;
    private SearchView searchView;
    private ChipGroup chipGroup;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //communicator = (ISearchCommunicator) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SearchPresenterImpl(this, MealsRepositoryImpl.getInstance(
                MealsRemoteDataSourceImpl.getInstance(getContext()),
                CategoriesRemoteDataSourceImpl.getInstance(getContext()),
                AreasRemoteDataSourceImpl.getInstance(getContext()),
                IngredientsRemoteDataSourceImpl.getInstance(getContext()),
                NetworkManager.getInstance(getContext(), null),
                MealsLocalDataSourceImpl.getInstance(getContext())), communicator);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSearch = view.findViewById(R.id.searchItemsRecyclerView);
        searchView = view.findViewById(R.id.searchView);
        chipGroup = view.findViewById(R.id.chipGroup);

        rvSearch.setHasFixedSize(true);
        rvSearch.setLayoutManager(new GridLayoutManager(getContext(), 3));

        searchView.onActionViewExpanded();
        searchView.setQueryHint("Search by Category");

        // Handle chip selection
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Chip selectedChip = group.findViewById(checkedId);
            if (selectedChip != null) {
                String selectedText = selectedChip.getText().toString();
                // Update SearchView hint based on selected chip
                searchView.setQueryHint("Search by " + selectedText);

                // Fetch data based on selected chip
                switch (selectedText) {
                    case "Category":
                        presenter.getCategories();
                        break;
                    case "Ingredient":
                        presenter.getIngredients();
                        break;
                    case "Country":
                        presenter.getAreas();
                        break;
                }
            }
        });

        // Set default selection to Category and fetch categories on start
        Chip categoryChip = chipGroup.findViewById(R.id.chipCategory);
        if (categoryChip != null) {
            categoryChip.setChecked(true); // Programmatically trigger the default selection
        } else {
            // Fallback if chipCategory is not found
            presenter.getCategories();
        }
    }

    @Override
    public void showCategories(List<Category> categories) {
        categoriesAdapter = new CategoriesAdapter(categories, new IOnCategoryClickListener() {
            @Override
            public void onCategoryClick(Category category) {
                //communicator.onSearchItemClick(category.getStrCategory(), "category");
            }
        });
        rvSearch.setAdapter(categoriesAdapter);
    }

    @Override
    public void showAreas(List<Area> areas) {
        areasAdapter = new AreasAdapter(areas, new IOnAreaClickListener() {
            @Override
            public void onAreaClick(Area area) {
                //communicator.onSearchItemClick(area.getStrArea(), "area");
            }
        });
        rvSearch.setAdapter(areasAdapter);
    }

    @Override
    public void showIngredients(List<Ingredient> ingredients) {
        ingredientsAdapter = new IngredientsAdapter(ingredients, new IOnIngredientClickListener() {
            @Override
            public void onIngredientClick(Ingredient ingredient) {
                //communicator.onSearchItemClick(ingredient.getStrIngredient(), "ingredient");
            }
        });
        rvSearch.setAdapter(ingredientsAdapter);
    }

    @Override
    public void showErrMsg(String errorMsg) {
        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}