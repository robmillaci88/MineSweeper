package com.example.robmillaci.minesweeper.Board;

import android.util.Log;

import java.util.ArrayList;

public class BoardUtils {
    public static int GAME_MODE;
    public static int NUM_BOARD_TILES;
    public static int BOARD_TILES_PER_ROW;
    public static int NUM_BOMBS;
    private static int[] FIRST_ROW;
    private static int[] LAST_ROW;
    private static int[] FIRST_COLUMN;
    private static int[] LAST_COLUMN;
    public static int DIFFICULTY;


    public BoardUtils() {
    }

    private static void initialize() {
        initialiseFirstRow();
        initializeLastRow();
        initializeFirstColumn();
        initializeLastColumn();
    }

    public static boolean isFirstRow(int number) {
        initialize();
        ArrayList<Integer> firstRowArrayList = new ArrayList<>();
        for (int i : FIRST_ROW) {
            firstRowArrayList.add(i);
        }
        return firstRowArrayList.contains(number);
    }

    public static boolean isLastRow(int number) {
        initialize();
        ArrayList<Integer> lastRowArrayList = new ArrayList<>();
        for (int i : LAST_ROW) {
            lastRowArrayList.add(i);
        }
        return lastRowArrayList.contains(number);
    }

    public static boolean isFirstColumn(int number) {
        initialize();
        ArrayList<Integer> firstColumnArrayList = new ArrayList<>();
        for (int i : FIRST_COLUMN) {
            firstColumnArrayList.add(i);
        }
        return firstColumnArrayList.contains(number);
    }

    public static boolean isLastColumn(int number) {
        initialize();
        ArrayList<Integer> lastColumnArrayList = new ArrayList<>();
        for (int i : LAST_COLUMN) {
            lastColumnArrayList.add(i);
        }
        return lastColumnArrayList.contains(number);
    }


    private static void initialiseFirstRow() {
        int[] firstRow = new int[BOARD_TILES_PER_ROW];
        for (int i = 0; i < BOARD_TILES_PER_ROW; i++) {
            firstRow[i] = i;
        }
        FIRST_ROW = firstRow;
    }

    private static void initializeLastRow() {
        int[] lastRowArray = new int[BOARD_TILES_PER_ROW];
        int substractor = 1;
        for (int i = 0; i < BOARD_TILES_PER_ROW; i++) {
            lastRowArray[i] = NUM_BOARD_TILES - substractor;
            substractor++;
        }

        LAST_ROW = lastRowArray;
    }


    private static void initializeFirstColumn() {
        int[] firstColumnArray = new int[NUM_BOARD_TILES / BOARD_TILES_PER_ROW];
        for (int i = 0; i < NUM_BOARD_TILES / BOARD_TILES_PER_ROW; i++) {
            firstColumnArray[i] = i * BOARD_TILES_PER_ROW;
        }
        FIRST_COLUMN = firstColumnArray;
    }

    private static void initializeLastColumn() {
        int[] lastcolumnArray = new int[NUM_BOARD_TILES / BOARD_TILES_PER_ROW];
        for (int i = 0; i < NUM_BOARD_TILES / BOARD_TILES_PER_ROW; i++) {
            lastcolumnArray[i] = (NUM_BOARD_TILES - 1) - (i * BOARD_TILES_PER_ROW);
        }
        LAST_COLUMN = lastcolumnArray;
    }

    public static void printtheshit() {
        Log.d("BOARDUTILSME", "printtheshit: per row " + BOARD_TILES_PER_ROW);
        Log.d("BOARDUTILSME", "printtheshit: number of tiles " + NUM_BOARD_TILES);
        Log.d("BOARDUTILSME", "printtheshit: number of bombs " + NUM_BOMBS);
    }
}
