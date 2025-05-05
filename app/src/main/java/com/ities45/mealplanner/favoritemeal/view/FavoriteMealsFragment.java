package com.ities45.mealplanner.favoritemeal.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ities45.mealplanner.R;
import com.ities45.mealplanner.favoritemeal.presenter.FavoriteMealsPresenterImpl;
import com.ities45.mealplanner.favoritemeal.presenter.IFavoriteMealsPresenter;
import com.ities45.mealplanner.mainactivity.view.IFavoriteMealCommunicator;
import com.ities45.mealplanner.mainactivity.view.IHomeCommunicator;
import com.ities45.mealplanner.model.local.db.MealsLocalDataSourceImpl;
import com.ities45.mealplanner.model.local.networklistener.NetworkManager;
import com.ities45.mealplanner.model.pojo.Meal;
import com.ities45.mealplanner.model.remote.areas.AreasRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.categories.CategoriesRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.ingredients.IngredientsRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.meals.MealsRemoteDataSourceImpl;
import com.ities45.mealplanner.model.repository.meals.MealsRepositoryImpl;

import java.util.List;

public class FavoriteMealsFragment extends Fragment implements IFavoriteMealsFragmentView{
    private IFavoriteMealCommunicator communicator;
    private IFavoriteMealsPresenter presenter;
    private RecyclerView rvFavMeals;
    private FavoriteMealsAdapter favoriteMealsAdapter;

    public FavoriteMealsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        communicator = (IFavoriteMealCommunicator) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FavoriteMealsPresenterImpl(null, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(getContext()),
                CategoriesRemoteDataSourceImpl.getInstance(getContext()),
                AreasRemoteDataSourceImpl.getInstance(getContext()),
                IngredientsRemoteDataSourceImpl.getInstance(getContext()),
                NetworkManager.getInstance(getContext(), null),
                MealsLocalDataSourceImpl.getInstance(getContext())), communicator);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvFavMeals = view.findViewById(R.id.rv_favorite_meals);
        rvFavMeals.setHasFixedSize(true);
        rvFavMeals.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false));

        LiveData<List<Meal>> meals = presenter.getFavMeals();
        meals.observe(getViewLifecycleOwner(), new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                favoriteMealsAdapter = new FavoriteMealsAdapter(meals, new IOnFavoriteMealClickListener() {
                    @Override
                    public void onFavMealCLick(Meal meal) {
                        presenter.onFavMealClicked(meal);
                    }

                    @Override
                    public void onFavMealRemoveBtnClick(Meal meal) {
                        showDeleteDialog(meal);
                    }
                });
                rvFavMeals.setAdapter(favoriteMealsAdapter);
            }
        });
    }

    @Override
    public void showFavMeal(Meal meal) {
        presenter.onFavMealClicked(meal);
    }

    @Override
    public void showErrMsg(String errMsg) {
        Toast.makeText(getContext(), errMsg, Toast.LENGTH_SHORT).show();
    }

    private void showDeleteDialog(Meal meal){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Do you want to remove meal ?");
        builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                presenter.removeMealFromFav(meal);
                favoriteMealsAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Meal removed from favorite", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}