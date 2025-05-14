package com.example.shapegame_java;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {
    private GameViewModel gameViewModel;
    private String currentScreen = "MainMenu";
    private BackgroundShapesManager backgroundShapesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ViewModel
        gameViewModel = new ViewModelProvider(this).get(GameViewModel.class);

        // Initialize sound manager
        gameViewModel.initializeSoundManager(this);

        // Initialize background shapes manager
        ConstraintLayout rootLayout = findViewById(R.id.main);
        backgroundShapesManager = new BackgroundShapesManager(this, rootLayout);

        // Set up screen navigation
        setupScreenNavigation();
    }

    private void setupScreenNavigation() {
        updateMusicState();
        showCurrentScreen();
    }

    private void updateMusicState() {
        if ("Result".equals(currentScreen)) {
            gameViewModel.stopGameMusic();
            gameViewModel.playResultSound();
        } else {
            gameViewModel.startGameMusic();
        }
    }

    private void showCurrentScreen() {
        switch (currentScreen) {
            case "MainMenu":
                showMainMenuScreen();
                break;
            case "Quiz":
                showQuizScreen();
                break;
            case "Pause":
                showPauseScreen();
                break;
            case "Result":
                showResultScreen();
                break;
            case "Levels":
                showLevelsScreen();
                break;
            case "Settings":
                showSettingsScreen();
                break;
        }
    }

    private void navigateToScreen(String screen) {
        currentScreen = screen;
        updateMusicState();
        backgroundShapesManager.animateToScreen(screen);
        showCurrentScreen();
    }

    private void showMainMenuScreen() {
        // Initialize and show main menu fragment/view
        MainMenuFragment fragment = new MainMenuFragment();
        fragment.setCallbacks(
            () -> {
                gameViewModel.resetGame();
                navigateToScreen("Quiz");
            },
            () -> navigateToScreen("Settings"),
            () -> navigateToScreen("Levels")
        );
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit();
    }

    private void showPauseScreen() {
        PauseFragment fragment = new PauseFragment();
        fragment.setCallbacks(
            () -> navigateToScreen("Quiz"), // Resume should just go back to Quiz
            () -> {
                gameViewModel.resetGame(); // Only reset when explicitly requested
                navigateToScreen("Quiz");
            },
            () -> {
                gameViewModel.resetGame(); // Reset when going to main menu
                navigateToScreen("MainMenu");
            }
        );
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit();
    }

    private void showResultScreen() {
        ResultFragment fragment = ResultFragment.newInstance(
            gameViewModel.getScore(),
            gameViewModel.getTotalQuestions()
        );
        fragment.setCallback(() -> navigateToScreen("MainMenu"));
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit();
    }

    private void showLevelsScreen() {
        LevelsFragment fragment = new LevelsFragment();
        fragment.setLevelSelectedListener(level -> {
            if (level == 0) {
                navigateToScreen("MainMenu");
            } else {
                gameViewModel.setLevel(level);
                navigateToScreen("MainMenu");
            }
        });
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit();
    }

    private void showSettingsScreen() {
        SettingsFragment fragment = new SettingsFragment();
        fragment.setCallback(() -> navigateToScreen("MainMenu"));
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit();
    }

    private void showQuizScreen() {
        QuizFragment fragment = new QuizFragment();
        fragment.setGameViewModel(gameViewModel);
        fragment.setCallbacks(
            () -> navigateToScreen("Result"),
            () -> navigateToScreen("Pause")
        );
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameViewModel.cleanup();
    }
}
