package com.ities45.mealplanner.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseUser;
import com.ities45.mealplanner.mainactivity.view.MainActivity;
import com.ities45.mealplanner.R;
import com.ities45.mealplanner.login.view.Login;
import com.ities45.mealplanner.model.local.sessionmanager.SessionManager;
import com.ities45.mealplanner.model.remote.firebase.auth.FirebaseAuthClient;
import com.ities45.mealplanner.model.remote.firebase.auth.IAuthCallback;
import com.ities45.mealplanner.model.repository.users.IUsersRepository;
import com.ities45.mealplanner.model.repository.users.UsersRepositoryImpl;
import com.ities45.mealplanner.register.view.Register;

public class OnBoarding extends AppCompatActivity {

    private IUsersRepository repo;
    private SessionManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding);

        manager = new SessionManager(this);

        TextView textView = findViewById(R.id.txt1);
        String fullText = "Your Go-To easy meal planning mate";
        SpannableString spannable = new SpannableString(fullText);
        int start = fullText.indexOf("Go-To");
        int end = start + "Go-To".length();
        int color = ContextCompat.getColor(this, R.color.btn);
        spannable.setSpan(
                new ForegroundColorSpan(color),
                start,
                end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        textView.setText(spannable);

        repo = UsersRepositoryImpl.getInstance(FirebaseAuthClient.getInstance());

        Button contBtn = findViewById(R.id.cont_as_guest);
        String contBtnText = "Continue as guest";
        SpannableString spannableString = new SpannableString(contBtnText);
        spannableString.setSpan(new UnderlineSpan(), 0, contBtnText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        contBtn.setText(spannableString);

        if (manager.isLoggedIn()) {
            Intent intent = new Intent(OnBoarding.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        contBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repo.anonymousLogin(new IAuthCallback() {
                    @Override
                    public void onSuccess(FirebaseUser authResult) {
                        manager.createGuestSession(authResult.getUid());
                        Intent intent = new Intent(OnBoarding.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(String exception) {
                        Toast.makeText(OnBoarding.this, "Anonymous login failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        Button register = findViewById(R.id.registerBtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoarding.this, Register.class);
                startActivity(intent);
                finish();
            }
        });

        Button login = findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoarding.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
