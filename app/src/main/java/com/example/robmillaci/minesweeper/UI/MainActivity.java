package com.example.robmillaci.minesweeper.UI;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.robmillaci.minesweeper.Board.Board;
import com.example.robmillaci.minesweeper.Board.BoardUtils;
import com.example.robmillaci.minesweeper.R;
import com.example.robmillaci.minesweeper.Tiles.BombTile;
import com.example.robmillaci.minesweeper.Tiles.Tile;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements
        View.OnClickListener, View.OnLongClickListener, MediaPlayer.OnCompletionListener {

    Map<Integer, Tile> boardSetup;
    Map<Integer, ImageView> tiles;
    Board board;

    TextView bombcounter;
    private TextView timerView;
    private TextView scoreBoard;
    private TextView flagcounter;
    private ImageView gameface;

    private boolean gameover = false;
    private boolean timerStarted = false;

    private int seconds = 0;
    private int minutes = 0;
    private int score = 0;
    private int flagcount = 0;
    private int gamesPlayed = 0;
    private int gamesPlayedVeryEasy = 0;
    private int gamesPlayedEasy = 0;
    private int gamesPlayedNormal = 0;
    private int gamesPlayedHard = 0;
    private int gamesPlayedVeryHard = 0;
    private int gameswon = 0;
    private int gameswonVeryEasy = 0;
    private int gameswonEasy = 0;
    private int gameswonNormal = 0;
    private int gameswonHard = 0;
    private int gameswonVeryHard = 0;
    private int timeplayedtotal_very_easy_seconds = 0;
    private int timeplayedtotal_very_easy_minutes = 0;
    private int timeplayedtotal_easy_seconds = 0;
    private int timeplayedtotal_easy_minutes = 0;
    private int timeplayedtotal_normal_seconds = 0;
    private int timeplayedtotal_normal_minutes = 0;
    private int timeplayedtotal_hard_seconds = 0;
    private int timeplayedtotal_hard_minutes = 0;
    private int timeplayedtotal_very_hard_seconds = 0;
    private int timeplayedtotal_very_hard_minutes = 0;

    private MediaPlayer mMediaPlayer;

    private boolean firstClick = true;

    LinearLayout tileLayout;
    ConstraintLayout gameLayout;
    ConstraintLayout bannerLayout;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPrefeEditor;
    Timer timer;

    @SuppressLint({"CommitPrefEdits", "UseSparseArrays"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPrefeEditor = sharedPreferences.edit();

        tileLayout = findViewById(R.id.tileLayout);
        gameLayout = findViewById(R.id.gamelayout);
        bannerLayout = findViewById(R.id.bannerLayout);
        bombcounter = findViewById(R.id.bombtextview);

        tiles = new HashMap<>();

        createGame();
        setContentView(createGameGrid(BoardUtils.NUM_BOARD_TILES, BoardUtils.BOARD_TILES_PER_ROW));

        flagcounter = findViewById(R.id.flagcounter);
        timerView = findViewById(R.id.timer);
        scoreBoard = findViewById(R.id.score);
        gameface = findViewById(R.id.gameface);
        bombcounter.setText(String.valueOf(BoardUtils.NUM_BOMBS));

        createHashMaptiles();
    }
    private View createGameGrid(int numberofTiles, int tilesPerRow) {
        gameLayout.removeAllViews();
        tileLayout.removeAllViews();

        int rows = numberofTiles / tilesPerRow;

        final LinearLayout MainLinearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParamsMain = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsMain.setMargins(0, 0, 0, 32);
        MainLinearLayout.setLayoutParams(layoutParamsMain);
        MainLinearLayout.setOrientation(LinearLayout.VERTICAL);
        MainLinearLayout.setId(R.id.tileLayout);
        MainLinearLayout.setWeightSum(rows);

        tileLayout = MainLinearLayout;

        ArrayList<LinearLayout> linearLayouts = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            LinearLayout linearLayout = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
            layoutParams.setMargins(0, 0, 0, 0);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setWeightSum(tilesPerRow);

            linearLayouts.add(linearLayout);
            MainLinearLayout.addView(linearLayout);
        }

        for (final LinearLayout l : linearLayouts) {
            for (int i = 0; i < tilesPerRow; i++) {
                final ImageView tile = new ImageView(this);

                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);

                int height = size.y;

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((height / 100) * 3, (height / 100) * 3, 1);

                tile.setLayoutParams(layoutParams);
                tile.setImageDrawable(getResources().getDrawable(R.drawable.silvertile));
                l.addView(tile);
            }
        }

        gameLayout.addView(MainLinearLayout);
        gameLayout.addView(bannerLayout);

        ConstraintSet set = new ConstraintSet();
        set.clone(gameLayout);
        set.connect(bannerLayout.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        set.connect(MainLinearLayout.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 90);
        set.connect(MainLinearLayout.getId(), ConstraintSet.TOP, bannerLayout.getId(), ConstraintSet.BOTTOM, 50);
        set.connect(MainLinearLayout.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, 25);
        set.connect(MainLinearLayout.getId(), ConstraintSet.RIGHT, ConstraintSet.PARENT_ID, ConstraintSet.RIGHT, 25);
        set.applyTo(gameLayout);

        return gameLayout;
    }

    private void createHashMaptiles() {
        int count = 0;
        for (int i = 0; i < tileLayout.getChildCount(); i++) {
            LinearLayout childLayout = (LinearLayout) tileLayout.getChildAt(i);
            for (int j = 0; j < childLayout.getChildCount(); j++) {
                ImageView im = (ImageView) childLayout.getChildAt(j);
                im.setCropToPadding(false);
                im.setScaleType(ImageView.ScaleType.FIT_XY);
                im.setTag(count);
                im.getLayoutParams().width = 0;
                im.setOnClickListener(this);
                im.setOnLongClickListener(this);
                tiles.put(count, im);
                count++;
            }
        }
    }

    private void createGame() {
        restoreSharedPreferences();
        gamesPlayed++;
        sharedPrefeEditor.putInt("gamesPlayed", gamesPlayed);

        switch (BoardUtils.GAME_MODE) {
            case 1:
                gamesPlayedVeryEasy++;
                sharedPrefeEditor.putInt("games_played_very_easy", gamesPlayedVeryEasy);
                break;
            case 2:
                gamesPlayedEasy++;
                sharedPrefeEditor.putInt("games_played_easy", gamesPlayedEasy);
                break;
            case 3:
                gamesPlayedNormal++;
                sharedPrefeEditor.putInt("games_played_normal", gamesPlayedNormal);
                break;
            case 4:
                gamesPlayedHard++;
                sharedPrefeEditor.putInt("games_played_hard", gamesPlayedHard);
                break;
            case 5:
                gamesPlayedVeryHard++;
                sharedPrefeEditor.putInt("games_played_very_hard", gamesPlayedVeryHard);
                break;
        }

        sharedPrefeEditor.commit();

        board = new Board();
        boardSetup = board.getGameBoard();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    public void startTimer(boolean timerOn) {
        if (!timerOn) {
            if (timer != null) {
                timer.cancel();
                timer = null;
                seconds = 0;
                minutes = 0;
                timerView.setText(R.string.default_timer_text);
            }
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    timer = new Timer();
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            String stringSeconds = (seconds < 10) ? "0" + String.valueOf(seconds) : String.valueOf(seconds);
                            String stringMinutes = (minutes < 10) ? "0" + String.valueOf(minutes) : String.valueOf(minutes);
                            updateTimerUI(stringMinutes + ":" + stringSeconds);
                            seconds++;
                            if (seconds == 60) {
                                seconds = 0;
                                minutes = minutes + 1;
                            }
                        }
                    }, 0, 1000);
                }
            });
            thread.start();
            timerStarted = true;
        }
    }


    private void updateScoreBoard() {
        String scoreString = String.valueOf(score);
        scoreBoard.setText(scoreString);
    }


    private void updateTimerUI(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timerView.setText(s);
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        restoreState();
    }

    private void restoreState() {
        gameswon = sharedPreferences.getInt("gameswon", 0);
        startTimer(timerStarted);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void onClick(View v) {
        //noinspection SuspiciousMethodCalls
        Tile t = boardSetup.get(v.getTag());

        if (SettingsActivity.clickvibration) {
            Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            assert vib != null;
            vib.vibrate(100);
        }
        if (firstClick && t instanceof BombTile || (firstClick && t.getNeighbourbombs() != 0)) {
            board = new Board();
            boardSetup = board.getGameBoard();
            createHashMaptiles();
            v.callOnClick();

        } else {
            if (!t.isRevealed()) {
                revealTile((ImageView) v, t);
                t.setRevealed(true);
                startTimer(timerStarted);
                score++;
                v.setOnClickListener(null);

                checkGameState();
                firstClick = false;
            }
        }
    }


    private void revealTile(final ImageView v, final Tile t) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(v, "rotationY", 0.0f, 180);
        final ObjectAnimator translateBack = ObjectAnimator.ofFloat(v, "rotationY", 0);
        animation.setDuration(250);
        animation.setRepeatCount(0);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        translateBack.setDuration(0);
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                translateBack.start();
                startReveal(v,t);
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        if (!t.isRevealed() && !(t instanceof BombTile)) {
            t.setRevealed(true);
            //todo if animations are on animate else startReveal();
            animation.start();
        } else if (t instanceof BombTile) {
            animation.end();
        }
    }

    private void startReveal(ImageView v, Tile t) {
        switch (t.getTileImageint()) {
            case 0:
                v.setImageDrawable(getResources().getDrawable(R.drawable.emptytile));
                for (int i : t.getNeighbours()) {
                    if (!(boardSetup.get(i) instanceof BombTile) && (i != -1)) {
                        tiles.get(i).callOnClick();
                    }
                }
                break;
            case 1:
                v.setImageDrawable(getResources().getDrawable(R.drawable.one));
                break;
            case 2:
                v.setImageDrawable(getResources().getDrawable(R.drawable.two));
                break;
            case 3:
                v.setImageDrawable(getResources().getDrawable(R.drawable.three));
                break;
            case 4:
                v.setImageDrawable(getResources().getDrawable(R.drawable.four));
                break;
            case 5:
                v.setImageDrawable(getResources().getDrawable(R.drawable.five));
                break;
            case 6:
                v.setImageDrawable(getResources().getDrawable(R.drawable.six));
                break;
            case 7:
                v.setImageDrawable(getResources().getDrawable(R.drawable.seven));
                break;
            case 8:
                v.setImageDrawable(getResources().getDrawable(R.drawable.eight));
                break;
            case -1:
                v.setImageDrawable(getResources().getDrawable(R.drawable.exploded_mine));
                t.setTileImageint(-2);
                timer.cancel();
                gameover();
                break;
        }
        scoreBoard.setText(String.valueOf(score));
    }

    private void gameover() {
        gameover = true;

        score = (int) (score * 0.5);

        AlertDialog.Builder gameoverDiag = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.gameover_dialog, null);
        gameoverDiag.setView(dialogView);

        calculatescore(dialogView);

        revealAllTiles();

        gameface.setImageDrawable(getResources().getDrawable(R.drawable.deadface));


        gameoverDiag.setPositiveButton("Play again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                timerStarted = false;
                playAgain();
            }
        });

        gameoverDiag.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), homeActivity.class));
            }
        });

        if (SettingsActivity.gameOverSoundOn) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.gameoversound);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.start();
        }

        if (SettingsActivity.gameOverVibrationOn) {
            Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            assert vib != null;
            vib.vibrate(300);
        }

        gameoverDiag.show();
    }

    private void revealAllTiles() {
        for (int i = 0; i < boardSetup.size(); i++) {
            Tile t = boardSetup.get(i);
            if (!t.isRevealed()) {
                t.setRevealed(true);
                ImageView v = tiles.get(i);
                switch (t.getTileImageint()) {
                    case 0:
                        v.setImageDrawable(getResources().getDrawable(R.drawable.emptytile));
                        break;
                    case 1:
                        v.setImageDrawable(getResources().getDrawable(R.drawable.one));
                        break;
                    case 2:
                        v.setImageDrawable(getResources().getDrawable(R.drawable.two));
                        break;
                    case 3:
                        v.setImageDrawable(getResources().getDrawable(R.drawable.three));
                        break;
                    case 4:
                        v.setImageDrawable(getResources().getDrawable(R.drawable.four));
                        break;
                    case 5:
                        v.setImageDrawable(getResources().getDrawable(R.drawable.five));
                        break;
                    case 6:
                        v.setImageDrawable(getResources().getDrawable(R.drawable.six));
                        break;
                    case 7:
                        v.setImageDrawable(getResources().getDrawable(R.drawable.seven));
                        break;
                    case 8:
                        v.setImageDrawable(getResources().getDrawable(R.drawable.eight));
                        break;
                    case -1:
                        v.setImageDrawable(getResources().getDrawable(R.drawable.mine));
                        break;
                }
            }
        }
    }

    @Override
    public boolean onLongClick(View v) {
        int tag = (int) v.getTag();
        Tile t = boardSetup.get(tag);
        ImageView i = tiles.get(tag);


        mMediaPlayer = MediaPlayer.create(this, R.raw.flagselector);
        mMediaPlayer.setOnCompletionListener(this);

        if (!t.isFlagged() && !t.isRevealed()) {
            score = score - 1;
            updateScoreBoard();
            i.setImageDrawable(getResources().getDrawable(R.drawable.flag));
            t.setFlagged(true);
            updateFlag(true);

            if (SettingsActivity.flagVibrationOn) {
                Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                assert vib != null;
                vib.vibrate(100);
            }

            if (SettingsActivity.flagSoundOn) {
                mMediaPlayer.start();
            }


        } else if (t.isFlagged() && !t.isRevealed()) {
            score = score + 1;
            updateScoreBoard();
            i.setImageDrawable(getResources().getDrawable(R.drawable.silvertile));
            mMediaPlayer.start();
            t.setFlagged(false);
            updateFlag(false);

            if (SettingsActivity.flagVibrationOn) {
                Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                assert vib != null;
                vib.vibrate(100);
            }
        }

        return true;
    }

    private void updateFlag(boolean increase) {
        if (increase) {
            flagcount++;
            flagcounter.setText(String.valueOf(flagcount));
        } else {
            flagcount--;
            flagcounter.setText(String.valueOf(flagcount));
        }
    }

    private void checkGameState() {
        if (!gameover) {
            int count = 0;
            for (int i = 0; i < BoardUtils.NUM_BOARD_TILES; i++) {
                if (boardSetup.get(i).isRevealed() && !(boardSetup.get(i) instanceof BombTile)) {
                    count++;
                }

                if (count == BoardUtils.NUM_BOARD_TILES - BoardUtils.NUM_BOMBS) {
                    gameover = true;

                    AlertDialog.Builder winDiag = new AlertDialog.Builder(this);
                    LayoutInflater inflater = this.getLayoutInflater();
                    @SuppressLint("InflateParams") View dialogView = inflater.inflate(R.layout.win_dialog, null);
                    winDiag.setView(dialogView);

                    gameswon++;
                    sharedPrefeEditor.putInt("gameswon", gameswon);

                    gameswonVeryEasy = sharedPreferences.getInt("gameswon_very_easy", 0);
                    gameswonEasy = sharedPreferences.getInt("gameswon_easy", 0);
                    gameswonNormal = sharedPreferences.getInt("gameswon_normal", 0);
                    gameswonHard = sharedPreferences.getInt("gameswon_hard", 0);
                    gameswonVeryHard = sharedPreferences.getInt("gameswon_very_hard", 0);


                    switch (BoardUtils.DIFFICULTY) {
                        case 1:
                            gameswonVeryEasy++;
                            sharedPrefeEditor.putInt("gameswon_very_easy", gameswonVeryEasy);
                            break;
                        case 2:
                            gameswonEasy++;
                            sharedPrefeEditor.putInt("gameswon_easy", gameswonEasy);
                            break;
                        case 3:
                            gameswonNormal++;
                            sharedPrefeEditor.putInt("gameswon_normal", gameswonNormal);
                            break;
                        case 4:
                            gameswonHard++;
                            sharedPrefeEditor.putInt("gameswon_hard", gameswonHard);
                            break;
                        case 5:
                            gameswonVeryHard++;
                            sharedPrefeEditor.putInt("gameswon_very_hard", gameswonVeryHard);
                            break;
                    }

                    sharedPrefeEditor.commit();

                    revealAllTiles();
                    timer.cancel();
                    calculatescore(dialogView);

                    winDiag.setPositiveButton(R.string.play_again, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            playAgain();
                        }
                    });

                    winDiag.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(getApplicationContext(), homeActivity.class));
                        }
                    });

                    if (SettingsActivity.winGameSoundOn) {
                        mMediaPlayer = MediaPlayer.create(this, R.raw.winsound);
                        mMediaPlayer.setOnCompletionListener(this);
                        mMediaPlayer.start();
                    }

                    if (SettingsActivity.winGameVibrationOn) {
                        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        assert vib != null;
                        vib.vibrate(300);
                    }
                    winDiag.show();
                }
            }
        }
    }

    private void playAgain() {
        for (int i = 0; i < tiles.size(); i++) {
            tiles.get(i).setImageDrawable(getResources().getDrawable(R.drawable.silvertile));
        }
        gameface.setImageDrawable(getResources().getDrawable(R.drawable.happyface));
        flagcount = 0;
        flagcounter.setText(String.valueOf(flagcount));
        timerStarted = false;
        gameover = false;
        firstClick = true;


        createGame();
        createHashMaptiles();
    }

    private void calculatescore(View v) {
        TextView winTime = v.findViewById(R.id.totalgametime);
        TextView gamesPlayedtext = v.findViewById(R.id.gamesplayed);
        TextView gamesWonText = v.findViewById(R.id.gameswon);
        TextView gamesWonPercentText = v.findViewById(R.id.gameswonpercent);
        TextView scoreText = v.findViewById(R.id.scoretext);
        TextView difficultybonus = v.findViewById(R.id.difficultybonustext);
        TextView timededuction = v.findViewById(R.id.timedeductiontext);
        TextView totalScore = v.findViewById(R.id.totalscoretext);

        gamesWonText.setText(String.valueOf(gameswon));

        double fractionOfGamesWon = ((double) gameswon / (double) gamesPlayed) * 100;
        DecimalFormat df = new DecimalFormat("#.00");
        String sb = "(" +
                df.format(fractionOfGamesWon) +
                "%)";
        gamesWonPercentText.setText(sb);


        winTime.setText(timerView.getText());
        gamesPlayedtext.setText(String.valueOf(gamesPlayed));
        scoreText.setText(String.valueOf(score));
        difficultybonus.setText(String.format("x %s", String.valueOf(BoardUtils.DIFFICULTY)));

        saveTime();

        int calculatedTimededuction = 0;
        for (int k = 0; k < minutes; k++) {
            calculatedTimededuction = calculatedTimededuction + 2;
        }

        timededuction.setText(String.format("- %s", String.valueOf(calculatedTimededuction)));

        int totalScoreCalculated = (score * BoardUtils.DIFFICULTY) - calculatedTimededuction;

        totalScore.setText(String.valueOf(totalScoreCalculated));
    }

    private void saveTime() {
        switch (BoardUtils.DIFFICULTY) {
            case 1:
                timeplayedtotal_very_easy_seconds += seconds;
                timeplayedtotal_very_easy_minutes += minutes;
                sharedPrefeEditor.putInt("timeplayedtotal_very_easy_seconds", timeplayedtotal_very_easy_seconds);
                sharedPrefeEditor.putInt("timeplayedtotal_very_easy_minutes", timeplayedtotal_very_easy_minutes);
                break;
            case 2:
                timeplayedtotal_easy_seconds += seconds;
                timeplayedtotal_easy_minutes += minutes;
                sharedPrefeEditor.putInt("timeplayedtotal_easy_seconds", timeplayedtotal_easy_seconds);
                sharedPrefeEditor.putInt("timeplayedtotal_easy_minutes", timeplayedtotal_easy_minutes);
                break;
            case 3:
                timeplayedtotal_normal_seconds += seconds;
                timeplayedtotal_normal_minutes += minutes;
                sharedPrefeEditor.putInt("timeplayedtotal_normal_seconds", timeplayedtotal_normal_seconds);
                sharedPrefeEditor.putInt("timeplayedtotal_normal_minutes", timeplayedtotal_normal_minutes);
                break;
            case 4:
                timeplayedtotal_hard_seconds += seconds;
                timeplayedtotal_hard_minutes += minutes;
                sharedPrefeEditor.putInt("timeplayedtotal_hard_seconds", timeplayedtotal_hard_seconds);
                sharedPrefeEditor.putInt("timeplayedtotal_hard_minutes", timeplayedtotal_hard_minutes);
                break;
            case 5:
                timeplayedtotal_very_hard_seconds += seconds;
                timeplayedtotal_very_hard_minutes += minutes;
                sharedPrefeEditor.putInt("timeplayedtotal_very_hard_seconds", timeplayedtotal_very_hard_seconds);
                sharedPrefeEditor.putInt("timeplayedtotal_very_hard_minutes", timeplayedtotal_very_hard_minutes);
                break;
        }
        sharedPrefeEditor.commit();
    }

    private void restoreSharedPreferences() {
        gamesPlayed = sharedPreferences.getInt("gamesPlayed", 0);
        gamesPlayedVeryEasy = sharedPreferences.getInt("games_played_very_easy", 0);
        gamesPlayedEasy = sharedPreferences.getInt("games_played_easy", 0);
        gamesPlayedNormal = sharedPreferences.getInt("games_played_normal", 0);
        gamesPlayedHard = sharedPreferences.getInt("games_played_hard", 0);

        gameswonVeryEasy = sharedPreferences.getInt("gameswon_very_easy", 0);
        gameswonEasy = sharedPreferences.getInt("gameswon_easy", 0);
        gameswonNormal = sharedPreferences.getInt("gameswon_normal", 0);
        gameswonHard = sharedPreferences.getInt("gameswon_hard", 0);
        gameswonVeryHard = sharedPreferences.getInt("gameswon_very_hard", 0);

        timeplayedtotal_very_easy_seconds = sharedPreferences.getInt("timeplayedtotal_very_easy_seconds", 0);
        timeplayedtotal_very_easy_minutes = sharedPreferences.getInt("timeplayedtotal_very_easy_minutes", 0);
        timeplayedtotal_easy_seconds = sharedPreferences.getInt("timeplayedtotal_easy_seconds", 0);
        timeplayedtotal_easy_minutes = sharedPreferences.getInt("timeplayedtotal_easy_minutes", 0);
        timeplayedtotal_normal_seconds = sharedPreferences.getInt("timeplayedtotal_normal_seconds", 0);
        timeplayedtotal_normal_minutes = sharedPreferences.getInt("timeplayedtotal_normal_minutes", 0);
        timeplayedtotal_hard_seconds = sharedPreferences.getInt("timeplayedtotal_hard_seconds", 0);
        timeplayedtotal_hard_seconds = sharedPreferences.getInt("timeplayedtotal_hard_minutes", 0);
        timeplayedtotal_very_hard_seconds = sharedPreferences.getInt("timeplayedtotal_very_hard_seconds", 0);
        timeplayedtotal_very_hard_minutes = sharedPreferences.getInt("timeplayedtotal_very_hard_minutes", 0);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mp.release();
    }

}

