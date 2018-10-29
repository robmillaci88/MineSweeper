package com.example.robmillaci.minesweeper.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.robmillaci.minesweeper.Models.Settingsitem;
import com.example.robmillaci.minesweeper.R;
import com.example.robmillaci.minesweeper.UI.SettingsActivity;

import java.util.ArrayList;

public class SettingsRecyclerViewAdapter extends RecyclerView.Adapter<SettingsRecyclerViewAdapter.MyViewHolder> {
    private ArrayList<Settingsitem> mSettingsitemArrayList;
    private Context mContext;
    private Settingsitem flagSettings;
    private Settingsitem gameoverSettings;
    private Settingsitem gamewinSettings;
    private SharedPreferences sp;


    public SettingsRecyclerViewAdapter(ArrayList<Settingsitem> settingsitemArrayList, Context context) {
        mSettingsitemArrayList = settingsitemArrayList;
        this.mContext = context;
        sp = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listviewitemlayout, parent, false);
        return new SettingsRecyclerViewAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        switch (position) {
            case 0: //flag
                holder.title.setText(R.string.reaction_placing_flag);

                if (SettingsActivity.flagVibrationOn && SettingsActivity.flagSoundOn) {
                    holder.description.setText(R.string.settings_vibration_and_sound);
                    holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundon));
                    holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.vibrationon));
                } else if (!SettingsActivity.flagVibrationOn && SettingsActivity.flagSoundOn) {
                    holder.description.setText(R.string.settings_sound_only);
                    holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundon));
                    holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.virbrationoff));
                } else if (SettingsActivity.flagVibrationOn && !SettingsActivity.flagSoundOn) {
                    holder.description.setText(R.string.setting_vibration_only);
                    holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundoff));
                    holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.vibrationon));
                } else {
                    holder.description.setText(R.string.settings_no_reaction);
                    holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundoff));
                    holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.virbrationoff));
                }

                flagSettings = mSettingsitemArrayList.get(position);
                flagSettings.setClickCount(sp.getInt("flagclickcount", 0));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (flagSettings.getClickCount()) {
                            case 0:
                                SettingsActivity.flagVibrationOn = false;
                                SettingsActivity.flagSoundOn = true;
                                holder.description.setText(R.string.settings_sound_only);
                                holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundon));
                                holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.virbrationoff));

                                flagSettings.setClickCount(flagSettings.getClickCount() + 1);

                                break;
                            case 1:
                                SettingsActivity.flagVibrationOn = true;
                                SettingsActivity.flagSoundOn = false;

                                holder.description.setText(R.string.setting_vibration_only);
                                holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundoff));
                                holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.vibrationon));

                                flagSettings.setClickCount(flagSettings.getClickCount() + 1);
                                break;
                            case 2:
                                SettingsActivity.flagVibrationOn = true;
                                SettingsActivity.flagSoundOn = true;
                                holder.description.setText(R.string.settings_vibration_and_sound);
                                holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundon));
                                holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.vibrationon));

                                flagSettings.setClickCount(flagSettings.getClickCount() + 1);
                                break;

                            case 3:
                                SettingsActivity.flagVibrationOn = false;
                                SettingsActivity.flagSoundOn = false;
                                holder.description.setText(R.string.settings_no_reaction);
                                holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundoff));
                                holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.virbrationoff));

                                flagSettings.setClickCount(flagSettings.getClickCount() + 1);
                        }
                        saveToSharedPreferences();
                    }
                });

                break;

            case 1: //gameover
                holder.title.setText(R.string.settings_reaction_game_over);

                if (SettingsActivity.gameOverSoundOn && SettingsActivity.gameOverVibrationOn) {
                    holder.description.setText(R.string.settings_vibration_and_sound);
                    holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundon));
                    holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.vibrationon));
                } else if (SettingsActivity.gameOverSoundOn && !SettingsActivity.flagSoundOn) {
                    holder.description.setText(R.string.settings_sound_only);
                    holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundon));
                    holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.virbrationoff));
                } else if (!SettingsActivity.gameOverSoundOn && SettingsActivity.gameOverVibrationOn) {
                    holder.description.setText(R.string.setting_vibration_only);
                    holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundoff));
                    holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.vibrationon));
                } else {
                    holder.description.setText(R.string.settings_no_reaction);
                    holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundoff));
                    holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.virbrationoff));
                }
                gameoverSettings = mSettingsitemArrayList.get(position);
                gameoverSettings.setClickCount(sp.getInt("gameoverClickcount", 0));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (gameoverSettings.getClickCount()) {
                            case 0:
                                SettingsActivity.gameOverVibrationOn = false;
                                SettingsActivity.gameOverSoundOn = true;
                                holder.description.setText(R.string.settings_sound_only);
                                holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundon));
                                holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.virbrationoff));

                                gameoverSettings.setClickCount(gameoverSettings.getClickCount() + 1);

                                break;
                            case 1:
                                SettingsActivity.gameOverVibrationOn = true;
                                SettingsActivity.gameOverSoundOn = false;

                                holder.description.setText(R.string.setting_vibration_only);
                                holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundoff));
                                holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.vibrationon));

                                gameoverSettings.setClickCount(gameoverSettings.getClickCount() + 1);
                                break;
                            case 2:
                                SettingsActivity.gameOverVibrationOn = true;
                                SettingsActivity.gameOverSoundOn = true;
                                holder.description.setText(R.string.settings_vibration_and_sound);
                                holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundon));
                                holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.vibrationon));

                                gameoverSettings.setClickCount(gameoverSettings.getClickCount() + 1);
                                break;

                            case 3:
                                SettingsActivity.gameOverVibrationOn = false;
                                SettingsActivity.gameOverSoundOn = false;
                                holder.description.setText(R.string.settings_no_reaction);
                                holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundoff));
                                holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.virbrationoff));

                                gameoverSettings.setClickCount(gameoverSettings.getClickCount() + 1);
                        }
                        saveToSharedPreferences();
                    }
                });

                break;
            case 2://win
                holder.title.setText(R.string.settings_reaction_on_win);

                if (SettingsActivity.winGameSoundOn && SettingsActivity.winGameVibrationOn) {
                    holder.description.setText(R.string.settings_vibration_and_sound);
                    holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundon));
                    holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.vibrationon));
                } else if (SettingsActivity.winGameSoundOn && !SettingsActivity.winGameVibrationOn) {
                    holder.description.setText(R.string.settings_sound_only);
                    holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundon));
                    holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.virbrationoff));
                } else if (!SettingsActivity.winGameSoundOn && SettingsActivity.winGameVibrationOn) {
                    holder.description.setText(R.string.setting_vibration_only);
                    holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundoff));
                    holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.vibrationon));
                } else {
                    holder.description.setText(R.string.settings_no_reaction);
                    holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundoff));
                    holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.virbrationoff));
                }

                gamewinSettings = mSettingsitemArrayList.get(position);
                gamewinSettings.setClickCount(sp.getInt("gamewinClickCount", 0));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (gamewinSettings.getClickCount()) {
                            case 0:
                                SettingsActivity.winGameVibrationOn = false;
                                SettingsActivity.winGameSoundOn = true;
                                holder.description.setText(R.string.settings_sound_only);
                                holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundon));
                                holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.virbrationoff));

                                gamewinSettings.setClickCount(gamewinSettings.getClickCount() + 1);

                                break;
                            case 1:
                                SettingsActivity.winGameVibrationOn = true;
                                SettingsActivity.winGameSoundOn = false;

                                holder.description.setText(R.string.setting_vibration_only);
                                holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundoff));
                                holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.vibrationon));

                                gamewinSettings.setClickCount(gamewinSettings.getClickCount() + 1);
                                break;
                            case 2:
                                SettingsActivity.winGameVibrationOn = true;
                                SettingsActivity.winGameSoundOn = true;
                                holder.description.setText(R.string.settings_vibration_and_sound);
                                holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundon));
                                holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.vibrationon));

                                gamewinSettings.setClickCount(gamewinSettings.getClickCount() + 1);
                                break;

                            case 3:
                                SettingsActivity.winGameVibrationOn = false;
                                SettingsActivity.winGameSoundOn = false;
                                holder.description.setText(R.string.settings_no_reaction);
                                holder.volicon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.soundoff));
                                holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.virbrationoff));

                                gamewinSettings.setClickCount(gamewinSettings.getClickCount() + 1);
                        }
                        saveToSharedPreferences();
                    }

                });
                break;

            case 3: //click settings
                holder.title.setText(R.string.settings_reaction_on_click);
                holder.volicon.setVisibility(View.INVISIBLE);

                if (SettingsActivity.clickvibration) {
                    holder.description.setText(R.string.setting_vibration_only);
                    holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.vibrationon));
                    holder.vibIcon.setTag(1);
                } else {
                    holder.description.setText(R.string.settings_no_reaction);
                    holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.virbrationoff));
                    holder.vibIcon.setTag(0);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int tag = (int) holder.vibIcon.getTag();
                        if (tag==1) {
                            SettingsActivity.clickvibration = false;
                            holder.description.setText(R.string.settings_no_reaction);
                            holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.virbrationoff));
                            holder.vibIcon.setTag(0);
                        } else {
                            SettingsActivity.clickvibration = true;
                            holder.description.setText(R.string.reaction_vibrate);
                            holder.vibIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.vibrationon));
                            holder.vibIcon.setTag(1);
                        }
                        saveToSharedPreferences();
                    }
                });
                break;
        }
    }


    @SuppressLint("ApplySharedPref")
    private void saveToSharedPreferences() {
        Log.d("SETTINGS", "saveToSharedPreferences: called");
        SharedPreferences.Editor sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext).edit();
        sharedPreferences.putBoolean("flagSoundOn", SettingsActivity.flagSoundOn);
        sharedPreferences.putBoolean("flagVibrationOn", SettingsActivity.flagVibrationOn);

        sharedPreferences.putBoolean("gameOverSoundOn", SettingsActivity.gameOverSoundOn);
        sharedPreferences.putBoolean("gameOverVibrationOn", SettingsActivity.gameOverVibrationOn);

        sharedPreferences.putBoolean("winGameSoundOn", SettingsActivity.winGameSoundOn);
        sharedPreferences.putBoolean("winGameVibrationOn", SettingsActivity.winGameVibrationOn);

        //save the click counts
        sharedPreferences.putInt("flagclickcount", flagSettings.getClickCount());
        sharedPreferences.putInt("gameoverClickcount", gameoverSettings.getClickCount());
        sharedPreferences.putInt("gamewinClickCount", gamewinSettings.getClickCount());

        sharedPreferences.putBoolean("clickSetting", SettingsActivity.clickvibration);

        sharedPreferences.commit();
    }

    @Override
    public int getItemCount() {
        return mSettingsitemArrayList.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageView volicon;
        ImageView vibIcon;

        private MyViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.settingstitle);
            this.description = itemView.findViewById(R.id.settingsdescription);
            this.vibIcon = itemView.findViewById(R.id.vibrationimage);
            this.volicon = itemView.findViewById(R.id.soundimage);
        }

    }


}
