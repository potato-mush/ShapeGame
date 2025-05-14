package com.example.shapegame_java;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShapeQuestion {
    private final int imageResId;
    private final String correctAnswer;
    private final List<String> incorrectAnswers;
    private List<String> options;

    public ShapeQuestion(int imageResId, String correctAnswer, List<String> incorrectAnswers) {
        this.imageResId = imageResId;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    public int getImageResId() {
        return imageResId;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public List<String> getOptions() {
        if (options == null) {
            options = new ArrayList<>();
            options.add(correctAnswer);
            options.addAll(incorrectAnswers);
            Collections.shuffle(options);
        }
        return options;
    }

    public void resetOptions() {
        options = null;
    }
}
