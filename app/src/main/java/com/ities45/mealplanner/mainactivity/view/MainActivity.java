package com.ities45.mealplanner.mainactivity.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ities45.mealplanner.R;
import com.ities45.mealplanner.favoritemeal.view.FavoriteMealsFragment;
import com.ities45.mealplanner.home.view.HomeFragment;
import com.ities45.mealplanner.itemdescription.view.ItemDescriptionFragment;
import com.ities45.mealplanner.model.pojo.Meal;

public class MainActivity extends AppCompatActivity implements IHomeCommunicator, IFavoriteMealCommunicator {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnItemSelectedListener(navListener);

        // Load initial fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();
    }

    private BottomNavigationView.OnItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                int id = item.getItemId();
                if (id == R.id.nav_home){
                    selectedFragment = new HomeFragment();
                }
//                else if (id == R.id.nav_search){
//
//                }
//                else if (id == R.id.nav_planned) {
//
//                }
                else if (id == R.id.nav_fav) {
                    selectedFragment = new FavoriteMealsFragment();
                }
//                else if (id == R.id.nav_profile) {
//
//                }
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                    return true;
                }
                return false; // Prevent navigation if fragment not implemented
            };

    @Override
    public void sendMealToItemDescriptionFragment(Meal meal) {
        ItemDescriptionFragment itemDescriptionFragment = new ItemDescriptionFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, itemDescriptionFragment)
                .commit();
        itemDescriptionFragment.onMealReceived(meal);
    }

    @Override
    public void sendMealIdToItemDescriptionFragment(String id) {
        ItemDescriptionFragment itemDescriptionFragment = new ItemDescriptionFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, itemDescriptionFragment)
                .commit();
        itemDescriptionFragment.onMealIdReceived(id);
    }
}