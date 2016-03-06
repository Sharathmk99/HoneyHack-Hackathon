package com.wikitude.samples.plugins;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.wikitude.WikitudeSDK;
import com.wikitude.WikitudeSDKStartupConfiguration;
import com.wikitude.common.camera.CameraSettings;
import com.wikitude.common.rendering.RenderExtension;
import com.wikitude.common.tracking.RecognizedTarget;
import com.wikitude.nativesdksampleapp.R;
import com.wikitude.rendering.ExternalRendering;
import com.wikitude.samples.WikitudeSDKConstants;
import com.wikitude.samples.rendering.external.CustomSurfaceView;
import com.wikitude.samples.rendering.external.Driver;
import com.wikitude.samples.rendering.external.GLRenderer;
import com.wikitude.tracker.ClientTracker;
import com.wikitude.tracker.ClientTrackerEventListener;
import com.wikitude.tracker.Tracker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import butterknife.Bind;


public class BarcodePluginActivity extends Activity implements ClientTrackerEventListener, ExternalRendering {

    private static final String TAG = "BarcodePlugin";
    private static String _codeContent;

    private WikitudeSDK _wikitudeSDK;
    private CustomSurfaceView _customSurfaceView;
    private Driver _driver;
    private GLRenderer _glRenderer;
    private boolean isObjectFound = false;
    private boolean isBarCodedetected = false;
    String category = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _wikitudeSDK = new WikitudeSDK(this);
        WikitudeSDKStartupConfiguration startupConfiguration = new WikitudeSDKStartupConfiguration(WikitudeSDKConstants.WIKITUDE_SDK_KEY, CameraSettings.CameraPosition.BACK, CameraSettings.CameraFocusMode.CONTINUOUS);
        _wikitudeSDK.onCreate(getApplicationContext(), startupConfiguration);
        ClientTracker tracker = null;
        if (getIntent().getStringExtra("category") != null) {
            category = getIntent().getStringExtra("category");
            Log.v(TAG,category);
            Log.v("HoneyHack",getIntent().getStringExtra("category"));
            if (getIntent().getStringExtra("category").equalsIgnoreCase("AirCleaner")) {
                tracker = _wikitudeSDK.getTrackerManager().create2dClientTracker("file:///android_asset/airclearner.wtc");
            } else if (getIntent().getStringExtra("category").equalsIgnoreCase("Humidifer")) {
                tracker = _wikitudeSDK.getTrackerManager().create2dClientTracker("file:///android_asset/humidifiers.wtc");
            } else if (getIntent().getStringExtra("category").equalsIgnoreCase("LightTimers")) {
                tracker = _wikitudeSDK.getTrackerManager().create2dClientTracker("file:///android_asset/lighttimer.wtc");
            } else if (getIntent().getStringExtra("category").equalsIgnoreCase("Thermostats")) {
                tracker = _wikitudeSDK.getTrackerManager().create2dClientTracker("file:///android_asset/thermostats.wtc");
            }
        }
        tracker.registerTrackerEventListener(this);
        _wikitudeSDK.getPluginManager().registerNativePlugins("wikitudePlugins", "barcode");
        initNative();
    }

    @Override
    protected void onResume() {
        super.onResume();
        _wikitudeSDK.onResume();
        _customSurfaceView.onResume();
        _driver.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        _wikitudeSDK.onPause();
        _customSurfaceView.onPause();
        _driver.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _wikitudeSDK.onDestroy();
    }

    @Override
    public void onRenderExtensionCreated(final RenderExtension renderExtension_) {
        _glRenderer = new GLRenderer(renderExtension_);
        _customSurfaceView = new CustomSurfaceView(getApplicationContext(), _glRenderer);
        _driver = new Driver(_customSurfaceView, 30);
        setContentView(_customSurfaceView);
        FrameLayout viewHolder = new FrameLayout(getApplicationContext());
        setContentView(viewHolder);
        viewHolder.addView(_customSurfaceView);
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        if(getIntent().getStringExtra("category").equalsIgnoreCase("Thermostats")){
            ScrollView barcodeLayout = (ScrollView) inflater.inflate(R.layout.ar_thermostats, null);
            viewHolder.addView(barcodeLayout);
            Typeface typeFace = Typeface.createFromAsset(getAssets(), "learningcurve.otf");
            TextView lastDate = (TextView)findViewById(R.id.ar_ther_date);
            lastDate.setTypeface(typeFace);
            TextView indoorHum = (TextView)findViewById(R.id.ar_ther_humd);
            indoorHum.setTypeface(typeFace);
            TextView indoor = (TextView)findViewById(R.id.ar_ther_indoor);
            indoor.setTypeface(typeFace);
            TextView outdoor = (TextView)findViewById(R.id.ar_ther_outdoor);
            outdoor.setTypeface(typeFace);
            TextView location = (TextView)findViewById(R.id.ar_ther_location);
            location.setTypeface(typeFace);
            TextView indoorTemp = (TextView)findViewById(R.id.ar_ther_temp);
            indoorTemp.setTypeface(typeFace);
            TextView outdoorHum = (TextView)findViewById(R.id.ar_ther_outdoor_hum);
            outdoorHum.setTypeface(typeFace);
            TextView outdoorTemp = (TextView)findViewById(R.id.ar_ther_outdoor_temp);
            outdoorTemp.setTypeface(typeFace);
            TextView status = (TextView)findViewById(R.id.ar_ther_status);
            status.setTypeface(typeFace);
            TextView set = (TextView)findViewById(R.id.ar_ther_set);
            set.setTypeface(typeFace);
        }else  if(getIntent().getStringExtra("category").equalsIgnoreCase("LightTimers")){
            LinearLayout barcodeLayout = (LinearLayout) inflater.inflate(R.layout.content_arlight_timer, null);
            viewHolder.addView(barcodeLayout);
            Typeface typeFace = Typeface.createFromAsset(getAssets(), "learningcurve.otf");
            TextView lightStatusText = (TextView)findViewById(R.id.ar_light_status_text);
            lightStatusText.setTypeface(typeFace);
            TextView lightSchedules = (TextView)findViewById(R.id.ar_light_schedules);
            lightSchedules.setTypeface(typeFace);
            TextView lightTime = (TextView)findViewById(R.id.ar_light_time);
            lightTime.setTypeface(typeFace);
            TextView lightSchedule = (TextView)findViewById(R.id.ar_light_schedule);
            lightSchedule.setTypeface(typeFace);
            TextView lightMode = (TextView)findViewById(R.id.ar_light_mode);
            lightMode.setTypeface(typeFace);
            Switch lightStatus = (Switch)findViewById(R.id.ar_light_status);
            lightStatus.setEnabled(true);
        }else if(getIntent().getStringExtra("category").equalsIgnoreCase("Humidifer")){
            ScrollView barcodeLayout = (ScrollView) inflater.inflate(R.layout.content_arhumidifiers, null);
            viewHolder.addView(barcodeLayout);
            Typeface typeFace = Typeface.createFromAsset(getAssets(), "learningcurve.otf");
            TextView mTextBattery = (TextView)findViewById(R.id.ar_hum_battery_text);
            mTextBattery.setTypeface(typeFace);
            TextView mTextCleaner = (TextView)findViewById(R.id.ar_hum_cleaner_text);
            mTextCleaner.setTypeface(typeFace);
            TextView mTextPower = (TextView)findViewById(R.id.ar_hum_power_text);
            mTextPower.setTypeface(typeFace);
            TextView mTextStatus = (TextView)findViewById(R.id.ar_hum_status_text);
            mTextStatus.setTypeface(typeFace);
            Switch status = (Switch)findViewById(R.id.ar_hum_status);
            status.setChecked(false);
        }else if(getIntent().getStringExtra("category").equalsIgnoreCase("AirCleaner")){
            ScrollView barcodeLayout = (ScrollView) inflater.inflate(R.layout.content_ar_air_clearner, null);
            viewHolder.addView(barcodeLayout);
            Typeface typeFace = Typeface.createFromAsset(getAssets(), "learningcurve.otf");
            TextView mTextBattery = (TextView)findViewById(R.id.ar_air_battery_text);
            mTextBattery.setTypeface(typeFace);
            TextView mTextCleaner = (TextView)findViewById(R.id.ar_air_cleaner_text);
            mTextCleaner.setTypeface(typeFace);
            TextView mTextPower = (TextView)findViewById(R.id.ar_air_power_text);
            mTextPower.setTypeface(typeFace);
            TextView mTextStatus = (TextView)findViewById(R.id.ar_air_status_text);
            mTextStatus.setTypeface(typeFace);
            Switch status = (Switch)findViewById(R.id.ar_air_status);
            status.setChecked(true);
        }
    }

    @Override
    public void onErrorLoading(final ClientTracker clientTracker_, final String errorMessage_) {
        Log.v(TAG, "onErrorLoading: " + errorMessage_);
    }

    @Override
    public void onTrackerFinishedLoading(final ClientTracker clientTracker_, final String trackerFilePath_) {

    }

    @Override
    public void onTargetRecognized(final Tracker tracker_, final String targetName_) {
        Log.v(TAG, "Detected: " + targetName_);
        isObjectFound = true;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(getIntent().getStringExtra("category").equalsIgnoreCase("Thermostats")){
                    ScrollView scrollView = (ScrollView)findViewById(R.id.ther_view);
                    if(scrollView.getVisibility() == View.INVISIBLE){
                        TextView indoorTemp = (TextView)findViewById(R.id.ar_ther_temp);
                        indoorTemp.setText(new Random().nextInt(34 - 30) + 30 + "°");
                        TextView outdoorTemp = (TextView)findViewById(R.id.ar_ther_outdoor_temp);
                        outdoorTemp.setText(new Random().nextInt(34 - 30) + 30+ "°");
                        TextView set = (TextView)findViewById(R.id.ar_ther_set);
                        set.setText("set to "+(new Random().nextInt(34 - 30) + 30)+ "°");
                        TextView lastDate = (TextView)findViewById(R.id.ar_ther_date);
                        lastDate.setText("Last Update: " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                        scrollView.setVisibility(View.VISIBLE);
                    }
                }else if(getIntent().getStringExtra("category").equalsIgnoreCase("LightTimers")){
                    LinearLayout scrollView = (LinearLayout)findViewById(R.id.light_view);
                    if(scrollView.getVisibility() == View.INVISIBLE){
                        TextView lightTime = (TextView)findViewById(R.id.ar_light_time);
                        lightTime.setText("Time: " + new SimpleDateFormat("hh:mm a").format(new Date()));
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.HOUR_OF_DAY,1);
                        TextView lightSchedules = (TextView)findViewById(R.id.ar_light_schedules);
                        lightSchedules.setText("\t\tTime: " + new SimpleDateFormat("hh:mm a").format(calendar.getTime())+ "\n\t\tMode: On");
                        scrollView.setVisibility(View.VISIBLE);
                    }
                }else if(getIntent().getStringExtra("category").equalsIgnoreCase("Humidifer")){
                    ScrollView scrollView = (ScrollView)findViewById(R.id.hum_view);
                    if(scrollView.getVisibility() == View.INVISIBLE){
                        setDataPower();
                        setDataEff();
                        setDataBattery();
                        scrollView.setVisibility(View.VISIBLE);
                    }
                }else if(getIntent().getStringExtra("category").equalsIgnoreCase("AirCleaner")){
                    ScrollView scrollView = (ScrollView)findViewById(R.id.air_view);
                    if(scrollView.getVisibility() == View.INVISIBLE){
                        setDataPowerAir();
                        setDataEffAir();
                        setDataBatteryAir();
                        scrollView.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

        //displayText();
    }

    @Override
    public void onTracking(final Tracker tracker_, final RecognizedTarget recognizedTarget_) {
       // _glRenderer.setCurrentlyRecognizedTarget(recognizedTarget_);
    }

    private void displayText() {
        if (isBarCodedetected && isObjectFound) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView targetInformationTextField = (TextView) findViewById(R.id.barcode_plugin_info_field);
                    targetInformationTextField.setText("Water Level is Half");
                    targetInformationTextField.setVisibility(View.VISIBLE);
                }
            });
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView targetInformationTextField = (TextView) findViewById(R.id.barcode_plugin_info_field);
                    targetInformationTextField.setText("Water Level is Half");
                    targetInformationTextField.setVisibility(View.INVISIBLE);
                }
            });
        }

    }

    @Override
    public void onTargetLost(final Tracker tracker_, final String targetName_) {
        _glRenderer.setCurrentlyRecognizedTarget(null);
        isObjectFound = false;
        isBarCodedetected = false;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(getIntent().getStringExtra("category").equalsIgnoreCase("Thermostats")){
                    ScrollView scrollView = (ScrollView)findViewById(R.id.ther_view);
                    scrollView.setVisibility(View.INVISIBLE);
                }else if(getIntent().getStringExtra("category").equalsIgnoreCase("LightTimers")){
                    LinearLayout scrollView = (LinearLayout)findViewById(R.id.light_view);
                    scrollView.setVisibility(View.INVISIBLE);
                }else if(getIntent().getStringExtra("category").equalsIgnoreCase("Humidifer")){
                    ScrollView scrollView = (ScrollView)findViewById(R.id.hum_view);
                    scrollView.setVisibility(View.INVISIBLE);
                }else if(getIntent().getStringExtra("category").equalsIgnoreCase("AirCleaner")){
                    ScrollView scrollView = (ScrollView)findViewById(R.id.air_view);
                    scrollView.setVisibility(View.INVISIBLE);
                }
            }
        });
       // displayText();
    }

    @Override
    public void onExtendedTrackingQualityUpdate(final Tracker tracker_, final String targetName_, final int oldTrackingQuality_, final int newTrackingQuality_) {

    }

    public void onBarcodeDetected(final String codeContent_) {
        _codeContent = codeContent_;
        isBarCodedetected = true;
        //displayText();
    }

    private native void initNative();

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
        LineChart mChartPower = (LineChart)findViewById(R.id.ar_hum_power_chart);
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
        LineChart mChartBattery = (LineChart)findViewById(R.id.ar_hum_battery);
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
        LineChart mChartEff = (LineChart)findViewById(R.id.ar_hum_cleaner_eff);
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



    private void setDataPowerAir() {
        Random r = new Random();
        int Low = 80;
        int High = 100;
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
        LineChart mChartPower = (LineChart)findViewById(R.id.ar_air_power_chart);
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

    private void setDataBatteryAir() {
        Random r = new Random();
        int Low = 3;
        int High = 15;
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
        for (int i = 0; i < 7; i++) {
            yVals2.add(new Entry(r.nextInt(High - Low) + Low, i));
        }
        LineDataSet set2 = new LineDataSet(yVals2, "Power Consumption in Percentage");
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
        LineChart mChartBattery = (LineChart)findViewById(R.id.ar_air_battery);
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

    private void setDataEffAir() {
        Random r = new Random();
        int Low = 60;
        int High = 80;
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
        for (int i = 0; i < 7; i++) {
            yVals2.add(new Entry(r.nextInt(High - Low) + Low, i));
        }
        LineDataSet set2 = new LineDataSet(yVals2, "Efficiency Consumption in Percentage");
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
        LineChart mChartEff = (LineChart)findViewById(R.id.ar_air_cleaner_eff);
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