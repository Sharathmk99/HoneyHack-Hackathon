package com.wikitude.samples;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wikitude.nativesdksampleapp.R;
import com.wikitude.samples.plugins.BarcodePluginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CategoryGridActivity extends AppCompatActivity {

    @Bind(R.id.gridView)
    GridView gridView;
    @Bind(R.id.location)
    TextView location;
    @Bind(R.id.change_location)
    Button changeLocation;
    @Bind(R.id.layout_location)
    LinearLayout linearLayout;
    private int[] icons = {R.drawable.thermostats, R.drawable.homeairquality, R.drawable.humidifiers, R.drawable.lighttimer};
    private String[] names = {"Thermostats", "Air Cleaner", "Humidifier", "Light Timers"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_grid);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        final Typeface typeFace = Typeface.createFromAsset(getAssets(), "learningcurve.otf");
        location.setTypeface(typeFace);
        final SharedPreferences sharedpreferences = getSharedPreferences("HoneyHack", Context.MODE_PRIVATE);
        if(sharedpreferences.getString("location",null) == null){
            linearLayout.setVisibility(View.INVISIBLE);
        }else{
            location.setText("Location: "+ sharedpreferences.getString("location",null));
        }
        changeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedpreferences.edit().putString("location",null);
                startActivity(new Intent(CategoryGridActivity.this,LocationSelectionActivity.class));
                finish();
            }
        });
        gridView.setAdapter(new GridAdapter());
        gridView.setVerticalSpacing(1);
        gridView.setHorizontalSpacing(1);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(CategoryGridActivity.this,R.style.AppTheme_Dark_Dialog);
                LayoutInflater inflater = CategoryGridActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_helper, null);
                ((TextView)dialogView.findViewById(R.id.dialog_ar)).setTypeface(typeFace);
                ((TextView)dialogView.findViewById(R.id.dialog_charts)).setTypeface(typeFace);
                dialogBuilder.setView(dialogView);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.show();
                ImageView ar = (ImageView)dialogView.findViewById(R.id.ar);
                ar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CategoryGridActivity.this, BarcodePluginActivity.class);
                        if (pos == 1) {
                            intent.putExtra("category","AirCleaner");
                        }else if(pos == 2){
                            intent.putExtra("category","Humidifer");
                        }else if(pos == 3){
                            intent.putExtra("category","LightTimers");
                        }else if(pos == 0){
                            intent.putExtra("category","Thermostats");
                        }
                        startActivity(intent);
                        if(alertDialog.isShowing())
                            alertDialog.dismiss();
                    }
                });
                ImageView charts = (ImageView)dialogView.findViewById(R.id.chart);
                charts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = null;
                        if (pos == 1) {
                            intent = new Intent(CategoryGridActivity.this, AirCleanerActivity.class);
                            intent.putExtra("category","AirCleaner");
                        }else if(pos == 2){
                            intent = new Intent(CategoryGridActivity.this, HumidifiersActivity.class);
                            intent.putExtra("category","Humidifer");
                        }else if(pos == 3){
                            intent = new Intent(CategoryGridActivity.this, LightTimer.class);
                            intent.putExtra("category","LightTimers");
                        }else if(pos == 0){
                            intent = new Intent(CategoryGridActivity.this, ThermostatsActivitty.class);
                            intent.putExtra("category","Thermostats");
                        }
                        startActivity(intent);
                        if(alertDialog.isShowing())
                            alertDialog.dismiss();
                    }
                });

            }
        });
    }

    private class GridAdapter extends BaseAdapter {

        Helper helper;

        @Override
        public int getCount() {
            return icons.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) CategoryGridActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.grid_helper, parent, false);
                helper = new Helper();
                helper.imageView = (ImageView) convertView.findViewById(R.id.item_image);
                helper.textView = (TextView) convertView.findViewById(R.id.item_name);
                Typeface typeFace = Typeface.createFromAsset(getAssets(), "learningcurve.otf");
                helper.textView.setTypeface(typeFace);
                convertView.setTag(helper);
            } else {
                helper = (Helper) convertView.getTag();
            }
            helper.textView.setText(names[position]);
            helper.imageView.setImageResource(icons[position]);
            return convertView;
        }

        private class Helper {
            ImageView imageView;
            TextView textView;
        }
    }
}
