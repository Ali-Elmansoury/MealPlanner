package com.ities45.mealplanner.register;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ities45.mealplanner.R;
import com.ities45.mealplanner.login.Login;

public class Register extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        TextView tvLogin = findViewById(R.id.tvLogin);
        String text = "Already have an account? Login";
        SpannableString spannableString = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(Register.this, Login.class));
            }
        };

        int startIndex = text.indexOf("Login");
        int endIndex = startIndex + "Login".length();

        spannableString.setSpan(new StyleSpan(Typeface.BOLD), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvLogin.setText(spannableString);
        tvLogin.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
