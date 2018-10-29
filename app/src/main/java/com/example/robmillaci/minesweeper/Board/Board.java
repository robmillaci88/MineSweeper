package com.example.robmillaci.minesweeper.Board;

import android.annotation.SuppressLint;

import com.example.robmillaci.minesweeper.Tiles.BombTile;
import com.example.robmillaci.minesweeper.Tiles.NumberTile;
import com.example.robmillaci.minesweeper.Tiles.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Integer, Tile> gameBoard;

    public Board() {
        this.gameBoard = createGameBoard();
        setNeighbourbombs();
    }

    private void setNeighbourbombs() {
        for (int i = 0; i < gameBoard.size(); i++) {
            Tile t = gameBoard.get(i);
            t.setNeighbourbombs(detectedNeighbourbombs(t));
        }
    }


    public Map<Integer, Tile> getGameBoard() {
        return gameBoard;
    }

    private Map<Integer, Tile> createGameBoard() {
        final Builder builder = new Builder();

        //create BoardUtils.NUM_BOARD_TILES random numbers
        ArrayList<Integer> randomNumbers = new ArrayList<>();

        for (int i = 0; i < BoardUtils.NUM_BOARD_TILES; i++) {
            randomNumbers.add(i);
        }

        //shuffle the array list 4 times to increase randomness
        for (int i = 0; i < 5; i++) {
            Collections.shuffle(randomNumbers);
        }

        ArrayList<Integer> bombsAdded = new ArrayList<>();
        for (int i = 0; i < BoardUtils.NUM_BOMBS; i++) {
            int randomNumber = randomNumbers.get(i);
            Tile thisTile = new BombTile();
            builder.setPiece(thisTile, randomNumber);
            thisTile.setNeighbours(createNeighbours(randomNumber));
            thisTile.setTileImageint(-1);
            bombsAdded.add(randomNumber);
        }

        for (int i = 0; i < BoardUtils.NUM_BOARD_TILES; i++) {
            if (!bombsAdded.contains(i)) {
                Tile thisTile = new NumberTile();
                builder.setPiece(thisTile, i);
                thisTile.setNeighbours(createNeighbours(i));

                thisTile.setTileImageint(thisTile.getNeighbourbombs());
            }
        }
        return builder.build();
    }

    private int detectedNeighbourbombs(Tile t) {
        ArrayList<Integer> neighbours = t.getNeighbours();
        int bombCount = 0;
        for (int i : neighbours) {
            if (gameBoard.get(i) instanceof BombTile) {
                bombCount++;
            }
        }
        return bombCount;
    }

    private ArrayList<Integer> createNeighbours(int position) {
        if (BoardUtils.isFirstColumn(position) && BoardUtils.isFirstRow(position)) {
            ArrayList<Integer> neighbours = new ArrayList<>();
            neighbours.add(position + 1);
            neighbours.add(position + BoardUtils.BOARD_TILES_PER_ROW);
            neighbours.add(position + BoardUtils.BOARD_TILES_PER_ROW + 1);
            return neighbours;
        } else if (BoardUtils.isFirstColumn(position) && !BoardUtils.isFirstRow(position) && !BoardUtils.isLastRow(position)) {
            ArrayList<Integer> neighbours = new ArrayList<>();
            neighbours.add(position + 1);
            neighbours.add(position + BoardUtils.BOARD_TILES_PER_ROW);
            neighbours.add(position + BoardUtils.BOARD_TILES_PER_ROW + 1);
            neighbours.add(position - BoardUtils.BOARD_TILES_PER_ROW + 1);
            neighbours.add(position - BoardUtils.BOARD_TILES_PER_ROW);
            return neighbours;
        } else if (BoardUtils.isFirstColumn(position) && BoardUtils.isLastRow(position)) {
            ArrayList<Integer> neighbours = new ArrayList<>();
            neighbours.add(position + 1);
            neighbours.add(position - BoardUtils.BOARD_TILES_PER_ROW);
            neighbours.add(position - (BoardUtils.BOARD_TILES_PER_ROW - 1));
            return neighbours;
        } else if (BoardUtils.isLastColumn(position) && BoardUtils.isFirstRow(position)) {
            ArrayList<Integer> neighbours = new ArrayList<>();
            neighbours.add(position - 1);
            neighbours.add(position + BoardUtils.BOARD_TILES_PER_ROW);
            neighbours.add(position + BoardUtils.BOARD_TILES_PER_ROW - 1);
            return neighbours;
        } else if (BoardUtils.isLastColumn(position) && !BoardUtils.isFirstRow(position) && !BoardUtils.isLastRow(position)) {
            ArrayList<Integer> neighbours = new ArrayList<>();
            neighbours.add(position - 1);
            neighbours.add(position - BoardUtils.BOARD_TILES_PER_ROW);
            neighbours.add(position - BoardUtils.BOARD_TILES_PER_ROW - 1);
            neighbours.add(position + BoardUtils.BOARD_TILES_PER_ROW - 1);
            neighbours.add(position + BoardUtils.BOARD_TILES_PER_ROW);
            return neighbours;
        } else if (BoardUtils.isLastColumn(position) && BoardUtils.isLastRow(position)) {
            ArrayList<Integer> neighbours = new ArrayList<>();
            neighbours.add(position - 1);
            neighbours.add(position - BoardUtils.BOARD_TILES_PER_ROW);
            neighbours.add(position - BoardUtils.BOARD_TILES_PER_ROW - 1);
            return neighbours;
        } else if (BoardUtils.isLastColumn(position)) {
            ArrayList<Integer> neighbours = new ArrayList<>();
            neighbours.add(position + 1);
            neighbours.add(position - 1);
            neighbours.add(position + BoardUtils.BOARD_TILES_PER_ROW);
            neighbours.add(position + BoardUtils.BOARD_TILES_PER_ROW);
            neighbours.add(position + BoardUtils.BOARD_TILES_PER_ROW);
            return neighbours;
        } else if (BoardUtils.isFirstRow(position)) {
            ArrayList<Integer> neighbours = new ArrayList<>();
            neighbours.add(position - 1);
            neighbours.add(position + 1);
            neighbours.add(position + BoardUtils.BOARD_TILES_PER_ROW);
            neighbours.add(position + BoardUtils.BOARD_TILES_PER_ROW + 1);
            neighbours.add(position + BoardUtils.BOARD_TILES_PER_ROW - 1);
            return neighbours;
        } else if (BoardUtils.isLastRow(position) && BoardUtils.isFirstColumn(position)) {
            ArrayList<Integer> neighbours = new ArrayList<>();
            neighbours.add(position + 1);
            neighbours.add(position - BoardUtils.BOARD_TILES_PER_ROW);
            neighbours.add(position - BoardUtils.BOARD_TILES_PER_ROW + 1);
            return neighbours;
        } else if (BoardUtils.isLastRow(position) && !BoardUtils.isLastColumn(position)) {
            ArrayList<Integer> neighbours = new ArrayList<>();
            neighbours.add(position - 1);
            neighbours.add(position + 1);
            neighbours.add(position - BoardUtils.BOARD_TILES_PER_ROW);
            neighbours.add(position - BoardUtils.BOARD_TILES_PER_ROW - 1);
            neighbours.add(position - BoardUtils.BOARD_TILES_PER_ROW + 1);
            return neighbours;
        } else {
            ArrayList<Integer> neighbours = new ArrayList<>();
            neighbours.add(position + 1);
            neighbours.add(position - 1);
            neighbours.add(position - BoardUtils.BOARD_TILES_PER_ROW);
            neighbours.add(position - BoardUtils.BOARD_TILES_PER_ROW - 1);
            neighbours.add(position - BoardUtils.BOARD_TILES_PER_ROW + 1);
            neighbours.add(position + BoardUtils.BOARD_TILES_PER_ROW);
            neighbours.add(position + BoardUtils.BOARD_TILES_PER_ROW + 1);
            neighbours.add(position + BoardUtils.BOARD_TILES_PER_ROW - 1);
            return neighbours;
        }
    }

    private static class Builder {
        @SuppressLint("UseSparseArrays")
        Map<Integer, Tile> boardConfig = new HashMap<>();

        Map<Integer, Tile> build() {
            return boardConfig;
        }

        void setPiece(final Tile tile, int position) {
            this.boardConfig.put(position, tile);
        }
    }
}
