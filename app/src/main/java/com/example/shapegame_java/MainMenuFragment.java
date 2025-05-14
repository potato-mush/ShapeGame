package com.example.shapegame_java;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainMenuFragment extends Fragment {
    private Runnable onStartQuiz;
    private Runnable onSettings;
    private Runnable onLevels;

    public void setCallbacks(Runnable onStartQuiz, Runnable onSettings, Runnable onLevels) {
        this.onStartQuiz = onStartQuiz;
        this.onSettings = onSettings;
        this.onLevels = onLevels;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);

        // Initialize buttons
        Button playButton = view.findViewById(R.id.playButton);
        Button levelsButton = view.findViewById(R.id.levelsButton);
        Button settingsButton = view.findViewById(R.id.settingsButton);

        // Set click listeners
        playButton.setOnClickListener(v -> {
            if (onStartQuiz != null) onStartQuiz.run();
        });

        levelsButton.setOnClickListener(v -> {
            if (onLevels != null) onLevels.run();
        });

        settingsButton.setOnClickListener(v -> {
            if (onSettings != null) onSettings.run();
        });

        return view;
    }
}
