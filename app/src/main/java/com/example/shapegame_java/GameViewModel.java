package com.example.shapegame_java;

import android.content.Context;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GameViewModel extends ViewModel {
    private int level = 1;
    private int questionIndex = 0;
    private int score = 0;
    private List<ShapeQuestion> shuffledQuestions = new ArrayList<>();
    private SoundManager soundManager;

    private final List<ShapeQuestion> grade1Questions = Arrays.asList(
        new ShapeQuestion(R.drawable.square, "Square", Arrays.asList("Circle", "Triangle", "Star")),
        new ShapeQuestion(R.drawable.circle, "Circle", Arrays.asList("Square", "Rectangle", "Star")),
        new ShapeQuestion(R.drawable.triangle, "Triangle", Arrays.asList("Circle", "Square", "Rectangle")),
        new ShapeQuestion(R.drawable.rectangle, "Rectangle", Arrays.asList("Triangle", "Circle", "Star")),
        new ShapeQuestion(R.drawable.star, "Star", Arrays.asList("Square", "Circle", "Triangle"))
    );

    private final List<ShapeQuestion> grade2Questions;
    private final List<ShapeQuestion> grade3Questions;

    public GameViewModel() {
        // Initialize grade2Questions
        List<ShapeQuestion> grade2Temp = new ArrayList<>(grade1Questions);
        grade2Temp.addAll(Arrays.asList(
            new ShapeQuestion(R.drawable.oval, "Oval", Arrays.asList("Circle", "Diamond", "Hexagon")),
            new ShapeQuestion(R.drawable.diamond, "Diamond", Arrays.asList("Oval", "Hexagon", "Rectangle")),
            new ShapeQuestion(R.drawable.hexagon, "Hexagon", Arrays.asList("Diamond", "Oval", "Circle"))
        ));
        grade2Questions = grade2Temp;

        // Initialize grade3Questions
        List<ShapeQuestion> grade3Temp = new ArrayList<>(grade2Questions);
        grade3Temp.addAll(Arrays.asList(
            new ShapeQuestion(R.drawable.cone, "Cone", Arrays.asList("Cube", "Sphere", "Cylinder")),
            new ShapeQuestion(R.drawable.cube, "Cube", Arrays.asList("Cone", "Sphere", "Cylinder")),
            new ShapeQuestion(R.drawable.sphere, "Sphere", Arrays.asList("Cube", "Cone", "Cylinder")),
            new ShapeQuestion(R.drawable.cylinder, "Cylinder", Arrays.asList("Sphere", "Cube", "Cone")),
            new ShapeQuestion(R.drawable.pentagon, "Pentagon", Arrays.asList("Hexagon", "Octagon", "Diamond")),
            new ShapeQuestion(R.drawable.octagon, "Octagon", Arrays.asList("Pentagon", "Hexagon", "Diamond"))
        ));
        grade3Questions = grade3Temp;
    }

    public void setLevel(int level) {
        this.level = level;
        resetGame();
    }

    public ShapeQuestion getCurrentQuestion() {
        return questionIndex < shuffledQuestions.size() ? shuffledQuestions.get(questionIndex) : null;
    }

    public void submitAnswer(String answer) {
        ShapeQuestion currentQuestion = getCurrentQuestion();
        if (currentQuestion == null) return;
        
        if (answer.equals(currentQuestion.getCorrectAnswer())) {
            score++;
            if (soundManager != null) soundManager.playCorrectSound();
        } else {
            if (soundManager != null) soundManager.playWrongSound();
        }
    }

    public void moveToNextQuestion() {
        questionIndex++;
    }

    public boolean isQuizFinished() {
        switch (level) {
            case 1:
                return questionIndex >= 4;
            case 2:
                return questionIndex >= 5;
            case 3:
                return questionIndex >= 7;
            default:
                return questionIndex >= 4;
        }
    }

    public void resetGame() {
        questionIndex = 0;
        score = 0;
        List<ShapeQuestion> sourceQuestions;
            switch (level) {
                case 1:
                    sourceQuestions = new ArrayList<>(grade1Questions);
                    break;
                case 2:
                    sourceQuestions = new ArrayList<>(grade2Questions);
                    break;
                case 3:
                    sourceQuestions = new ArrayList<>(grade3Questions);
                    break;
                default:
                    sourceQuestions = new ArrayList<>(grade1Questions);
                    break;
            }
        Collections.shuffle(sourceQuestions);
        int takeCount;
            switch (level) {
                case 1:
                    takeCount = 4;
                    break;
                case 2:
                    takeCount = 5;
                    break;
                case 3:
                    takeCount = 7;
                    break;
                default:
                    takeCount = 4;
                    break;
            }
        shuffledQuestions = sourceQuestions.subList(0, takeCount);
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return shuffledQuestions.size();
    }

    public void initializeSoundManager(Context context) {
        soundManager = new SoundManager(context);
    }

    // Sound management methods
    public void startGameMusic() { if (soundManager != null) soundManager.startGameMusic(); }
    public void stopGameMusic() { if (soundManager != null) soundManager.stopGameMusic(); }
    public void playResultSound() { if (soundManager != null) soundManager.playResultSound(); }

    public void cleanup() {
        stopGameMusic();
        if (soundManager != null) {
            soundManager.release();
        }
    }
}
