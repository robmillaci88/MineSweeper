package com.example.robmillaci.minesweeper.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.robmillaci.minesweeper.Models.Statistics;
import com.example.robmillaci.minesweeper.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
   private ArrayList<Statistics> mStatisticsArrayList;
   private List<Integer> pieChartColors;
   private Context mContext;

    public RecyclerViewAdapter(ArrayList<Statistics> statisticsArrayList, Context context) {
        mStatisticsArrayList = statisticsArrayList;
        this.mContext = context;
    }

    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        pieChartColors = new ArrayList<>();
//
        int red = mContext.getResources().getColor(R.color.pastelRed);
        int orange = mContext.getResources().getColor(R.color.pastelOrange);
        int blue = mContext.getResources().getColor(R.color.pastelBlue);
        int yellow = mContext.getResources().getColor(R.color.pastelYellow);
        int green = mContext.getResources().getColor(R.color.pastelGreen);

        pieChartColors.add(green);
        pieChartColors.add(yellow);
        pieChartColors.add(blue);
        pieChartColors.add(orange);
        pieChartColors.add(red);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stats_recyvlerview_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, int position) {
        switch (position) {
            case 0:
                Statistics gamesPlayed = mStatisticsArrayList.get(0);
                holder.total.setText(mContext.getResources().getString(R.string.total_games_played_text, String.valueOf(gamesPlayed.getTotal())));
                holder.title.setText(gamesPlayed.getTitle());

                if (gamesPlayed.isTherePlayedData()) {
                    ArrayList<PieEntry> totalgamesvalues = new ArrayList<>();
                    totalgamesvalues.add(new PieEntry(gamesPlayed.getVeryEasyTotal(), "Very easy " + String.valueOf(gamesPlayed.getVeryEasyTotal())));
                    totalgamesvalues.add(new PieEntry(gamesPlayed.getEasyTotal(), "Easy " + String.valueOf(gamesPlayed.getEasyTotal())));
                    totalgamesvalues.add(new PieEntry(gamesPlayed.getNormalTotal(), "Normal " + String.valueOf(gamesPlayed.getNormalTotal())));
                    totalgamesvalues.add(new PieEntry(gamesPlayed.getHardTotal(), "Hard " + String.valueOf(gamesPlayed.getHardTotal())));
                    totalgamesvalues.add(new PieEntry(gamesPlayed.getVeryHardTotal(), "Very Hard " + String.valueOf(gamesPlayed.getVeryHardTotal())));
                    PieDataSet dataSet = new PieDataSet(totalgamesvalues, "");

                    dataSet.setColors(pieChartColors);
                    setPieChart(holder.mPieChart, dataSet);
                }

                break;
            case 1:
                Statistics gamesWon = mStatisticsArrayList.get(1);
                holder.total.setText(mContext.getResources().getString(R.string.total_games_won, String.valueOf(gamesWon.getTotal())));
                holder.title.setText(gamesWon.getTitle());

                if (gamesWon.isTherePlayedData()) {
                    if (gamesWon.isThereGamesWonData()) {
                        ArrayList<PieEntry> gamesWonValues = new ArrayList<>();
                        gamesWonValues.add(new PieEntry(gamesWon.getVeryEasyTotal(), "Very easy " + String.valueOf(gamesWon.getVeryEasyTotal())));
                        gamesWonValues.add(new PieEntry(gamesWon.getEasyTotal(), "Easy " + String.valueOf(gamesWon.getEasyTotal())));
                        gamesWonValues.add(new PieEntry(gamesWon.getNormalTotal(), "Normal " + String.valueOf(gamesWon.getNormalTotal())));
                        gamesWonValues.add(new PieEntry(gamesWon.getHardTotal(), "Hard " + String.valueOf(gamesWon.getHardTotal())));
                        gamesWonValues.add(new PieEntry(gamesWon.getVeryHardTotal(), "Very Hard " + String.valueOf(gamesWon.getVeryHardTotal())));
                        PieDataSet gamesWonDataset = new PieDataSet(gamesWonValues, "");

                        gamesWonDataset.setColors(pieChartColors);
                        setPieChart(holder.mPieChart, gamesWonDataset);
                        break;
                    }
                }
            case 2:
                Statistics totalTime = mStatisticsArrayList.get(position);
                holder.total.setText(mContext.getString(R.string.total_time_played,totalTime.getTimetotal()));
                holder.title.setText(totalTime.getTitle());

                if (totalTime.isThereTimeData()) {
                    ArrayList<PieEntry> totaltime = new ArrayList<>();
                    totaltime.add(new PieEntry(totalTime.getVeryEasyTotalTime(),
                            "Very easy " + totalTime.getVeryEasyTime()));

                    totaltime.add(new PieEntry(totalTime.getEasyTotalTime(),
                            "Easy " + totalTime.getEasyTime()));

                    totaltime.add(new PieEntry(totalTime.getNormalTotalTime(),
                            "Normal " + totalTime.getNormalTime()));


                    totaltime.add(new PieEntry(totalTime.getHardTotalTime(),
                            "Hard " + totalTime.getHardTime()));


                    totaltime.add(new PieEntry(totalTime.getVeryhardTotalTime(),
                            "Very Hard " + totalTime.getVeryhardTime()));

                    PieDataSet totaltimeDataset = new PieDataSet(totaltime, "");
                    totaltimeDataset.setColors(pieChartColors);
                    setPieChart(holder.mPieChart, totaltimeDataset);
                    break;
                }
        }
    }

    @Override
    public int getItemCount() {
        return mStatisticsArrayList.size();
    }

    private void setPieChart(PieChart pieChart, PieDataSet dataSet) {
        //Sets the pie chart to use percentages
        pieChart.setUsePercentValues(true);

        //set offests for the pie chart layout
        pieChart.setExtraOffsets(5, 5, 5, 5);

        //set the speed at which the graph decelerates after drag
        pieChart.setDragDecelerationFrictionCoef(0.9f);

        //sets the alpha value of the transparent inner circle
        pieChart.setTransparentCircleRadius(61f);

        //sets the color of the center hole of the graph
        pieChart.setHoleColor(mContext.getResources().getColor(R.color.transparent));

        pieChart.setHoleRadius(50f);

        //sets an animation when drawing the graph
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        // disable the pie chart description label
        pieChart.getDescription().setEnabled(false);

        PieData pieData = new PieData(dataSet); //create a new instance of PieData passing the data set
        pieData.setValueFormatter(new PercentFormatter());

        pieData.setValueTextSize(10f); //set the text sie of the pie chart values

        pieData.setValueTextColor(Color.BLACK); //set the color of the pie chart text

        pieChart.setDrawCenterText(true);
        pieChart.setDrawEntryLabels(false);
        pieChart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        pieChart.getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        pieChart.getLegend().setWordWrapEnabled(true);
        pieChart.setData(pieData); //set the data to the pie chart

        //PieChart Ends Here
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView total;
        PieChart mPieChart;

        private MyViewHolder(View itemView) {

            super(itemView);
            this.title = itemView.findViewById(R.id.statsTitle);
            this.total = itemView.findViewById(R.id.statstotal);
            this.mPieChart = itemView.findViewById(R.id.piechart_1);
        }

    }
}
