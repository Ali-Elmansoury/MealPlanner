package com.ities45.mealplanner.mainactivity.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ities45.mealplanner.R;
import com.ities45.mealplanner.favoritemeal.view.FavoriteMealsFragment;
import com.ities45.mealplanner.home.view.HomeFragment;
import com.ities45.mealplanner.itemdescription.view.ItemDescriptionFragment;
import com.ities45.mealplanner.login.view.Login;
import com.ities45.mealplanner.model.local.sessionmanager.SessionManager;
import com.ities45.mealplanner.plannedmeal.view.PlannedMealsFragment;
import com.ities45.mealplanner.profile.view.ProfileFragment;
import com.ities45.mealplanner.search.view.SearchFragment;
import com.ities45.mealplanner.searchmeal.view.SearchMealFragment;
import com.ities45.mealplanner.model.pojo.Meal;

public class MainActivity extends AppCompatActivity implements IHomeCommunicator, IFavoriteMealCommunicator, IPlannedMealCommunicator, ISearchMealCommunicator, ISearchCommunicator {

    private BottomNavigationView bottomNav;
    private SessionManager sessionManager;
    private String userId;
    private String userEmail;
    private String userName;
    private boolean isGuest;
    private boolean isLoggedIn;
    private Fragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        userId = sessionManager.getUserId();
        userEmail = sessionManager.getUserEmail();
        userName = sessionManager.getUserName();
        isGuest = sessionManager.isGuest();
        isLoggedIn = sessionManager.isLoggedIn();

        bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnItemSelectedListener(navListener);

        // Load initial fragment
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, homeFragment)
                .commit();
    }

    private BottomNavigationView.OnItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                int id = item.getItemId();

                // Check if user is guest and trying to access restricted fragments
                if (isGuest && (id == R.id.nav_planned || id == R.id.nav_fav)) {
                    showGuestAlertDialog();
                    return false; // Prevent navigation
                }

                // Proceed with fragment selection for non-restricted fragments or logged-in users
                if (id == R.id.nav_home) {
                    selectedFragment = new HomeFragment();
                } else if (id == R.id.nav_search) {
                    selectedFragment = new SearchFragment();
                } else if (id == R.id.nav_planned) {
                    selectedFragment = new PlannedMealsFragment();
                } else if (id == R.id.nav_fav) {
                    selectedFragment = new FavoriteMealsFragment();
                } else if (id == R.id.nav_profile) {
                    selectedFragment = new ProfileFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                    return true;
                }
                return false; // Prevent navigation if fragment not implemented
            };

    private void showGuestAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Guest User")
                .setMessage("You are a guest. Please log in to use this feature.")
                .setPositiveButton("Login", (dialog, which) -> {
                    // Navigate to LoginActivity
                    Intent intent = new Intent(MainActivity.this, Login.class); // Replace with your LoginActivity
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setCancelable(true)
                .show();
    }

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

    @Override
    public void onSearchItemClickedSendToSearchMeal(String itemName, String itemType) {
        SearchMealFragment searchMealFragment = new SearchMealFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, searchMealFragment)
                .commit();
        searchMealFragment.onMealItemReceived(itemName, itemType);
    }

}