package com.ities45.mealplanner.login.presenter;

import android.util.Patterns;

import com.google.firebase.auth.FirebaseUser;
import com.ities45.mealplanner.login.view.ILoginView;
import com.ities45.mealplanner.model.local.sessionmanager.SessionManager;
import com.ities45.mealplanner.model.remote.firebase.auth.IAuthCallback;
import com.ities45.mealplanner.model.repository.users.IUsersRepository;

public class LoginPresenterImpl implements ILoginPresenter{
    private ILoginView view;
    private IUsersRepository repo;
    private SessionManager manager;

    public LoginPresenterImpl(ILoginView view, IUsersRepository repo, SessionManager manager) {
        this.view = view;
        this.repo = repo;
        this.manager = manager;
    }

    @Override
    public void handleLogin() {
        String email = view.getEmail();
        String password = view.getPassword();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view.showEmailError("Invalid email format");
            return;
        }

        if (password.isEmpty()) {
            view.showPasswordError("Password cannot be empty");
            return;
        }

        repo.loginWithEmail(email, password, new IAuthCallback() {
            @Override
            public void onSuccess(FirebaseUser authResult) {
                repo.getUserName(authResult.getUid(), new IUsersRepository.UserNameCallback() {
                    @Override
                    public void onSuccess(String username) {
                        manager.createUserSession(authResult.getUid(), email, username);
                    }

                    @Override
                    public void onFailure(String error) {
                        view.onLoginFailure("Failed to get username, " + error);
                    }
                });
                view.onLoginSuccess();
            }

            @Override
            public void onFailure(String exception) {
                view.onLoginFailure(exception);
            }
        });
    }

    @Override
    public void handleAnonymousLogin() {
        repo.anonymousLogin(new IAuthCallback() {
            @Override
            public void onSuccess(FirebaseUser authResult) {
                manager.createGuestSession(authResult.getUid());
                view.onAnonymousLoginSuccess();
            }

            @Override
            public void onFailure(String exception) {
                view.onAnonymousLoginFailure(exception);
            }
        });
    }
}
