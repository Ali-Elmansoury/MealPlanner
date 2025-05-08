package com.ities45.mealplanner.model.repository.users;

import com.ities45.mealplanner.model.remote.firebase.auth.IAuthCallback;

public interface IUsersRepository {
    void signUpWithEmail(String email, String password, String name, IAuthCallback callback);
    void loginWithEmail(String email, String password, IAuthCallback callback);
    void anonymousLogin(IAuthCallback callback);
    String getCurrentUserId();

    void getUserName(String userId, UserNameCallback callback);

    interface UserNameCallback {
        void onSuccess(String username);
        void onFailure(String error);
    }
}
