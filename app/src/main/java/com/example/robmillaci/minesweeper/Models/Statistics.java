package com.example.robmillaci.minesweeper.Models;

import android.util.Log;

public class Statistics {
    private String title;
    private int veryEasyTotal;
    private int EasyTotal;
    private int normalTotal;
    private int hardTotal;
    private int veryHardTotal;

    private int timeveryEasyTotal_minutes;
    private int timeveryEasyTotal_seconds;
    private int timeEasyTotal_minutes;
    private int timeEasyTotal_seconds;
    private int timenormalTotal_minutes;
    private int timenormalTotal_seconds;
    private int timehardTotal_minutes;
    private int timehardTotal_seconds;
    private int timeveryHardTotal_minutes;
    private int timeveryHardTotal_seconds;

    private int total;

    public int getVeryEasyTotalTime() {
        return (timeveryEasyTotal_minutes * 60) + timeveryEasyTotal_seconds;
    }

    public int getEasyTotalTime() {
        return (timeEasyTotal_minutes * 60) + timeEasyTotal_seconds;
    }

    public int getNormalTotalTime() {
        return (timenormalTotal_minutes * 60) + timenormalTotal_seconds;
    }

    public int getHardTotalTime() {
        return (timehardTotal_minutes * 60) + timehardTotal_seconds;
    }

    public int getVeryhardTotalTime() {
        return (timeveryHardTotal_minutes * 60) + timeveryHardTotal_seconds;
    }

    public String getTimetotal() {
        int totalminutes = timeveryEasyTotal_minutes + timeEasyTotal_minutes + timenormalTotal_minutes + timehardTotal_minutes + timeveryHardTotal_minutes;
        int totalseconds = timeveryEasyTotal_seconds + timeEasyTotal_seconds + timenormalTotal_seconds + timehardTotal_seconds + timeveryHardTotal_seconds;

        totalminutes = totalminutes + Math.round(totalseconds / 60);
        totalseconds = totalseconds % 60;

        return String.valueOf(totalminutes) + "m : " + String.valueOf(totalseconds) + "s";
    }

    public boolean isTherePlayedData() {
        return total > 0;
    }

    public boolean isThereGamesWonData() {
        return veryEasyTotal + EasyTotal + normalTotal + hardTotal + veryHardTotal > 0;
    }

