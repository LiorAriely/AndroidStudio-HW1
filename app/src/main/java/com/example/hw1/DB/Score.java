package com.example.hw1.DB;

public class Score {
    private int score = 0;
    private String userName ="";
    private double latitude = 0.0;
    private double longitude = 0.0;

    public Score(){
    }

    public int getScore() {
        return score;
    }

    public Score setScore(int score) {
        this.score = score;
        return this;
    }

    public String getUser() {
        return userName;
    }

    public Score setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public double getLatitude() {
        return latitude;
    }

    public Score setLatitude(double lat) {
        this.latitude = lat;
        return this;
    }

    public double getLongitude() {
        return longitude;
    }

    public Score setLongitude(double lon) {
        this.longitude = lon;
        return this;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("User:").append(userName);
        sb.append("\nScore:").append(score);
        return sb.toString();

    }
}