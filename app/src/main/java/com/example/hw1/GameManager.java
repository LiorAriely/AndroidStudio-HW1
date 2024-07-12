package com.example.hw1;

import android.view.View;
import android.widget.ImageView;

import com.example.hw1.DB.AppDB;
import com.example.hw1.DB.Score;
import com.example.hw1.Utilities.AppSP;
import com.google.gson.Gson;

import java.util.Random;

public class GameManager {

    private int currentRow = 7;
    private int currentCol = 1;
    private int initialLives;
    private int score = 0;
    private final String SCORE = "scores";
    private int lives = 3;
    private String userName;
    private Random random = new Random();
    private ImageView[][] cells;

    public GameManager(int initialLives,String userName) {
        this.userName=userName;
        this.score = 0;
        this.initialLives = initialLives;
        if (initialLives > 0 && initialLives < 4) {
            this.lives = initialLives;
        }
        else
            this.lives=3;

    }

    public ImageView[][] getCells() {
        return cells;
    }

    public void setCells(ImageView[][] cells) {
        this.cells = cells;
    }

    public void decreaseLive() {
        if (lives > 0) {
            lives--;
        }
    }

    public int getLives() {
        return lives;
    }

    public void movePlaneLeft() {
        if (currentCol > 0) {
            cells[currentRow][currentCol].setVisibility(View.INVISIBLE);
            currentCol--; // Move left
            cells[currentRow][currentCol].setVisibility(View.VISIBLE);
        }
    }

    public void movePlaneRight() {
        if (currentCol < 4) {
            cells[currentRow][currentCol].setVisibility(View.INVISIBLE);
            currentCol++; // Move right
            cells[currentRow][currentCol].setVisibility(View.VISIBLE);
        }
    }

    public int movePlaneWithSens(int i){
        if(i==1&& getCurrentCol() < 4)
            currentCol++; // Move right
        else if(i==-1&& getCurrentCol() > 0)
            currentCol--; // Move left
        return getCurrentCol();
    }

    //Reset the number of lives to the initial value.
    public void resetLives() {
        this.lives = initialLives;
    }

    //Reset the plane position to the initial position.
    public void resetPosition() {
        cells[currentRow][currentCol].setVisibility(View.INVISIBLE);
        currentRow = 7;
        currentCol = 1;
        cells[currentRow][currentCol].setVisibility(View.VISIBLE);
    }
    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }

    public void addScore() {
        score += 10;
    }

    public int getScore() {
        return score;
    }

    public void save(double longitude, double latitude) {
        AppDB appDB;
        String json = AppSP.getInstance().getStrSP(SCORE,"");
        appDB = new Gson().fromJson(json, AppDB.class);
        if(appDB==null) {
            appDB = new AppDB();
        }
        Score scr = createScore(longitude,latitude);
        appDB.getScores().add(scr);
        AppSP.getInstance().putString(SCORE,new Gson().toJson(appDB));
    }

    private Score createScore(double longitude, double latitude){
        return new Score().setUserName(userName).setScore(score).setLatitude(latitude).setLongitude(longitude);
    }
}