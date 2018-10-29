package com.example.robmillaci.minesweeper.Tiles;

import java.util.ArrayList;

public class Tile {
    private ArrayList<Integer> neighbours;
    private int neighbourbombs;
    private int tileImageint;
    private boolean isRevealed = false;
    private boolean isFlagged = false;

    Tile() {
        this.neighbours = new ArrayList<>();
        this.neighbourbombs = 99;
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    public int getNeighbourbombs() {
        return neighbourbombs;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public int getTileImageint() {
        return tileImageint;
    }

    public void setTileImageint(int tileImageint) {
        this.tileImageint = tileImageint;
    }

    public void setNeighbourbombs(int neighbourbombs) {
        this.neighbourbombs = neighbourbombs;
        this.tileImageint = neighbourbombs;
    }

    public void setNeighbours(ArrayList<Integer> neighbours) {
        this.neighbours = neighbours;
    }

    public ArrayList<Integer> getNeighbours() {
        return neighbours;
    }

    @Override
    public String toString() {
        return this instanceof BombTile ? "Tile : Bomb. Neighbours" + neighbours : "Tile : empty. Neighbours " + neighbours;
    }
}
