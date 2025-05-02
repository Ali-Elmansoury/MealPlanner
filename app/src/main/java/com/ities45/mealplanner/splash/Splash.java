package com.ities45.mealplanner.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.dotlottie.dlplayer.Mode;
import com.ities45.mealplanner.R;
import com.ities45.mealplanner.onboarding.OnBoarding;
import com.lottiefiles.dotlottie.core.model.Config;
import com.lottiefiles.dotlottie.core.util.DotLottieSource;
import com.lottiefiles.dotlottie.core.widget.DotLottieAnimation;

public class Splash extends AppCompatActivity {

    private DotLottieAnimation splashAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        splashAnim = findViewById(R.id.lottie_splash);
        Config config = new Config.Builder()
                .autoplay(true)
                .speed(1f)
                .loop(false)
                .source(new DotLottieSource.Asset("lottie_animated_splash.json")) // Asset from the asset folder .json or .lottie
                .playMode(Mode.FORWARD)
                .build();
        splashAnim.load(config);
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Splash.this, OnBoarding.class);
            startActivity(intent);
        }, 5000);
    }
}
