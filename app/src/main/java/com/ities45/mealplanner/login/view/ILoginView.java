package com.ities45.mealplanner.login.view;

import com.google.firebase.auth.FirebaseUser;

public interface ILoginView {
    String getEmail();
    String getPassword();
    void showEmailError(String message);
    void showPasswordError(String message);
    void onLoginSuccess();
    void onLoginFailure(String error);
    void onAnonymousLoginSuccess();
    void onAnonymousLoginFailure(String error);
}
