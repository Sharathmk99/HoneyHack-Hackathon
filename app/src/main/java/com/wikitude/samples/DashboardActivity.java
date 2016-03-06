package com.wikitude.samples;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.wikitude.nativesdksampleapp.R;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private static String LOG_TAG = "HoneyHack";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);
        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this,CategoryGridActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void setData(PieChart pieChart) {
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
        yVals2.add(new Entry(4, 0));
        yVals2.add(new Entry(3, 1));
        yVals2.add(new Entry(1, 2));
        yVals2.add(new Entry(5, 3));
        PieDataSet set2 = new PieDataSet(yVals2, "");
        set2.setColor(Color.parseColor("#F9EE27"));
        set2.setSliceSpace(3f);
        set2.setSelectionShift(5f);
        pieChart.setDrawCenterText(true);
        pieChart.setHoleColor(Color.parseColor("#8F5050"));
        set2.setDrawValues(false);
        pieChart.setDrawSliceText(false);
        pieChart.setTouchEnabled(true);


        // set the marker to the chart

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        set2.setColors(colors);
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Thermostats");
        xVals.add("Air Cleaner");
        xVals.add("Humidifier");
        xVals.add("Light Timers");
        MyMarkerView mv = new MyMarkerView(this,
                R.layout.custommarker,xVals);
        pieChart.setMarkerView(mv);
        PieData data = new PieData(xVals, set2);
        data.setValueTextColor(Color.parseColor("#F9EE27"));
        data.setValueTextSize(9f);
        pieChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setTextColor(Color.parseColor("#F9EE27"));
        pieChart.setDescription("");
        pieChart.setNoDataTextDescription("No Data Available");
        pieChart.setData(data);
    }

    private void setDataPower(PieChart pieChart) {
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
        yVals2.add(new Entry(900, 0));
        yVals2.add(new Entry(800, 1));
        yVals2.add(new Entry(1200, 2));
        yVals2.add(new Entry(400, 3));
        PieDataSet set2 = new PieDataSet(yVals2, "");
        set2.setColor(Color.parseColor("#F9EE27"));
        set2.setSliceSpace(3f);
        set2.setSelectionShift(5f);
        pieChart.setDrawCenterText(true);
        pieChart.setHoleColor(Color.parseColor("#8F5050"));
        set2.setDrawValues(false);
        pieChart.setDrawSliceText(false);
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        set2.setColors(colors);
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Thermostats");
        xVals.add("Air Cleaner");
        xVals.add("Humidifier");
        xVals.add("Light Timers");
        PieData data = new PieData(xVals, set2);
        data.setValueTextColor(Color.parseColor("#F9EE27"));
        data.setValueTextSize(9f);
        pieChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        Legend l = pieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setTextColor(Color.parseColor("#F9EE27"));
        pieChart.setDescription("");
        pieChart.setNoDataTextDescription("No Data Available");
        pieChart.setData(data);
    }

    private void setDataEff(BarChart barChart) {
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setDescription("");
        barChart.setDrawGridBackground(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(2);

        ArrayList<BarEntry> yVals2 = new ArrayList<BarEntry>();
        yVals2.add(new BarEntry(900, 0));
        yVals2.add(new BarEntry(800, 1));
        yVals2.add(new BarEntry(1200, 2));
        yVals2.add(new BarEntry(400, 3));
        BarDataSet set2 = new BarDataSet(yVals2, "");
        set2.setColor(Color.parseColor("#F9EE27"));
        set2.setDrawValues(false);
        set2.setBarSpacePercent(35f);
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        set2.setColors(colors);
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Ther..");
        xVals.add("Air..");
        xVals.add("Humi..");
        xVals.add("Ligh..");
        barChart.getAxisRight().setEnabled(false);
        BarData data = new BarData(xVals, set2);
        data.setValueTextColor(Color.parseColor("#F9EE27"));
        data.setValueTextSize(9f);
        barChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        barChart.getXAxis().setTextColor(Color.parseColor("#F9EE27"));
        barChart.getAxisLeft().setTextColor(Color.parseColor("#F9EE27"));
        barChart.getLegend().setEnabled(false);

        barChart.setDescription("");
        barChart.setNoDataTextDescription("No Data Available");
        barChart.setData(data);
    }

    private class MyRecyclerViewAdapter extends RecyclerView
            .Adapter<RecyclerView.ViewHolder> {

        public MyRecyclerViewAdapter() {
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
            if (viewType == 0) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_view, parent, false);
                DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
                return dataObjectHolder;
            } else if (viewType == 1) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.bar_chart, parent, false);
                DataObjectHolder1 dataObjectHolder = new DataObjectHolder1(view);
                return dataObjectHolder;
            } else {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.card_view, parent, false);
                DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
                return dataObjectHolder;
            }

        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Typeface typeFace = Typeface.createFromAsset(getAssets(), "learningcurve.otf");
            if (position == 0) {
                DataObjectHolder dataObjectHolder = (DataObjectHolder)holder;
                dataObjectHolder.label.setText("Total Number Of Asset");
                dataObjectHolder.label.setTypeface(typeFace);
                setData(dataObjectHolder.mChart);
            } else if (position == 1) {
                DataObjectHolder1 dataObjectHolder = (DataObjectHolder1)holder;
                dataObjectHolder.label.setText("Efficiency");
                dataObjectHolder.label.setTypeface(typeFace);
                setDataEff(dataObjectHolder.mChart);
            } else {
                DataObjectHolder dataObjectHolder = (DataObjectHolder)holder;
                dataObjectHolder.label.setText("Total Power in Watts");
                dataObjectHolder.label.setTypeface(typeFace);
                setDataPower(dataObjectHolder.mChart);
            }

        }

        @Override
        public int getItemCount() {
            return 3;
        }

        public class DataObjectHolder extends RecyclerView.ViewHolder
                implements View
                .OnClickListener {
            TextView label;
            PieChart mChart;

            public DataObjectHolder(View itemView) {
                super(itemView);
                label = (TextView) itemView.findViewById(R.id.textView);

                mChart = (PieChart) itemView.findViewById(R.id.pie_chart);

                Log.i(LOG_TAG, "Adding Listener");
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

            }
        }

        public class DataObjectHolder1 extends RecyclerView.ViewHolder
                implements View
                .OnClickListener {
            TextView label;
            BarChart mChart;

            public DataObjectHolder1(View itemView) {
                super(itemView);
                label = (TextView) itemView.findViewById(R.id.textView);

                mChart = (BarChart) itemView.findViewById(R.id.bar_chart);

                Log.i(LOG_TAG, "Adding Listener");
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {

            }
        }

    }
}
