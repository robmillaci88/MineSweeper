package com.example.robmillaci.minesweeper.UI;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robmillaci.minesweeper.Board.BoardUtils;
import com.example.robmillaci.minesweeper.R;

import java.util.ArrayList;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class homeActivity extends AppCompatActivity {
    private int numrows = 10;
    private int numcols = 10;
    private int numbombs = 20;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);

        Button playbtn = findViewById(R.id.playbtn);
        Button statsButton = findViewById(R.id.statsbtn);
        Button settingsbtn = findViewById(R.id.settingsbtn);
        Button aboutbtn = findViewById(R.id.aboutbtn);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        aboutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAboutDialog();
            }
        });



        statsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StatisticsActivity.class));
            }
        });

        settingsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SettingsActivity.class));
            }
        });


        playbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<RadioButton> radiobtns = new ArrayList<>();

                AlertDialog.Builder newGameDiag = new AlertDialog.Builder(homeActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                @SuppressLint("InflateParams") View chooserview = inflater.inflate(R.layout.difficulty_chooser, null);

                newGameDiag.setView(chooserview);

                RadioButton veryEasy = chooserview.findViewById(R.id.veryeasyradio);
                RadioButton easy = chooserview.findViewById(R.id.easyradio);
                RadioButton medium = chooserview.findViewById(R.id.normalradio);
                RadioButton hard = chooserview.findViewById(R.id.hardradio);
                RadioButton veryhard = chooserview.findViewById(R.id.veryhard);

                radiobtns.add(veryEasy);
                radiobtns.add(easy);
                radiobtns.add(medium);
                radiobtns.add(hard);
                radiobtns.add(veryhard);


                for (RadioButton r : radiobtns) {
                    r.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            RadioButton thisbutton = (RadioButton) v;

                            if (thisbutton.isChecked()) {
                                for (RadioButton r : radiobtns) {
                                    if (r != thisbutton) {
                                        r.setChecked(false);
                                    } else {
                                        switch (r.getTag().toString()) {
                                            case "1":
                                                BoardUtils.BOARD_TILES_PER_ROW = 10;
                                                BoardUtils.NUM_BOARD_TILES = 100;
                                                BoardUtils.NUM_BOMBS = 10;
                                                BoardUtils.GAME_MODE = 1;
                                                BoardUtils.DIFFICULTY = 1;
                                                break;
                                            case "2":
                                                BoardUtils.BOARD_TILES_PER_ROW = 12;
                                                BoardUtils.NUM_BOARD_TILES = 240;
                                                BoardUtils.NUM_BOMBS = 20;
                                                BoardUtils.GAME_MODE = 2;
                                                BoardUtils.DIFFICULTY = 2;
                                                break;
                                            case "3":
                                                BoardUtils.BOARD_TILES_PER_ROW = 15;
                                                BoardUtils.NUM_BOARD_TILES = 375;
                                                BoardUtils.NUM_BOMBS = 30;
                                                BoardUtils.GAME_MODE = 3;
                                                BoardUtils.DIFFICULTY = 3;
                                                break;
                                            case "4":
                                                BoardUtils.BOARD_TILES_PER_ROW = 15;
                                                BoardUtils.NUM_BOARD_TILES = 450;
                                                BoardUtils.NUM_BOMBS = 50;
                                                BoardUtils.GAME_MODE = 4;
                                                BoardUtils.DIFFICULTY = 4;
                                                break;
                                            case "5":
                                                BoardUtils.BOARD_TILES_PER_ROW = 15;
                                                BoardUtils.NUM_BOARD_TILES = 450;
                                                BoardUtils.NUM_BOMBS = 80;
                                                BoardUtils.GAME_MODE = 5;
                                                BoardUtils.DIFFICULTY = 5;
                                                break;
                                        }
                                    }
                                }
                            }
                        }

                    });
                }

                newGameDiag.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long startTime = System.currentTimeMillis();
                        progressBar.setVisibility(View.VISIBLE);
                        progressBar.setProgress(1);

                        int count = 0;
                        for (RadioButton r : radiobtns) {
                            if (r.isChecked()) {
                                count++;
                            }
                        }
                        if (count != 0) {
                            try {
                                final Intent newGameIntent = new Intent(getApplicationContext(), MainActivity.class);
                                final PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, newGameIntent, 0);
                                pendingIntent.send();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Please select a difficulty", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                newGameDiag.setNegativeButton("Cancel", null);
                newGameDiag.show();
            }
        });


    }

    private void createAboutDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(homeActivity.this);
        final View v = LayoutInflater.from(this).inflate(R.layout.aboutdialog,null);
        alertDialogBuilder.setView(v);
        alertDialogBuilder.setPositiveButton("OK",null);

        AlertDialog alertDialog = alertDialogBuilder.create();

        StringBuilder sb = new StringBuilder();
        sb.append("Minesweeper is a single-player puzzle video game. The objective of the game is to clear " +
                "a rectangular board containing hidden \"mines\" or bombs without detonating any of them, " +
                "with help from clues about the number of neighboring mines in each field.");
        sb.append("\n");
        sb.append("\n");
        sb.append("The player is initially presented with a grid of undifferentiated squares. Some randomly selected squares, " +
                "unknown to the player, are designated to contain mines. " +
                "Typically, the size of the grid and the number of mines are set in advance by the user, " +
                "either by entering the numbers or selecting from defined skill levels, depending on the implementations.");
        sb.append("\n");
        sb.append("\n");
        sb.append("The game is played by revealing squares of the grid by clicking or otherwise indicating each square. " +
                "If a square containing a mine is revealed, the player loses the game. If no mine is revealed, a digit is instead displayed in the square, " +
                "indicating how many adjacent squares contain mines; if no mines are adjacent, the square becomes blank, and all adjacent squares will be " +
                "recursively revealed. " +
                "The player uses this information to deduce the contents of other squares, and may either safely reveal each" +
                " square or mark the square as containing a mine.");
        sb.append("\n");
        sb.append("\n");
        sb.append("SINGLE CLICK a cell to reveal the cell.");
        sb.append("\n");
        sb.append("\n");
        sb.append("LONG CLICK a cell to mark the cell as a potential mine.");
        sb.append("\n");
        sb.append("\n");
        sb.append("LONG CLICK a marked cell to clear the flag.");
        sb.append("\n");
        sb.append("\n");
        sb.append("Once all non mine cells are revealed, the player wins and the game is over.");
        sb.append("\n");
        sb.append("\n");
        sb.append("Game behaviour in relation to vibration and sound feedback can be changed in settings.");
        sb.append("\n");
        sb.append("\n");
        sb.append("If you enjoyed the game please leave a rating :)");
        sb.append("\n");
        sb.append("\n");
        sb.append("Any feedback would be appreciated, please send a message to the email address provided above.");
        sb.append("\n");
        sb.append("\n");

        alertDialog.show();
        TextView aboutText = v.findViewById(R.id.aboutTextView);

        aboutText.setText(sb.toString());


    }
    @Override
    protected void onStop() {
        super.onStop();
        progressBar.setVisibility(View.GONE);
        progressBar.setProgress(0);

    }


    public void restoreSettings(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        SettingsActivity.flagSoundOn = sharedPreferences.getBoolean("flagSoundOn",true);
        SettingsActivity.flagVibrationOn = sharedPreferences.getBoolean("flagVibrationOn",true);

        SettingsActivity.gameOverSoundOn = sharedPreferences.getBoolean("gameOverSoundOn",true);
        SettingsActivity.gameOverVibrationOn = sharedPreferences.getBoolean("gameOverVibrationOn",true);

        SettingsActivity.winGameSoundOn = sharedPreferences.getBoolean("winGameSoundOn",true);
        SettingsActivity.winGameVibrationOn = sharedPreferences.getBoolean("winGameVibrationOn",true);

        SettingsActivity.clickvibration = sharedPreferences.getBoolean("clickSetting",true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        restoreSettings();
    }
}




