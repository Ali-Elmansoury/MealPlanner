package com.ities45.mealplanner.register.view;

public interface IRegisterView {
    String getName();
    String getEmail();
    String getPassword();
    String getConfirmPassword();
    void showNameError(String message);
    void showEmailError(String message);
    void showPasswordError(String message);
    void showConfirmPasswordError(String message);
    void onSignupSuccess();
    void onSignupFailure(String error);
}
