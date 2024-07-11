package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hw1.Sensors.SensorsDetector;
import com.example.hw1.Utilities.Sound;
import com.google.android.material.button.MaterialButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private AppCompatImageView[] game_hearts;
    private MaterialButton game_arrow_left;
    private MaterialButton game_arrow_right;
    private final int totalRows=8;
    private final int totalCols=5;
    private final int totalBirdRows=7;
    private Random random = new Random();
    private int fall_interval = 3000;

    private int fast_speed = 1000;

    private int slow_speed = 3000;
    private Handler handler = new Handler();
    private Runnable fallingRunnable;
    private GameManager gameManager;
    private Sound soundPlayer;
    private SensorsDetector sensorsDec;
    public boolean isSensors = false;

    public static final String KEY_SENSOR = "KEY_SENSOR";
    public static final String KEY_SPEED="KEY_SPEED";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        soundPlayer = new Sound(this);
        gameManager = new GameManager(3);// Initialize game manager with 3 lives
        sensorsDec = new SensorsDetector(this, callBack_movement);
        setupGameBoard();
        getValuesPreviousIntent();
        hideAllBirds();
        findViews();
        startBirdFalling();

    }

    private SensorsDetector.CallBack_PlaneView callBack_movement = new SensorsDetector.CallBack_PlaneView() {
        @Override
        public void movePlaneWithSensor(int index){
            gameManager.getCells()[gameManager.getCurrentRow()][gameManager.getCurrentCol()].setVisibility(View.INVISIBLE);
            gameManager.movePlaneWithSens(index);
            gameManager.getCells()[gameManager.getCurrentRow()][gameManager.getCurrentCol()].setVisibility(View.VISIBLE);
        }

        @Override
        public void changeSpeedWithSensor(int speed) {
            fall_interval = speed;
        }
    };

    private void getValuesPreviousIntent() {
        Intent previousIntent = getIntent();
        boolean isFastMode = previousIntent.getExtras().getBoolean(KEY_SPEED);
        isSensors= previousIntent.getExtras().getBoolean(KEY_SENSOR);
        setSpeed(isFastMode);
    }

    private void setSpeed(boolean isFastMode) {
        if(isFastMode)
            fall_interval = fast_speed;
        else
            fall_interval = slow_speed;

    }
    //Find and setup view elements.
    private void findViews() {
        game_arrow_left = findViewById(R.id.game_arrow_left);
        game_arrow_right = findViewById(R.id.game_arrow_right);
        if(isSensors){
            game_arrow_left.setVisibility(View.INVISIBLE);
            game_arrow_right.setVisibility(View.INVISIBLE);
            sensorsDec.startX();
        }
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

    // Setup the game board by finding and assigning all cell views.
    private void setupGameBoard() {
        ImageView[][] cells = new ImageView[totalRows][totalCols];
        gameManager.setCells(cells);
        for (int i = 0; i < totalRows; i++) {
            for (int j = 0; j < totalCols; j++) {
                int resID = getResources().getIdentifier("cell_" + i + j, "id", getPackageName());
                gameManager.getCells()[i][j] = findViewById(resID);
            }
        }
    }

    //Hide all bird images
    private void hideAllBirds() {
        for (int i = 0; i < totalBirdRows; i++) {
            for (int j = 0; j < totalCols; j++) {
                int resID = getResources().getIdentifier("cell_" + i + j, "id", getPackageName());
                ImageView imageView = findViewById(resID);
                imageView.setVisibility(View.INVISIBLE);
            }
        }
    }

    //Start the bird falling logic, which will be repeatedly executed.
    private void startBirdFalling() {
        fallingRunnable = new Runnable() {
            @Override
            public void run() {
                int col = random.nextInt(3);
                boolean isBird = random.nextBoolean();
                for (int row = 0; row < totalBirdRows; row++) {
                    final int finalRow = row;
                    final int finalCol = col;
                    final boolean finalIsBird = isBird;

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (finalRow > 0) {
                                gameManager.getCells()[finalRow - 1][finalCol].setVisibility(View.INVISIBLE);
                            }
                            gameManager.getCells()[finalRow][finalCol].setImageResource(finalIsBird ? R.drawable.bird2 : R.drawable.coin); // Set the image resource
                            gameManager.getCells()[finalRow][finalCol].setVisibility(View.VISIBLE);
                            if ((finalRow+1) == gameManager.getCurrentRow() && finalCol == gameManager.getCurrentCol()) {
                                checkType(finalIsBird ? 0 : 1);
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

                handler.postDelayed(this, fall_interval);
            }
        };

        handler.post(fallingRunnable);
    }

    //Handle collision event when a bird hits the plane.
    private void handleCollision() {
        Toast.makeText(this, "Collision", Toast.LENGTH_SHORT).show();

        //Add vibration when collision
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (v != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                VibrationEffect effect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE);
                v.vibrate(effect);
            }
        }
        playCrashSound();
        gameManager.decreaseLive();
        game_hearts[gameManager.getLives()].setVisibility(View.INVISIBLE);

        if (gameManager.getLives() == 0) {
            Toast.makeText(this, "You lose!", Toast.LENGTH_SHORT).show();
            handler.removeCallbacks(fallingRunnable);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    resetGame();
                }
            }, 6000);//Wait 6 sec until game starts over
        }
    }

    //Handle collision event when the plane collects coin.
    private void handleScore() {
        Toast.makeText(this, "Great!", Toast.LENGTH_SHORT).show();
        gameManager.addScore();
        playCoinSound();
    }

    private void playCrashSound() {
       soundPlayer.playSound(R.raw.crash);
    }

    private void playCoinSound() {
        soundPlayer.playSound(R.raw.coin_pickup);
    }


    //Reset the game to its initial state.
    private void resetGame() {
        gameManager.resetLives();
        gameManager.resetPosition();
        for (AppCompatImageView heart : game_hearts) {
            heart.setVisibility(View.VISIBLE);
        }
        hideAllBirds();
        startBirdFalling();
    }

    private void checkType(int type) {
        if(type == 0) {//0 represents Bird
            handleCollision();
        }
        else{//Coin
            handleScore();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isSensors) {
            sensorsDec.stopX();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isSensors) {
            sensorsDec.stopX();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(fallingRunnable);
    }
}