package com.example.hw1;

import android.view.View;
import android.widget.ImageView;

import java.util.Random;

public class GameManager {

    private int currentRow = 5;
    private int currentCol = 1;
    private int lives = 3;
    private Random random = new Random();
    private ImageView[][] cells;

    public GameManager(int initialLives) {
        if (initialLives > 0 && initialLives <= 4) {
            lives = initialLives;
        }

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
        if (currentCol < 2) {
            cells[currentRow][currentCol].setVisibility(View.INVISIBLE);
            currentCol++; // Move right
            cells[currentRow][currentCol].setVisibility(View.VISIBLE);
        }
    }



    public int getCurrentRow() {
        return currentRow;
    }

    public int getCurrentCol() {
        return currentCol;
    }
}