    public Statistics(String title) {
        this.title = title;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getVeryEasyTotal() {
        return veryEasyTotal;
    }

    public void setVeryEasyTotal(int veryEasyTotal) {
        this.veryEasyTotal = veryEasyTotal;
    }

    public int getEasyTotal() {
        return EasyTotal;
    }

    public void setEasyTotal(int easyTotal) {
        EasyTotal = easyTotal;
    }

    public int getNormalTotal() {
        return normalTotal;
    }

    public void setNormalTotal(int normalTotal) {
        this.normalTotal = normalTotal;
    }

    public int getHardTotal() {
        return hardTotal;
    }

    public void setHardTotal(int hardTotal) {
        this.hardTotal = hardTotal;
    }

    public int getVeryHardTotal() {
        return veryHardTotal;
    }

    public void setVeryHardTotal(int veryHardTotal) {
        this.veryHardTotal = veryHardTotal;
    }

    private int getTimeveryEasyTotal_minutes() {
        return timeveryEasyTotal_minutes;
    }

    public void setTimeveryEasyTotal_minutes(int timeveryEasyTotal_minutes) {
        this.timeveryEasyTotal_minutes = timeveryEasyTotal_minutes;
    }

    private int getTimeveryEasyTotal_seconds() {
        return timeveryEasyTotal_seconds;
    }

    public void setTimeveryEasyTotal_seconds(int timeveryEasyTotal_seconds) {
        this.timeveryEasyTotal_seconds = timeveryEasyTotal_seconds;
    }

    private int getTimeEasyTotal_minutes() {
        return timeEasyTotal_minutes;
    }

    public void setTimeEasyTotal_minutes(int timeEasyTotal_minutes) {
        this.timeEasyTotal_minutes = timeEasyTotal_minutes;
    }
    private int getTimeEasyTotal_seconds() {
        return timeEasyTotal_seconds;
    }

    public void setTimeEasyTotal_seconds(int timeEasyTotal_seconds) {
        this.timeEasyTotal_seconds = timeEasyTotal_seconds;
    }
    private int getTimenormalTotal_minutes() {
        return timenormalTotal_minutes;
    }

    public void setTimenormalTotal_minutes(int timenormalTotal_minutes) {
        this.timenormalTotal_minutes = timenormalTotal_minutes;
    }

    private int getTimenormalTotal_seconds() {
        return timenormalTotal_seconds;
    }

    public void setTimenormalTotal_seconds(int timenormalTotal_seconds) {
        this.timenormalTotal_seconds = timenormalTotal_seconds;
    }

    private int getTimehardTotal_minutes() {
        return timehardTotal_minutes;
    }

    public void setTimehardTotal_minutes(int timehardTotal_minutes) {
        this.timehardTotal_minutes = timehardTotal_minutes;
    }

    private int getTimehardTotal_seconds() {
        return timehardTotal_seconds;
    }

    public void setTimehardTotal_seconds(int timehardTotal_seconds) {
        this.timehardTotal_seconds = timehardTotal_seconds;
    }

    private int getTimeveryHardTotal_minutes() {
        return timeveryHardTotal_minutes;
    }

    public void setTimeveryHardTotal_minutes(int timeveryHardTotal_minutes) {
        this.timeveryHardTotal_minutes = timeveryHardTotal_minutes;
    }

    private int getTimeveryHardTotal_seconds() {
        return timeveryHardTotal_seconds;
    }

    public void setTimeveryHardTotal_seconds(int timeveryHardTotal_seconds) {
        this.timeveryHardTotal_seconds = timeveryHardTotal_seconds;
    }

    public String getVeryEasyTime() {
        int seconds = getTimeveryEasyTotal_seconds();
        int minutes = getTimeveryEasyTotal_minutes();

        minutes = minutes + (seconds / 60);
        seconds = seconds % 60;

        return minutes + " m : " + seconds + "s";
    }

    public String getEasyTime() {
        int seconds = getTimeEasyTotal_seconds();
        int minutes = getTimeEasyTotal_minutes();

        minutes = minutes + (seconds / 60);
        seconds = seconds % 60;

        return minutes + " m : " + seconds + "s";
    }

    public String getNormalTime() {
        int seconds = getTimenormalTotal_seconds();
        int minutes = getTimenormalTotal_minutes();

        Log.d("CHECKTHETIMERFUCK", "getNormalTime: " + "minutes are " + minutes);
        Log.d("CHECKTHETIMERFUCK", "getNormalTime: " + "seconds are " + seconds);

        minutes = minutes + (seconds / 60);
        seconds = seconds % 60;


        Log.d("CHECKTHETIMERFUCK", "getNormalTime: " + "minutes are now " + minutes);
        Log.d("CHECKTHETIMERFUCK", "getNormalTime: " + "seconds are now " + seconds);

        return minutes + " m : " + seconds + "s";
    }

    public String getHardTime() {
        int seconds = getTimehardTotal_seconds();
        int minutes = getTimehardTotal_minutes();

        minutes = minutes + (seconds / 60);
        seconds = seconds % 60;

        return minutes + " m : " + seconds + "s";
    }

    public String getVeryhardTime() {
        int seconds = getTimeveryHardTotal_seconds();
        int minutes = getTimeveryHardTotal_minutes();

        minutes = minutes + (seconds / 60);
        seconds = seconds % 60;

        return minutes + " m : " + seconds + "s";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public boolean isThereTimeData() {
        return timeveryEasyTotal_minutes +
                timeveryEasyTotal_seconds +
                timeEasyTotal_minutes +
                timeEasyTotal_seconds +
                timenormalTotal_minutes +
                timenormalTotal_seconds +
                timehardTotal_minutes +
                timehardTotal_seconds +
                timeveryHardTotal_minutes +
                timeveryHardTotal_seconds > 0;
    }

    @Override
    public String toString() {
        return String.valueOf(timeveryEasyTotal_minutes +
                timeveryEasyTotal_seconds +
                timeEasyTotal_minutes +
                timeEasyTotal_seconds +
                timenormalTotal_minutes +
                timenormalTotal_seconds +
                timehardTotal_minutes +
                timehardTotal_seconds +
                timeveryHardTotal_minutes +
                timeveryHardTotal_seconds);
    }
}
