package com.example.shapegame_java;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.SeekBar;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {
    private Runnable onReturnToMenu;
    private PreferencesManager preferencesManager;
    private SoundManager soundManager;
    private TextView volumeText;

    public void setCallback(Runnable onReturnToMenu) {
        this.onReturnToMenu = onReturnToMenu;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferencesManager = new PreferencesManager(requireContext());
        soundManager = new SoundManager(requireContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        ImageButton backButton = view.findViewById(R.id.backButton);
        volumeText = view.findViewById(R.id.volumeText);
        SeekBar volumeSlider = view.findViewById(R.id.volumeSlider);

        // Set initial volume
        float currentVolume = preferencesManager.getVolume();
        volumeSlider.setProgress((int)(currentVolume * 100));
        updateVolumeText(currentVolume);

        backButton.setOnClickListener(v -> {
            if (onReturnToMenu != null) onReturnToMenu.run();
        });

        volumeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float volume = progress / 100f;
                updateVolumeText(volume);
                preferencesManager.saveVolume(volume);
                soundManager.updateVolume();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (soundManager != null) {
            soundManager.release();
        }
    }

    private void updateVolumeText(float volume) {
        volumeText.setText(String.format("Volume: %d%%", (int)(volume * 100)));
    }
}
