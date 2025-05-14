package com.example.shapegame_java;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;

public class LevelsFragment extends Fragment {
    private LevelSelectedListener levelSelectedListener;

    public interface LevelSelectedListener {
        void onLevelSelected(int level);
    }

    public void setLevelSelectedListener(LevelSelectedListener listener) {
        this.levelSelectedListener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_levels, container, false);

        ImageButton backButton = view.findViewById(R.id.backButton);
        Button grade1Button = view.findViewById(R.id.grade1Button);
        Button grade2Button = view.findViewById(R.id.grade2Button);
        Button grade3Button = view.findViewById(R.id.grade3Button);

        backButton.setOnClickListener(v -> {
            if (levelSelectedListener != null) {
                levelSelectedListener.onLevelSelected(0);
            }
        });

        grade1Button.setOnClickListener(v -> {
            if (levelSelectedListener != null) {
                levelSelectedListener.onLevelSelected(1);
            }
        });

        grade2Button.setOnClickListener(v -> {
            if (levelSelectedListener != null) {
                levelSelectedListener.onLevelSelected(2);
            }
        });

        grade3Button.setOnClickListener(v -> {
            if (levelSelectedListener != null) {
                levelSelectedListener.onLevelSelected(3);
            }
        });

        return view;
    }
}
