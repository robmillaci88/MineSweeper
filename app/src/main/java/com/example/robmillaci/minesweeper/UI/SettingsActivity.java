package com.example.robmillaci.minesweeper.UI;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.robmillaci.minesweeper.Adapters.SettingsRecyclerViewAdapter;
import com.example.robmillaci.minesweeper.Models.Settingsitem;
import com.example.robmillaci.minesweeper.R;

import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    ArrayList<Settingsitem> settingsObjects;
    RecyclerView settingsRecyclerView;

    public static boolean flagSoundOn = true;
    public static boolean flagVibrationOn = true;
    public static boolean gameOverSoundOn = true;
    public static boolean gameOverVibrationOn = true;
    public static boolean winGameSoundOn = true;
    public static boolean winGameVibrationOn = true;
    public static boolean clickvibration = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        ImageView backbtn = findViewById(R.id.settingsbackbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        settingsRecyclerView = findViewById(R.id.settingsRecycvlerView);

        settingsObjects = new ArrayList<>();

        Settingsitem flagsettings = new Settingsitem();
        Settingsitem gameover = new Settingsitem();
        Settingsitem win = new Settingsitem();
        Settingsitem clickitem = new Settingsitem();


        settingsObjects.add(flagsettings);
        settingsObjects.add(gameover);
        settingsObjects.add(win);
        settingsObjects.add(clickitem);

        SettingsRecyclerViewAdapter settingsRecyclerViewAdapter = new SettingsRecyclerViewAdapter(settingsObjects, this);
        settingsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        //noinspection ConstantConditions
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.settingsdivider));

        settingsRecyclerView.addItemDecoration(itemDecorator);

        settingsRecyclerView.setAdapter(settingsRecyclerViewAdapter);


    }


    public static void printthevalues() {
        Log.d("SETTINGS", "printthevalues: flag sound on " + flagSoundOn + " " + "flag vibration on " + flagVibrationOn);
        Log.d("SETTINGS", "printthevalues: gameover sound on " + gameOverSoundOn + " " + "game over vibration on " + gameOverVibrationOn);
        Log.d("SETTINGS", "printthevalues: win game sound on " + winGameSoundOn + " " + "win vibration on " + winGameVibrationOn);
    }
}
