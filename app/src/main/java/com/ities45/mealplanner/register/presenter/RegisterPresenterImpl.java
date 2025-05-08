package com.ities45.mealplanner.register.presenter;

import android.util.Patterns;

import com.google.firebase.auth.FirebaseUser;
import com.ities45.mealplanner.model.remote.firebase.auth.IAuthCallback;
import com.ities45.mealplanner.model.repository.users.IUsersRepository;
import com.ities45.mealplanner.register.view.IRegisterView;

public class RegisterPresenterImpl implements IRegisterPresenter{
    private IRegisterView view;
    private IUsersRepository repo;

    public RegisterPresenterImpl(IRegisterView view, IUsersRepository repo) {
        this.view = view;
        this.repo = repo;
    }

    @Override
    public void handleSignup() {
        String name = view.getName();
        String email = view.getEmail();
        String password = view.getPassword();
        String confirmPassword = view.getConfirmPassword();

        if (name.isEmpty()) {
            view.showNameError("Name cannot be empty");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.showEmailError("Invalid email format");
            return;
        }

        if (password.length() < 6) {
            view.showPasswordError("Password must be at least 6 characters");
            return;
        }

        if (!password.equals(confirmPassword)) {
            view.showConfirmPasswordError("Passwords do not match");
            return;
        }

        repo.signUpWithEmail(email, confirmPassword, name, new IAuthCallback() {
            @Override
            public void onSuccess(FirebaseUser authResult) {
                view.onSignupSuccess();
            }

            @Override
            public void onFailure(String exception) {
                view.onSignupFailure(exception);
            }
        });
    }
}
