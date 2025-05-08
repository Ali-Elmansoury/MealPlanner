package com.ities45.mealplanner.register.view;

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

import com.ities45.mealplanner.R;
import com.ities45.mealplanner.login.view.Login;
import com.ities45.mealplanner.mainactivity.view.MainActivity;
import com.ities45.mealplanner.model.remote.firebase.auth.FirebaseAuthClient;
import com.ities45.mealplanner.model.repository.users.UsersRepositoryImpl;
import com.ities45.mealplanner.register.presenter.IRegisterPresenter;
import com.ities45.mealplanner.register.presenter.RegisterPresenterImpl;

public class Register extends AppCompatActivity implements IRegisterView{

    private EditText etName, etEmail, etPassword, etConfirmPass;
    private TextView tvLogin;
    private Button btnRegister, btnFacebook, btnGoogle;
    private IRegisterPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        tvLogin = findViewById(R.id.tvLogin);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPass = findViewById(R.id.etConfirmPassword);
        btnRegister = findViewById(R.id.btnRegister);
        btnFacebook = findViewById(R.id.btnFacebook);
        btnGoogle = findViewById(R.id.btnGoogle);

        presenter = new RegisterPresenterImpl(this, UsersRepositoryImpl.getInstance(FirebaseAuthClient.getInstance()));

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

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.handleSignup();
            }
        });
    }

    @Override
    public String getName() {
        return etName.getText().toString();
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
    public String getConfirmPassword() {
        return etConfirmPass.getText().toString();
    }

    @Override
    public void showNameError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
    public void showConfirmPasswordError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignupSuccess() {
        Toast.makeText(this, "Firebase auth signup success", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
    }

    @Override
    public void onSignupFailure(String error) {
        Toast.makeText(this, "Signup failure: " + error, Toast.LENGTH_SHORT).show();
    }
}
