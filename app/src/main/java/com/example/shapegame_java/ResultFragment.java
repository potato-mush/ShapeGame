package com.example.shapegame_java;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class ResultFragment extends Fragment {
    private int score;
    private int totalQuestions;
    private Runnable onReturnToMenu;

    public static ResultFragment newInstance(int score, int totalQuestions) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putInt("score", score);
        args.putInt("totalQuestions", totalQuestions);
        fragment.setArguments(args);
        return fragment;
    }

    public void setCallback(Runnable onReturnToMenu) {
        this.onReturnToMenu = onReturnToMenu;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            score = getArguments().getInt("score");
            totalQuestions = getArguments().getInt("totalQuestions");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        TextView scoreText = view.findViewById(R.id.scoreText);
        scoreText.setText(String.format("Your Score: %d / %d", score, totalQuestions));

        Button mainMenuButton = view.findViewById(R.id.mainMenuButton);
        mainMenuButton.setOnClickListener(v -> {
            if (onReturnToMenu != null) onReturnToMenu.run();
        });

        return view;
    }
}
