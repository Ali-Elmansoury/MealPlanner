package com.ities45.mealplanner.plannedmeal.view;

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

import com.airbnb.lottie.L;
import com.ities45.mealplanner.R;
import com.ities45.mealplanner.mainactivity.view.IPlannedMealCommunicator;
import com.ities45.mealplanner.model.local.db.MealsLocalDataSourceImpl;
import com.ities45.mealplanner.model.local.networklistener.NetworkManager;
import com.ities45.mealplanner.model.pojo.Meal;
import com.ities45.mealplanner.model.remote.areas.AreasRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.categories.CategoriesRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.ingredients.IngredientsRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.meals.MealsRemoteDataSourceImpl;
import com.ities45.mealplanner.model.repository.meals.MealsRepositoryImpl;
import com.ities45.mealplanner.plannedmeal.presenter.IPlannedMealsPresenter;
import com.ities45.mealplanner.plannedmeal.presenter.PlannedMealsPresenterImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PlannedMealsFragment extends Fragment implements IPlannedMealsFragmentView{

    private IPlannedMealCommunicator communicator;
    private IPlannedMealsPresenter presenter;
    private RecyclerView rvCalendar;
    private RecyclerView rvPlannedMeals;
    private CalendarAdapter calendarAdapter;
    private PlannedMealsAdapter plannedMealsAdapter;


    public PlannedMealsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        communicator = (IPlannedMealCommunicator) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PlannedMealsPresenterImpl(null, MealsRepositoryImpl.getInstance(MealsRemoteDataSourceImpl.getInstance(getContext()),
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
        return inflater.inflate(R.layout.fragment_planned_meals, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPlannedMeals = view.findViewById(R.id.plannedRecyclerView);
        rvPlannedMeals.setHasFixedSize(true);
        rvPlannedMeals.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false));

        rvCalendar = view.findViewById(R.id.weekCalendarRecyclerView);
        rvCalendar.setHasFixedSize(true);
        rvCalendar.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Initialize calendar adapter
        calendarAdapter = new CalendarAdapter(getWeekDates(), new IOnDayClickListener() {
            @Override
            public void onDayClick(String date) {
                LiveData<List<Meal>> meals = presenter.getPlannedMealsByDate(date);
                meals.observe(getViewLifecycleOwner(), new Observer<List<Meal>>() {
                    @Override
                    public void onChanged(List<Meal> meals) {
                        plannedMealsAdapter = new PlannedMealsAdapter(meals, new IOnPlannedMealClickListener() {
                            @Override
                            public void onPlannedMealClick(Meal meal) {
                                presenter.onPlannedMealClicked(meal);
                            }

                            @Override
                            public void onPlannedMealRemoveBtnClick(Meal meal) {
                                showDeleteDialog(meal);
                            }
                        });
                        rvPlannedMeals.setAdapter(plannedMealsAdapter);
                    }
                });
            }
        });
        rvCalendar.setAdapter(calendarAdapter);
    }

    @Override
    public void showPlannedMeal(Meal meal) {
        presenter.onPlannedMealClicked(meal);
    }

    @Override
    public void showErrMsg(String errMsg) {
        Toast.makeText(getContext(), errMsg, Toast.LENGTH_SHORT).show();
    }

    private List<String> getWeekDates() {
        List<String> dates = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        for (int i = 0; i < 7; i++) {
            dates.add(sdf.format(calendar.getTime()));
            calendar.add(Calendar.DATE, 1);
        }
        return dates;
    }

    private void showDeleteDialog(Meal meal){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Do you want to remove meal ?");
        builder.setPositiveButton("Remove", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                presenter.removeMealFromPlanned(meal);
                plannedMealsAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Meal removed from planned", Toast.LENGTH_SHORT).show();
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