package com.example.shapegame_java;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundManager {
    private MediaPlayer gameMusic;
    private MediaPlayer correctSound;
    private MediaPlayer wrongSound;
    private MediaPlayer resultSound;
    private final PreferencesManager preferencesManager;
    private final Context context;

    public SoundManager(Context context) {
        this.context = context;
        this.preferencesManager = new PreferencesManager(context);
        initializeSounds();
    }

    private void initializeSounds() {
        gameMusic = MediaPlayer.create(context, R.raw.gamesound);
        if (gameMusic != null) {
            gameMusic.setLooping(true);
            float volume = preferencesManager.getVolume();
            gameMusic.setVolume(volume, volume);
        }
        
        correctSound = MediaPlayer.create(context, R.raw.correct);
        wrongSound = MediaPlayer.create(context, R.raw.wrong);
        resultSound = MediaPlayer.create(context, R.raw.resultsound);
        updateVolume();
    }

    public void updateVolume() {
        float volume = preferencesManager.getVolume();
        if (gameMusic != null) gameMusic.setVolume(volume, volume);
        if (correctSound != null) correctSound.setVolume(volume, volume);
        if (wrongSound != null) wrongSound.setVolume(volume, volume);
        if (resultSound != null) resultSound.setVolume(volume, volume);
    }

    private void playSoundEffect(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            float volume = preferencesManager.getVolume();
            mediaPlayer.setVolume(volume, volume);
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.seekTo(0);
            }
            mediaPlayer.start();
        }
    }

    public void startGameMusic() {
        try {
            if (gameMusic != null) {
                float volume = preferencesManager.getVolume();
                gameMusic.setVolume(volume, volume);
                if (!gameMusic.isPlaying()) {
                    gameMusic.start();
                }
            }
        } catch (IllegalStateException e) {
            if (gameMusic != null) {
                gameMusic.release();
            }
            gameMusic = MediaPlayer.create(context, R.raw.gamesound);
            if (gameMusic != null) {
                gameMusic.setLooping(true);
                float volume = preferencesManager.getVolume();
                gameMusic.setVolume(volume, volume);
                gameMusic.start();
            }
        }
    }

    public void stopGameMusic() {
        if (gameMusic != null && gameMusic.isPlaying()) {
            gameMusic.pause();
            gameMusic.seekTo(0);
        }
    }

    public void playCorrectSound() {
        playSoundEffect(correctSound);
    }

    public void playWrongSound() {
        playSoundEffect(wrongSound);
    }

    public void playResultSound() {
        playSoundEffect(resultSound);
    }

    public void release() {
        if (gameMusic != null) {
            gameMusic.release();
            gameMusic = null;
        }
        if (correctSound != null) {
            correctSound.release();
            correctSound = null;
        }
        if (wrongSound != null) {
            wrongSound.release();
            wrongSound = null;
        }
        if (resultSound != null) {
            resultSound.release();
            resultSound = null;
        }
    }
}
