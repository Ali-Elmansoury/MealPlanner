package com.ities45.mealplanner.model.remote.firebase.auth;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ities45.mealplanner.model.repository.users.IUsersRepository;

import java.util.HashMap;
import java.util.Map;

public class FirebaseAuthClient {
    private static FirebaseAuthClient instance;
    private FirebaseAuth auth;
    private FirebaseFirestore db;

    private FirebaseAuthClient() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public static synchronized FirebaseAuthClient getInstance() {
        if (instance == null) {
            instance = new FirebaseAuthClient();
        }
        return instance;
    }

    public void createUserWithEmail(String email, String password, String name, IAuthCallback callback) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            // Create user document in Firestore
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("name", name);
                            userData.put("email", email);

                            db.collection("users").document(user.getUid())
                                    .set(userData)
                                    .addOnSuccessListener(aVoid -> callback.onSuccess(user))
                                    .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
                        }
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    public void signInWithEmail(String email, String password, IAuthCallback callback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(auth.getCurrentUser());
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    public void anonymousLogin(IAuthCallback callback) {
        auth.signInAnonymously()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess(auth.getCurrentUser());
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }

    // Add this method to get current user
    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    // Add this method
    public String getCurrentUserId() {
        FirebaseUser currentUser = auth.getCurrentUser();
        return currentUser != null ? currentUser.getUid() : null;
    }

    public void getUserName(String userId, IUsersRepository.UserNameCallback callback) {
        db.collection("users").document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String username = document.getString("name");
                            callback.onSuccess(username);
                        } else {
                            callback.onFailure("User document not found");
                        }
                    } else {
                        callback.onFailure(task.getException().getMessage());
                    }
                });
    }
}
