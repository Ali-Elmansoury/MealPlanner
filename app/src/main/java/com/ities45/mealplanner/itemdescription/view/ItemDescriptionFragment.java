package com.ities45.mealplanner.itemdescription.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ities45.mealplanner.R;
import com.ities45.mealplanner.itemdescription.presenter.I_ItemDescriptionPresenter;
import com.ities45.mealplanner.itemdescription.presenter.ItemDescriptionPresenterImpl;
import com.ities45.mealplanner.model.local.db.MealsLocalDataSourceImpl;
import com.ities45.mealplanner.model.local.networklistener.INetworkStatusListener;
import com.ities45.mealplanner.model.local.networklistener.NetworkManager;
import com.ities45.mealplanner.model.pojo.IngredientMeasureItem;
import com.ities45.mealplanner.model.pojo.Meal;
import com.ities45.mealplanner.model.remote.areas.AreasRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.categories.CategoriesRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.ingredients.IngredientsRemoteDataSourceImpl;
import com.ities45.mealplanner.model.remote.meals.MealsRemoteDataSourceImpl;
import com.ities45.mealplanner.model.repository.meals.MealsRepositoryImpl;
import com.ities45.mealplanner.plannedmeal.view.DatePickerDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ItemDescriptionFragment extends Fragment implements I_ItemDescriptionFragmentView, INetworkStatusListener {

    private I_ItemDescriptionPresenter presenter;
    private View itemDescContentLayout;
    private View noInternetLayout;
    private ImageView favBtn, mealThumb, flagIcon;
    private TextView mealName, mealInstructions, mealCategory, mealCountry;
    private Button addToPlanned;
    private RecyclerView rvIngredients;
    private WebView ytVideo;
    private ItemIngredientsAdapter itemIngredientsAdapter;
    private List<IngredientMeasureItem> ingredients;
    private Meal pendingMeal; // Store meal until presenter is initialized
    private String pendingId;
    private boolean isViewReady = false; // Track view initialization

    public ItemDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ItemDescriptionPresenterImpl(this, MealsRepositoryImpl.getInstance(
                MealsRemoteDataSourceImpl.getInstance(getContext()),
                CategoriesRemoteDataSourceImpl.getInstance(getContext()),
                AreasRemoteDataSourceImpl.getInstance(getContext()),
                IngredientsRemoteDataSourceImpl.getInstance(getContext()),
                NetworkManager.getInstance(getContext(), this),
                MealsLocalDataSourceImpl.getInstance(getContext())
        ));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.item_desc_no_net_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemDescContentLayout = view.findViewById(R.id.itemDescContentLayout);
        noInternetLayout = view.findViewById(R.id.noInternetLayout);

        favBtn = view.findViewById(R.id.favoriteButton);
        mealThumb = view.findViewById(R.id.mealImageView);
        flagIcon = view.findViewById(R.id.countryFlagImageView);

        mealName = view.findViewById(R.id.mealNameTextView);
        mealCategory = view.findViewById(R.id.categoryTextView);
        mealCountry = view.findViewById(R.id.countryTextView);
        mealInstructions = view.findViewById(R.id.instructionsTextView);

        addToPlanned = view.findViewById(R.id.addToMealPlanButton);

        rvIngredients = view.findViewById(R.id.ingredientsRecyclerView);
        rvIngredients.setHasFixedSize(true);
        rvIngredients.setLayoutManager(new LinearLayoutManager(
                getContext(), LinearLayoutManager.VERTICAL, false));

        ytVideo = view.findViewById(R.id.youtubeWebView);

        // Configure WebView for YouTube video playback
        ytVideo.getSettings().setJavaScriptEnabled(true);
        ytVideo.setWebChromeClient(new WebChromeClient()); // Enable video playback

        isViewReady = true; // Views are now ready

        // Process pending meal if it exists
        if (pendingMeal != null) {
            presenter.processReceivedMeal(pendingMeal);
            pendingMeal = null;
        }

        if (pendingId != null) {
            presenter.processReceivedMealId(pendingId);
            pendingId = null;
        }

        presenter.checkConnectionAndUpdateUI();
    }

    // Called via communicator pattern from MainActivity
    public void onMealReceived(Meal meal) {
        if (isViewReady) {
            // Views are ready; process immediately
            presenter.processReceivedMeal(meal);
        } else {
            // Views not ready; save for later
            pendingMeal = meal;
        }
    }

    public void onMealIdReceived(String id) {
        if (isViewReady) {
            // Views are ready; process immediately
            presenter.processReceivedMealId(id);
        } else {
            // Views not ready; save for later
            pendingId = id;
        }
    }

    @Override
    public void showMeal(Meal meal) {
        Glide.with(mealThumb.getContext()).load(meal.getStrMealThumb()).diskCacheStrategy(DiskCacheStrategy.ALL).into(mealThumb);
        Glide.with(flagIcon.getContext()).load(meal.getFlagUrl()).diskCacheStrategy(DiskCacheStrategy.ALL).into(flagIcon);
        mealName.setText(meal.getStrMeal());
        mealCategory.setText(meal.getStrCategory());
        mealCountry.setText(meal.getStrArea());
        mealInstructions.setText(meal.getStrInstructions());
        ingredients = presenter.getMealIngredients(meal);
        itemIngredientsAdapter = new ItemIngredientsAdapter(ingredients);
        rvIngredients.setAdapter(itemIngredientsAdapter);
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addMealToFav(meal);
            }
        });
        addToPlanned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialogFragment dialog = new DatePickerDialogFragment();
                dialog.setDateSetListener((view, year, month, dayOfMonth) -> {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(year, month, dayOfMonth);
                    String dateString = sdf.format(selectedDate.getTime());
                    presenter.addMealToPlanned(meal, dateString);
                    Toast.makeText(getContext(), "Meal planned for " + dateString, Toast.LENGTH_SHORT).show();
                });
                dialog.show(getParentFragmentManager(), "datePicker");
            }
        });

        // Load YouTube video
        String youtubeLink = meal.getStrYoutube(); // Assuming getStrYoutube() returns the YouTube link
        if (youtubeLink != null && !youtubeLink.isEmpty()) {
            String videoId = extractVideoId(youtubeLink);
            if (videoId != null) {
                String html = "<html>" +
                        "<body>" +
                        "<iframe width=\"100%\" height=\"100%\" " +
                        "src=\"https://www.youtube.com/embed/" + videoId + "?autoplay=0&rel=0&showinfo=0\" " +
                        "frameborder=\"0\" " +
                        "allowfullscreen>" +
                        "</iframe>" +
                        "</body>" +
                        "</html>";
                ytVideo.loadData(html, "text/html", "utf-8");
            } else {
                Toast.makeText(getContext(), "Invalid YouTube link", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "No YouTube video available", Toast.LENGTH_SHORT).show();
        }
    }

    // Method to extract video ID from YouTube URL
    private String extractVideoId(String youtubeUrl) {
        if (youtubeUrl != null && youtubeUrl.contains("v=")) {
            String[] parts = youtubeUrl.split("v=");
            if (parts.length > 1) {
                String videoId = parts[1];
                // Handle cases where there are additional query parameters (e.g., &t=10s)
                int ampersandIndex = videoId.indexOf('&');
                if (ampersandIndex != -1) {
                    videoId = videoId.substring(0, ampersandIndex);
                }
                return videoId;
            }
        }
        return null;
    }

    @Override
    public void showErrMsg(String errorMsg) {
        //Toast.makeText(getContext(), errorMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMainContent() {
        itemDescContentLayout.setVisibility(View.VISIBLE);
        noInternetLayout.setVisibility(View.GONE);
    }

    @Override
    public void showNoInternetLayout() {
        itemDescContentLayout.setVisibility(View.GONE);
        noInternetLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void favMealExists() {
        Toast.makeText(getContext(), "Item exists in favorite meals", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void favMealAdded() {
        Toast.makeText(getContext(), "Item added to favorite meals successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void plannedMealAdded() {
        Toast.makeText(getContext(), "Item added to planned meals successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void plannedMealExists() {
        Toast.makeText(getContext(), "Item exists in planned meals", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
        if (ytVideo != null) {
            ytVideo.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
        if (ytVideo != null) {
            ytVideo.onPause();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (ytVideo != null) {
            ytVideo.destroy();
        }
    }

    @Override
    public void onNetworkAvailable() {
        presenter.checkConnectionAndUpdateUI();
    }

    @Override
    public void onNetworkLost() {
        presenter.checkConnectionAndUpdateUI();
    }
}