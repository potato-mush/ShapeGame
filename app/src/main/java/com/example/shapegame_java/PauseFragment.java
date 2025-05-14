package com.example.shapegame_java;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;

public class PauseFragment extends Fragment {
    private Runnable onResume;
    private Runnable onReset;
    private Runnable onMainMenu;

    public void setCallbacks(Runnable onResume, Runnable onReset, Runnable onMainMenu) {
        this.onResume = onResume;
        this.onReset = onReset;
        this.onMainMenu = onMainMenu;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pause, container, false);

        Button resumeButton = view.findViewById(R.id.resumeButton);
        Button resetButton = view.findViewById(R.id.resetButton);
        Button mainMenuButton = view.findViewById(R.id.mainMenuButton);

        resumeButton.setOnClickListener(v -> {
            if (onResume != null) onResume.run();
        });

        resetButton.setOnClickListener(v -> {
            if (onReset != null) onReset.run();
        });

        mainMenuButton.setOnClickListener(v -> {
            if (onMainMenu != null) onMainMenu.run();
        });

        return view;
    }
}
