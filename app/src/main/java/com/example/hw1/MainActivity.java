package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private AppCompatImageView[] game_hearts;
    private MaterialButton game_arrow_left;
    private MaterialButton game_arrow_right;



    private Random random = new Random();
    private final int FALL_INTERVAL = 2000;
    private Handler handler = new Handler();
    private Runnable birdFallingRunnable;
    private GameManager gameManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameManager = new GameManager(3);
        setupGameBoard();
        hideAllBirds();

        findViews();

        startBirdFalling();

    }

    private void findViews() {
        game_arrow_left = findViewById(R.id.game_arrow_left);
        game_arrow_right = findViewById(R.id.game_arrow_right);
        game_hearts = new AppCompatImageView[]{
                findViewById(R.id.game_IMG_heart1),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart3)
        };

        game_arrow_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.movePlaneLeft();
            }
        });

        game_arrow_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameManager.movePlaneRight();
            }
        });

    }

    private void setupGameBoard() {
        ImageView[][] cells = new ImageView[6][3];
        gameManager.setCells(cells);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                int resID = getResources().getIdentifier("cell_" + i + j, "id", getPackageName());
                gameManager.getCells()[i][j] = findViewById(resID);
            }
        }
    }

    private void hideAllBirds() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                int resID = getResources().getIdentifier("cell_" + i + j, "id", getPackageName());
                ImageView imageView = findViewById(resID);
                imageView.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void startBirdFalling() {
        birdFallingRunnable = new Runnable() {
            @Override
            public void run() {
                int col = random.nextInt(3);
                for (int row = 0; row < 5; row++) {
                    final int finalRow = row;
                    final int finalCol = col;

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (finalRow > 0) {
                                gameManager.getCells()[finalRow - 1][finalCol].setVisibility(View.INVISIBLE);
                            }
                            gameManager.getCells()[finalRow][finalCol].setVisibility(View.VISIBLE);
                            if ((finalRow+1) == gameManager.getCurrentRow() && finalCol == gameManager.getCurrentCol()) {
                                handleCollision();
                            }
                        }
                    }, finalRow * 500);


                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            gameManager.getCells()[finalRow][finalCol].setVisibility(View.INVISIBLE);
                        }
                    }, (finalRow + 1) * 500);
                }


                handler.postDelayed(this, FALL_INTERVAL);
            }
        };

        handler.post(birdFallingRunnable);
    }

    private void handleCollision() {
        Toast.makeText(this, "collision", Toast.LENGTH_SHORT).show();

        //Add vibration when collision
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (v != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                VibrationEffect effect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE);
                v.vibrate(effect);
            }
        }
        gameManager.decreaseLive();
        game_hearts[gameManager.getLives()].setVisibility(View.INVISIBLE);

        if (gameManager.getLives() == 0) {
            Toast.makeText(this, "You lose!", Toast.LENGTH_SHORT).show();
            handler.removeCallbacks(birdFallingRunnable);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(birdFallingRunnable);
    }
}