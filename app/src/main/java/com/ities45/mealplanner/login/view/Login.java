package com.ities45.mealplanner.login.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.ities45.mealplanner.R;
import com.ities45.mealplanner.login.presenter.ILoginPresenter;
import com.ities45.mealplanner.login.presenter.LoginPresenterImpl;
import com.ities45.mealplanner.mainactivity.view.MainActivity;
import com.ities45.mealplanner.model.local.sessionmanager.SessionManager;
import com.ities45.mealplanner.model.remote.firebase.auth.FirebaseAuthClient;
import com.ities45.mealplanner.model.repository.users.UsersRepositoryImpl;
import com.ities45.mealplanner.register.view.Register;

public class Login extends AppCompatActivity implements ILoginView{
    private TextView tvRegister;
    private EditText etEmail, etPassword;
    private Button btnLogin, btnGoogle, btnFacebook;
    private ILoginPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        tvRegister = findViewById(R.id.register);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnFacebook = findViewById(R.id.btnFacebook);
        btnGoogle = findViewById(R.id.btnGoogle);

        presenter = new LoginPresenterImpl(this, UsersRepositoryImpl.getInstance(FirebaseAuthClient.getInstance()), new SessionManager(this));


        String text = "Don't have an account? Register";
        SpannableString spannableString = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(Login.this, Register.class));
            }
        };

        int startIndex = text.indexOf("Register");
        int endIndex = startIndex + "Register".length();

        spannableString.setSpan(new StyleSpan(Typeface.BOLD), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvRegister.setText(spannableString);
        tvRegister.setMovementMethod(LinkMovementMethod.getInstance());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.handleLogin();
            }
        });
    }

    @Override
    public String getEmail() {
        return etEmail.getText().toString();
    }

    @Override
    public String getPassword() {
        return etPassword.getText().toString();
    }

    @Override
    public void showEmailError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPasswordError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(this, "Firebase auth login success ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Login.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLoginFailure(String error) {
        Toast.makeText(this, "Login failed, " + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAnonymousLoginSuccess() {

    }

    @Override
    public void onAnonymousLoginFailure(String error) {

    }
}
