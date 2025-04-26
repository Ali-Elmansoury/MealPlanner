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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.ities45.mealplanner.MainActivity;
import com.ities45.mealplanner.R;
import com.ities45.mealplanner.login.Login;
import com.ities45.mealplanner.register.Register;

public class OnBoarding extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding);

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

        Button contBtn = findViewById(R.id.cont_as_guest);
        String contBtnText = "Continue as guest";
        SpannableString spannableString = new SpannableString(contBtnText);
        spannableString.setSpan(new UnderlineSpan(), 0, contBtnText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        contBtn.setText(spannableString);
        contBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoarding.this, MainActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        Button register = findViewById(R.id.registerBtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoarding.this, Register.class);
                startActivity(intent);
                //finish();
            }
        });

        Button login = findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoarding.this, Login.class);
                startActivity(intent);
                //finish();
            }
        });
    }
}
