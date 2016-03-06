package com.wikitude.samples;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.wikitude.nativesdksampleapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ThermostatsActivitty extends AppCompatActivity {
    @Bind(R.id.ther_date)
    TextView lastDate;
    @Bind(R.id.ther_humd)
    TextView indoorHum;
    @Bind(R.id.ther_indoor)
    TextView indoor;
    @Bind(R.id.ther_outdoor)
    TextView outdoor;
    @Bind(R.id.ther_location)
    TextView location;
    @Bind(R.id.ther_temp)
    TextView indoorTemp;
    @Bind(R.id.ther_outdoor_hum)
    TextView outdoorHum;
    @Bind(R.id.ther_outdoor_temp)
    TextView outdoorTemp;
    @Bind(R.id.ther_status)
    TextView status;
    @Bind(R.id.ther_set)
    TextView set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thermostats_activitty);
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

}
