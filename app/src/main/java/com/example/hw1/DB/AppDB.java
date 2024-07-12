package com.example.hw1.DB;

import java.util.ArrayList;

public class AppDB {//list of result
    private ArrayList<Score> results;
    private final int LIMIT_TOP10 = 10;

    public AppDB() {
        this.results = new ArrayList<>();
    }

    public ArrayList<Score> getScores() {
        results.sort((r1, r2) -> r2.getScore() - r1.getScore());
        if (results.size() == LIMIT_TOP10) {
            results.remove(LIMIT_TOP10 - 1);
        }

        return results;
    }

    public AppDB setScores(ArrayList<Score> results) {
        this.results = results;
        return this;
    }
}