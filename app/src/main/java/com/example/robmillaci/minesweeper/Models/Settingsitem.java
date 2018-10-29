package com.example.robmillaci.minesweeper.Models;

import android.widget.ImageView;

public class Settingsitem {
    private String title;
    private String description;
    private ImageView volicon;
    private ImageView vibIcon;
    private int clickCount = 0;

    public String getTitle() {
        return title;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        if (clickCount == 4) {
            this.clickCount = 0;
        } else {
            this.clickCount = clickCount;
        }
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageView getVolicon() {
        return volicon;
    }

    public void setVolicon(ImageView volicon) {
        this.volicon = volicon;
    }

    public ImageView getVibIcon() {
        return vibIcon;
    }

    public void setVibIcon(ImageView vibIcon) {
        this.vibIcon = vibIcon;
    }
}
