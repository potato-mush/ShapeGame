package com.example.shapegame_java;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import java.util.Random;

public class BackgroundShapesManager {
    private final ImageView[] shapes;
    private final ConstraintLayout rootLayout;
    private final Random random;
    private String currentScreen;
    private static final int SHAPE_SIZE_DP = 140;
    private static final int ANIMATION_DURATION = 1000;
    private static final int SHAPE_DELAY = 150;
    private static final float[] SHAPE_SCALES = {1.0f, 1.0f, 1.0f, 1.0f};
    private static final float[] SHAPE_ROTATIONS = {65f, 45f, 45f, 60f};

    public BackgroundShapesManager(Context context, ConstraintLayout rootLayout) {
        this.rootLayout = rootLayout;
        this.random = new Random();
        this.shapes = new ImageView[4];
        
        int shapeSizePx = (int) (context.getResources().getDisplayMetrics().density * SHAPE_SIZE_DP);
        
        int[] shapeDrawables = {
            R.drawable.square,
            R.drawable.circle,
            R.drawable.triangle,
            R.drawable.star
        };

        for (int i = 0; i < shapes.length; i++) {
            shapes[i] = new ImageView(context);
            shapes[i].setId(View.generateViewId());
            shapes[i].setImageResource(shapeDrawables[i]);
            shapes[i].setAlpha(1.0f);
            shapes[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
            shapes[i].setElevation(-1f);
            shapes[i].setScaleX(SHAPE_SCALES[i]);
            shapes[i].setScaleY(SHAPE_SCALES[i]);
            shapes[i].setRotation(SHAPE_ROTATIONS[i]);
            
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                shapeSizePx,
                shapeSizePx
            );
            rootLayout.addView(shapes[i], 0, params);
        }

        rootLayout.post(this::initializeShapePositions);
    }

    private void initializeShapePositions() {
        int width = rootLayout.getWidth();
        int height = rootLayout.getHeight();

        float[] positions = calculatePositions("MainMenu", width, height);
        for (int i = 0; i < shapes.length; i++) {
            shapes[i].setX(positions[i * 2]);
            shapes[i].setY(positions[i * 2 + 1]);
        }
    }

    public void animateToScreen(String screenName) {
        if (screenName.equals(currentScreen)) return;
        String previousScreen = currentScreen;
        currentScreen = screenName;

        int width = rootLayout.getWidth();
        int height = rootLayout.getHeight();

        AnimatorSet animatorSet = new AnimatorSet();
        Animator[] animators = new Animator[shapes.length * 2];

        float[] positions = calculatePositions(screenName, width, height);
        float[] currentPositions = calculatePositions(previousScreen != null ? previousScreen : "MainMenu", width, height);

        for (int i = 0; i < shapes.length; i++) {
            float startX = currentPositions[i * 2];
            float startY = currentPositions[i * 2 + 1];
            float endX = positions[i * 2];
            float endY = positions[i * 2 + 1];

            ObjectAnimator xAnimator = ObjectAnimator.ofFloat(
                shapes[i],
                View.X,
                startX,
                (startX + endX) / 2,
                endX
            );
            
            ObjectAnimator yAnimator = ObjectAnimator.ofFloat(
                shapes[i],
                View.Y,
                startY,
                Math.min(startY, endY) - 100,
                endY
            );

            xAnimator.setStartDelay(i * SHAPE_DELAY);
            yAnimator.setStartDelay(i * SHAPE_DELAY);

            xAnimator.setInterpolator(new SlideInterpolator());
            yAnimator.setInterpolator(new SlideInterpolator());

            animators[i * 2] = xAnimator;
            animators[i * 2 + 1] = yAnimator;
        }

        animatorSet.playTogether(animators);
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.start();
    }

    private float[] calculatePositions(String screenName, int width, int height) {
        float[] positions = new float[8];

        switch (screenName) {
            case "MainMenu":
                // Shape 1 (index 0)
                positions[0] = width - 190;
                positions[1] = height - 720;
                // Shape 2 (index 1)
                positions[2] = 10;
                positions[3] = height / 2f;
                // Shape 3 (index 2)
                positions[4] = 10;
                positions[5] = height - 420;
                // Shape 4 (index 3)
                positions[6] = width / 3f + 80;
                positions[7] = height - 240;
                break;

            case "Settings":
                // Shape 1
                positions[0] = width / 2f + 100;
                positions[1] = height / 2f + 60;
                // Shape 2
                positions[2] = 100;
                positions[3] = height / 2f + 80;
                // Shape 3
                positions[4] = 100;
                positions[5] = height - 500;
                // Shape 4
                positions[6] = width / 2f + 100;
                positions[7] = height - 500;
                break;

            case "Levels":
                // Shape 1
                positions[0] = width / 2f + 100;
                positions[1] = height / 2f + 60;
                // Shape 2
                positions[2] = 100;
                positions[3] = height / 2f + 80;
                // Shape 3
                positions[4] = 100;
                positions[5] = height - 500;
                // Shape 4
                positions[6] = width / 2f + 100;
                positions[7] = height - 500;
                break;

            default:
                // Use MainMenu positions as default
                positions[0] = width - 90;
                positions[1] = height - 420;
                positions[2] = 10;
                positions[3] = height / 2f;
                positions[4] = 10;
                positions[5] = height - 200;
                positions[6] = width / 3f + 80;
                positions[7] = height - 200;
                break;
        }

        return positions;
    }

    private static class SlideInterpolator extends AccelerateDecelerateInterpolator {
        private static final float DAMPING_RATIO = 0.9f;
        private static final float STIFFNESS = 100f;

        @Override
        public float getInterpolation(float input) {
            float t = super.getInterpolation(input);
            return (float) (1 - Math.pow(1 - t, 3));
        }
    }
}
