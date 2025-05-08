package com.ities45.mealplanner.profile.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.ities45.mealplanner.R;
import com.ities45.mealplanner.model.local.sessionmanager.SessionManager;
import com.ities45.mealplanner.model.remote.firebase.auth.FirebaseAuthClient;
import com.ities45.mealplanner.register.view.Register;

public class ProfileFragment extends Fragment {

    private TextView tvName, tvEmail;
    private Button btnLogOut;
    private SessionManager sessionManager;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvName = view.findViewById(R.id.user_name);
        tvEmail = view.findViewById(R.id.user_email);
        btnLogOut = view.findViewById(R.id.logout_button);

        sessionManager = new SessionManager(getContext());



        if (sessionManager.isGuest()){
            tvName.setText("Username: Guest");
            tvEmail.setText("Email: null");
        }
        else if(sessionManager.isLoggedIn()){
            tvName.setText("Username: " + sessionManager.getUserName());
            tvEmail.setText("Email: " + sessionManager.getUserEmail());
        }
        else {

        }

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                sessionManager.clearSession();
                startActivity(new Intent(getContext(), Register.class));
                getParentFragmentManager().popBackStack();
            }
        });
    }
}