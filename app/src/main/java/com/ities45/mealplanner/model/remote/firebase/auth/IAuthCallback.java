package com.ities45.mealplanner.model.remote.firebase.auth;

import com.google.firebase.auth.FirebaseUser;

public interface IAuthCallback {
    void onSuccess(FirebaseUser authResult);
    void onFailure(String exception);
}
