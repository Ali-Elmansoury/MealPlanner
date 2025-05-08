package com.ities45.mealplanner.model.repository.users;

import com.ities45.mealplanner.model.remote.firebase.auth.FirebaseAuthClient;
import com.ities45.mealplanner.model.remote.firebase.auth.IAuthCallback;

public class UsersRepositoryImpl implements IUsersRepository{
    private final FirebaseAuthClient authClient;
    private static UsersRepositoryImpl repo = null;

    private UsersRepositoryImpl(FirebaseAuthClient authClient) {
        this.authClient = authClient;
    }

    public static UsersRepositoryImpl getInstance(FirebaseAuthClient authClient){
        if (repo == null){
            repo = new UsersRepositoryImpl(authClient);
        }
        return repo;
    }

    @Override
    public void signUpWithEmail(String email, String password, String name, IAuthCallback callback) {
        authClient.createUserWithEmail(email, password, name, callback);
    }

    @Override
    public void loginWithEmail(String email, String password, IAuthCallback callback) {
        authClient.signInWithEmail(email, password, callback);
    }

    @Override
    public void anonymousLogin(IAuthCallback callback) {
        authClient.anonymousLogin(callback);
    }

    @Override
    public String getCurrentUserId() {
        return authClient.getCurrentUserId();
    }

    @Override
    public void getUserName(String userId, UserNameCallback callback) {
        authClient.getUserName(userId, callback);
    }
}
