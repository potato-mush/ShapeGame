package com.example.shapegame_java;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import java.util.List;

public class QuizFragment extends Fragment {
    private GameViewModel gameViewModel;
    private Runnable onQuizEnd;
    private Runnable onPause;
    private ImageView shapeImage;
    private ImageView questionIcon;
    private TextView questionText;
    private Button option1, option2, option3, option4;
    private Button nextButton;

    public void setGameViewModel(GameViewModel viewModel) {
        this.gameViewModel = viewModel;
        if (getView() != null) {
            updateQuestion();
        }
    }

    public void setCallbacks(Runnable onQuizEnd, Runnable onPause) {
        this.onQuizEnd = onQuizEnd;
        this.onPause = onPause;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        // Initialize views
        shapeImage = view.findViewById(R.id.shapeImage);
        questionIcon = view.findViewById(R.id.questionIcon);
        questionText = view.findViewById(R.id.questionText);
        option1 = view.findViewById(R.id.option1);
        option2 = view.findViewById(R.id.option2);
        option3 = view.findViewById(R.id.option3);
        option4 = view.findViewById(R.id.option4);
        nextButton = view.findViewById(R.id.nextButton);
        ImageButton pauseButton = view.findViewById(R.id.pauseButton);

        // Set up click listeners
        option1.setOnClickListener(v -> handleAnswer(option1, option1.getText().toString()));
        option2.setOnClickListener(v -> handleAnswer(option2, option2.getText().toString()));
        option3.setOnClickListener(v -> handleAnswer(option3, option3.getText().toString()));
        option4.setOnClickListener(v -> handleAnswer(option4, option4.getText().toString()));
        
        nextButton.setOnClickListener(v -> {
            nextButton.setVisibility(View.GONE);
            gameViewModel.moveToNextQuestion();
            if (gameViewModel.isQuizFinished()) {
                if (onQuizEnd != null) onQuizEnd.run();
            } else {
                updateQuestion();
            }
        });

        pauseButton.setOnClickListener(v -> {
            if (onPause != null) onPause.run();
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (gameViewModel != null) {
            updateQuestion();
        }
    }

    private void handleAnswer(Button selectedButton, String answer) {
        if (gameViewModel != null) {
            ShapeQuestion currentQuestion = gameViewModel.getCurrentQuestion();
            if (currentQuestion != null) {
                boolean isCorrect = answer.equals(currentQuestion.getCorrectAnswer());
                
                // Disable all buttons to prevent multiple answers
                setButtonsEnabled(false);
                
                // Show visual feedback
                selectedButton.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(
                        isCorrect ? 0xFF00FF00 : 0xFFFF0000
                    )
                );

                gameViewModel.submitAnswer(answer);
                
                // Show next button
                nextButton.setVisibility(View.VISIBLE);
            }
        }
    }

    private void updateQuestion() {
        if (gameViewModel != null) {
            ShapeQuestion currentQuestion = gameViewModel.getCurrentQuestion();
            if (currentQuestion != null) {
                // Reset button colors and enable them
                resetButtons();
                setButtonsEnabled(true);

                // Set question image
                shapeImage.setImageResource(currentQuestion.getImageResId());

                // Set options
                List<String> options = currentQuestion.getOptions();
                if (options.size() >= 4) {
                    option1.setText(options.get(0));
                    option2.setText(options.get(1));
                    option3.setText(options.get(2));
                    option4.setText(options.get(3));
                }

                // Hide next button
                nextButton.setVisibility(View.GONE);
            }
        }
    }

    private void resetButtons() {
        int defaultColor = 0xFF45E1E1;
        option1.setBackgroundTintList(android.content.res.ColorStateList.valueOf(defaultColor));
        option2.setBackgroundTintList(android.content.res.ColorStateList.valueOf(defaultColor));
        option3.setBackgroundTintList(android.content.res.ColorStateList.valueOf(defaultColor));
        option4.setBackgroundTintList(android.content.res.ColorStateList.valueOf(defaultColor));
    }

    private void setButtonsEnabled(boolean enabled) {
        option1.setEnabled(enabled);
        option2.setEnabled(enabled);
        option3.setEnabled(enabled);
        option4.setEnabled(enabled);
    }
}
