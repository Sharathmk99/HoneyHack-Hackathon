package com.wikitude.samples.plugins;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

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
import java.util.Date;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;


public class BarcodePluginActivity extends Activity implements ClientTrackerEventListener, ExternalRendering {

    private static final String TAG = "BarcodePlugin";
    private static String _codeContent;

    private WikitudeSDK _wikitudeSDK;
    private CustomSurfaceView _customSurfaceView;
    private Driver _driver;
    private GLRenderer _glRenderer;
    private boolean isObjectFound = false;
    private boolean isBarCodedetected = false;
    @Bind(R.id.ar_ther_date)
    TextView lastDate;
    @Bind(R.id.ar_ther_humd)
    TextView indoorHum;
    @Bind(R.id.ar_ther_indoor)
    TextView indoor;
    @Bind(R.id.ar_ther_outdoor)
    TextView outdoor;
    @Bind(R.id.ar_ther_location)
    TextView location;
    @Bind(R.id.ar_ther_temp)
    TextView indoorTemp;
    @Bind(R.id.ar_ther_outdoor_hum)
    TextView outdoorHum;
    @Bind(R.id.ar_ther_outdoor_temp)
    TextView outdoorTemp;
    @Bind(R.id.ar_ther_status)
    TextView status;
    @Bind(R.id.ar_ther_set)
    TextView set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _wikitudeSDK = new WikitudeSDK(this);
        WikitudeSDKStartupConfiguration startupConfiguration = new WikitudeSDKStartupConfiguration(WikitudeSDKConstants.WIKITUDE_SDK_KEY, CameraSettings.CameraPosition.BACK, CameraSettings.CameraFocusMode.CONTINUOUS);
        _wikitudeSDK.onCreate(getApplicationContext(), startupConfiguration);
        ClientTracker tracker = null;
        if (getIntent().getStringExtra("category") != null) {
            Log.v("HoneyHack",getIntent().getStringExtra("category"));
            if (getIntent().getStringExtra("category").equalsIgnoreCase("AirCleaner")) {
                tracker = _wikitudeSDK.getTrackerManager().create2dClientTracker("file:///android_asset/aircleaner.wtc");
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
        ScrollView barcodeLayout = (ScrollView) inflater.inflate(R.layout.ar_thermostats, null);

        viewHolder.addView(barcodeLayout);
        ButterKnife.bind(this);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "learningcurve.otf");
        lastDate.setTypeface(typeFace);
        indoorHum.setTypeface(typeFace);
        indoor.setTypeface(typeFace);
        outdoor.setTypeface(typeFace);
        location.setTypeface(typeFace);
        indoorTemp.setTypeface(typeFace);
        outdoorHum.setTypeface(typeFace);
        outdoorTemp.setTypeface(typeFace);
        status.setTypeface(typeFace);
        set.setTypeface(typeFace);
        indoorTemp.setText(new Random().nextInt(34 - 30) + 30 + "°");
        outdoorTemp.setText(new Random().nextInt(34 - 30) + 30+ "°");
        set.setText("set to "+(new Random().nextInt(34 - 30) + 30)+ "°");
        lastDate.setText("Last Update: " + new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
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
                ScrollView scrollView = (ScrollView)findViewById(R.id.ther_view);
                scrollView.setVisibility(View.VISIBLE);
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
                ScrollView scrollView = (ScrollView)findViewById(R.id.ther_view);
                scrollView.setVisibility(View.INVISIBLE);
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
        displayText();
    }

    private native void initNative();
}