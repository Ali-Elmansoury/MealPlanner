package com.ities45.mealplanner.waiting;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.airbnb.lottie.LottieAnimationView;
import com.ities45.mealplanner.R;

public class WaitingFragment extends Fragment {

    private static final String ARG_ANIMATION_FILE = "animation_file";

    private LottieAnimationView lottieAnimation;
    private INavigationCommunicator communicator;

    public static WaitingFragment newInstance(String animationFile) {
        WaitingFragment fragment = new WaitingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ANIMATION_FILE, animationFile);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            communicator = (INavigationCommunicator) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement INavigationCommunicator");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_waiting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lottieAnimation = view.findViewById(R.id.lottie_wait);
        String animationFile = getArguments() != null ? getArguments().getString(ARG_ANIMATION_FILE, "lottie_animated_wait.json") : "lottie_animated_wait.json";
        lottieAnimation.setAnimation(animationFile);
        lottieAnimation.setRepeatCount(0); // Play once
        lottieAnimation.playAnimation();
    }

    public void navigateToDestination() {
        if (isAdded()) {
            Fragment destinationFragment = communicator.getDestinationFragment();
            if (destinationFragment != null) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, destinationFragment);
                transaction.remove(WaitingFragment.this);
                transaction.commit();
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        communicator = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        lottieAnimation = null;
    }
}