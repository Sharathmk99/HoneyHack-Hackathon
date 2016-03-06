package com.wikitude.samples;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.wikitude.nativesdksampleapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HumidifiersActivity extends AppCompatActivity {
    @Bind(R.id.hum_battery)
    LineChart mChartBattery;
    @Bind(R.id.hum_cleaner_eff)
    LineChart mChartEff;
    @Bind(R.id.hum_power_chart)
    LineChart mChartPower;
    @Bind(R.id.hum_status)
    Switch status;
    @Bind(R.id.hum_battery_text)
    TextView mTextBattery;
    @Bind(R.id.hum_cleaner_text)
    TextView mTextCleaner;
    @Bind(R.id.hum_power_text)
    TextView mTextPower;
    @Bind(R.id.hum_status_text)
    TextView mTextStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_humidifiers);
        ButterKnife.bind(this);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "learningcurve.otf");
        mTextBattery.setTypeface(typeFace);
        mTextCleaner.setTypeface(typeFace);
        mTextPower.setTypeface(typeFace);
        mTextStatus.setTypeface(typeFace);
        status.setChecked(false);
        setDataPower();
        setDataEff();
        setDataBattery();
    }

    private ArrayList<String> generateDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        ArrayList<String> dates = new ArrayList<String>();
        for (int i = 0; i < 7; i++){
            dates.add(simpleDateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DATE,-1);
        }
        return dates;
    }
    private ArrayList<String> generateYear() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        ArrayList<String> dates = new ArrayList<String>();
        for (int i = 0; i < 7; i++){
            dates.add(simpleDateFormat.format(calendar.getTime()));
            calendar.add(Calendar.YEAR,-1);
        }
        return dates;
    }

    private void setDataPower() {
        Random r = new Random();
        int Low = 45;
        int High = 65;
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
        for (int i = 0; i < 7; i++) {
            yVals2.add(new Entry(r.nextInt(High - Low) + Low, i));
        }
        LineDataSet set2 = new LineDataSet(yVals2, "Dryness in Percentage");
        set2.setColor(Color.parseColor("#F9EE27"));
        set2.setLineWidth(1f);
        set2.setCircleSize(3f);
        set2.setDrawCircleHole(false);
        set2.setDrawValues(false);
        set2.setFillAlpha(65);
        set2.setFillColor(Color.BLACK);
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set2);
        LineData data = new LineData(generateDate(), dataSets);
        data.setValueTextColor(Color.parseColor("#F9EE27"));
        data.setValueTextSize(9f);
        mChartPower.setDrawGridBackground(false);
        mChartPower.getAxisRight().setEnabled(false);
        mChartPower.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        Legend l = mChartPower.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.WHITE);
        mChartPower.setDescription("");
        mChartPower.setNoDataTextDescription("No Data Available");
        mChartPower.getXAxis().setTextColor(Color.parseColor("#F9EE27"));
        mChartPower.getAxisLeft().setTextColor(Color.parseColor("#F9EE27"));
//        mChartPower.getAxisLeft().setAxisMaxValue(100);
//        mChartPower.getAxisLeft().setAxisMaxValue(80);
        mChartPower.getAxisLeft().setStartAtZero(false);
        mChartPower.setData(data);
    }

    private void setDataBattery() {
        Random r = new Random();
        int Low = 60;
        int High = 80;
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
        for (int i = 0; i < 7; i++) {
            yVals2.add(new Entry(r.nextInt(High - Low) + Low, i));
        }
        LineDataSet set2 = new LineDataSet(yVals2, "Power Consumption in Watts");
        set2.setColor(Color.parseColor("#F9EE27"));
        set2.setLineWidth(1f);
        set2.setCircleSize(3f);
        set2.setDrawCircleHole(false);
        set2.setDrawValues(false);
        set2.setFillAlpha(65);
        set2.setFillColor(Color.BLACK);
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set2);
        LineData data = new LineData(generateDate(), dataSets);
        data.setValueTextColor(Color.parseColor("#F9EE27"));
        data.setValueTextSize(9f);
        mChartBattery.setDrawGridBackground(false);
        mChartBattery.getAxisRight().setEnabled(false);
        mChartBattery.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        Legend l = mChartBattery.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.WHITE);
        mChartBattery.setDescription("");
        mChartBattery.setNoDataTextDescription("No Data Available");
        mChartBattery.getXAxis().setTextColor(Color.parseColor("#F9EE27"));
        mChartBattery.getAxisLeft().setTextColor(Color.parseColor("#F9EE27"));
//        mChartPower.getAxisLeft().setAxisMaxValue(100);
//        mChartPower.getAxisLeft().setAxisMaxValue(80);
        mChartBattery.getAxisLeft().setStartAtZero(false);
        mChartBattery.setData(data);
    }

    private void setDataEff() {
        Random r = new Random();
        int Low = 5;
        int High = 10;
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
        for (int i = 0; i < 7; i++) {
            yVals2.add(new Entry(r.nextInt(High - Low) + Low, i));
        }
        LineDataSet set2 = new LineDataSet(yVals2, "Efficiency - Water Saved in gallons");
        set2.setColor(Color.parseColor("#F9EE27"));
        set2.setLineWidth(1f);
        set2.setCircleSize(3f);
        set2.setDrawCircleHole(false);
        set2.setDrawValues(false);
        set2.setFillAlpha(65);
        set2.setFillColor(Color.BLACK);
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(set2);
        LineData data = new LineData(generateYear(), dataSets);
        data.setValueTextColor(Color.parseColor("#F9EE27"));
        data.setValueTextSize(9f);
        mChartEff.setDrawGridBackground(false);
        mChartEff.getAxisRight().setEnabled(false);
        mChartEff.animateX(2500, Easing.EasingOption.EaseInOutQuart);
        Legend l = mChartEff.getLegend();
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.WHITE);
        mChartEff.setDescription("");
        mChartEff.setNoDataTextDescription("No Data Available");
        mChartEff.getXAxis().setTextColor(Color.parseColor("#F9EE27"));
        mChartEff.getAxisLeft().setTextColor(Color.parseColor("#F9EE27"));
//        mChartPower.getAxisLeft().setAxisMaxValue(100);
//        mChartPower.getAxisLeft().setAxisMaxValue(80);
        mChartEff.getAxisLeft().setStartAtZero(false);
        mChartEff.setData(data);
    }
}
