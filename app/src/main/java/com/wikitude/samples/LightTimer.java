package com.wikitude.samples;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;
import android.widget.TextView;

import com.wikitude.nativesdksampleapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LightTimer extends AppCompatActivity {

    @Bind(R.id.light_mode)
    TextView lightMode;
    @Bind(R.id.light_schedule)
    TextView lightSchedule;
    @Bind(R.id.light_time)
    TextView lightTime;
    @Bind(R.id.light_schedules)
    TextView lightSchedules;
    @Bind(R.id.light_status_text)
    TextView lightStatusText;
    @Bind(R.id.light_status)
    Switch lightStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_timer);
        ButterKnife.bind(this);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "learningcurve.otf");
        lightStatusText.setTypeface(typeFace);
        lightSchedules.setTypeface(typeFace);
        lightTime.setTypeface(typeFace);
        lightSchedule.setTypeface(typeFace);
        lightMode.setTypeface(typeFace);
        lightStatus.setEnabled(true);
        lightTime.setText("Time: " + new SimpleDateFormat("hh:mm a").format(new Date()));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY,1);
        lightSchedules.setText("\t\tTime: " + new SimpleDateFormat("hh:mm a").format(calendar.getTime())+ "\n\t\tMode: On");
    }

}
