package com.example.robmillaci.minesweeper.UI;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.robmillaci.minesweeper.Adapters.RecyclerViewAdapter;
import com.example.robmillaci.minesweeper.Models.Statistics;
import com.example.robmillaci.minesweeper.R;

import java.util.ArrayList;

public class StatisticsActivity extends AppCompatActivity {

    ArrayList<Statistics> mStatisticsArrayList;
    SharedPreferences sp;

    ImageView statsDeletebtn;
    ImageView statsBackbtn;

    RecyclerViewAdapter mRecyclerViewAdapter;

    Statistics gamesPlayed;
    Statistics gamesWon;
    Statistics timesplayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_statistics);

        statsBackbtn = findViewById(R.id.settingsbackbtn);
        statsDeletebtn = findViewById(R.id.statsDeleteBtn);

        statsBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        statsDeletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder deleteDiag = new AlertDialog.Builder(StatisticsActivity.this);
                deleteDiag.setTitle("Delete statistics")
                        .setMessage("Are you sure you want to delete all statistics? This is permanent")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteStats();
                                createRecyclerViewAdaptor();
                            }
                        });
                deleteDiag.show();
            }
        });


        sp = PreferenceManager.getDefaultSharedPreferences(this);

        mStatisticsArrayList = new ArrayList<>();

        gamesPlayed = new Statistics("Games Played");
        gamesWon = new Statistics("Games Won");
        timesplayed = new Statistics("Total time played");

        gamesPlayed.setTotal(sp.getInt("gamesPlayed", 0));
        gamesPlayed.setVeryEasyTotal(sp.getInt("games_played_very_easy", 0));
        gamesPlayed.setEasyTotal(sp.getInt("games_played_easy", 0));
        gamesPlayed.setNormalTotal(sp.getInt("games_played_normal", 0));
        gamesPlayed.setHardTotal(sp.getInt("games_played_hard", 0));
        gamesPlayed.setVeryHardTotal(sp.getInt("games_played_very_hard", 0));

        gamesWon.setTotal(sp.getInt("gameswon", 0));
        gamesWon.setVeryEasyTotal(sp.getInt("gameswon_very_easy", 0));
        gamesWon.setEasyTotal(sp.getInt("gameswon_easy", 0));
        gamesWon.setNormalTotal(sp.getInt("gameswon_normal", 0));
        gamesWon.setHardTotal(sp.getInt("gameswon_hard", 0));
        gamesWon.setVeryHardTotal(sp.getInt("gameswon_very_hard", 0));

        timesplayed.setTimeveryEasyTotal_seconds(sp.getInt("timeplayedtotal_very_easy_seconds", 0));
        timesplayed.setTimeveryEasyTotal_minutes(sp.getInt("timeplayedtotal_very_easy_minutes", 0));
        timesplayed.setTimeEasyTotal_seconds(sp.getInt("timeplayedtotal_easy_seconds", 0));
        timesplayed.setTimeEasyTotal_minutes(sp.getInt("timeplayedtotal_easy_minutes", 0));
        timesplayed.setTimenormalTotal_seconds(sp.getInt("timeplayedtotal_normal_seconds", 0));
        timesplayed.setTimenormalTotal_minutes(sp.getInt("timeplayedtotal_normal_minutes", 0));
        timesplayed.setTimehardTotal_seconds(sp.getInt("timeplayedtotal_hard_seconds", 0));
        timesplayed.setTimehardTotal_minutes(sp.getInt("timeplayedtotal_hard_minutes", 0));
        timesplayed.setTimeveryHardTotal_seconds(sp.getInt("timeplayedtotal_very_hard_seconds", 0));
        timesplayed.setTimeveryHardTotal_minutes(sp.getInt("timeplayedtotal_very_hard_minutes", 0));

        mStatisticsArrayList.add(gamesPlayed);
        mStatisticsArrayList.add(gamesWon);
        mStatisticsArrayList.add(timesplayed);

        createRecyclerViewAdaptor();
    }

    private void createRecyclerViewAdaptor() {
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        //noinspection ConstantConditions
        itemDecorator.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.divider));

        RecyclerView recyclerView = findViewById(R.id.statsRecyclerView);
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerViewAdapter = new RecyclerViewAdapter(mStatisticsArrayList, this);

        recyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private void deleteStats() {
        SharedPreferences.Editor sharedPreferenceEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        sharedPreferenceEditor.putInt("gamesPlayed", 0);
        sharedPreferenceEditor.putInt("games_played_very_easy", 0);
        sharedPreferenceEditor.putInt("games_played_easy", 0);
        sharedPreferenceEditor.putInt("games_played_normal", 0);
        sharedPreferenceEditor.putInt("games_played_hard", 0);
        sharedPreferenceEditor.putInt("games_played_very_hard", 0);

        sharedPreferenceEditor.putInt("gameswon", 0);
        sharedPreferenceEditor.putInt("gameswon_very_easy", 0);
        sharedPreferenceEditor.putInt("gameswon_easy", 0);
        sharedPreferenceEditor.putInt("gameswon_normal", 0);
        sharedPreferenceEditor.putInt("gameswon_hard", 0);
        sharedPreferenceEditor.putInt("gameswon_very_hard", 0);

        sharedPreferenceEditor.putInt("timeplayedtotal_very_easy_seconds", 0);
        sharedPreferenceEditor.putInt("timeplayedtotal_very_easy_minutes", 0);
        sharedPreferenceEditor.putInt("timeplayedtotal_easy_seconds", 0);
        sharedPreferenceEditor.putInt("timeplayedtotal_easy_minutes", 0);
        sharedPreferenceEditor.putInt("timeplayedtotal_normal_seconds", 0);
        sharedPreferenceEditor.putInt("timeplayedtotal_normal_seconds", 0);
        sharedPreferenceEditor.putInt("timeplayedtotal_hard_seconds", 0);
        sharedPreferenceEditor.putInt("timeplayedtotal_hard_minutes", 0);
        sharedPreferenceEditor.putInt("timeplayedtotal_very_hard_seconds", 0);
        sharedPreferenceEditor.putInt("timeplayedtotal_very_hard_minutes", 0);

        sharedPreferenceEditor.apply();

        gamesPlayed = new Statistics("Games played");
        gamesWon = new Statistics("Games won");
        timesplayed = new Statistics("Times Played");

        mStatisticsArrayList.clear();
        mStatisticsArrayList.add(gamesPlayed);
        mStatisticsArrayList.add(gamesWon);
        mStatisticsArrayList.add(timesplayed);
    }
}
