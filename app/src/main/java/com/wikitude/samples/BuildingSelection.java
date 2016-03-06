package com.wikitude.samples;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wikitude.nativesdksampleapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BuildingSelection extends AppCompatActivity {

    @Bind(R.id.building_names_list)
    ListView listView;
    private String[] buildingNames = {"Building 17", "Building 40"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_selection);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        listView.setAdapter(new ListViewAapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(getIntent().getStringExtra("category") != null){
                    if(getIntent().getStringExtra("category").equalsIgnoreCase("AirCleaner")){
                        startActivity(new Intent(BuildingSelection.this, AirCleanerActivity.class));
                    }else if(getIntent().getStringExtra("category").equalsIgnoreCase("Humidifer")){
                        startActivity(new Intent(BuildingSelection.this, HumidifiersActivity.class));
                    }else if(getIntent().getStringExtra("category").equalsIgnoreCase("LightTimers")){
                        startActivity(new Intent(BuildingSelection.this, LightTimer.class));
                    }else if(getIntent().getStringExtra("category").equalsIgnoreCase("Thermostats")){
                        startActivity(new Intent(BuildingSelection.this, ThermostatsActivitty.class));
                    }
                }

            }
        });
    }

    private class ListViewAapter extends BaseAdapter {

        Helper helper;

        @Override
        public int getCount() {
            return buildingNames.length;
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
                LayoutInflater layoutInflater = (LayoutInflater) BuildingSelection.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_view_helper, parent, false);
                helper = new Helper();
                helper.textView = (TextView) convertView.findViewById(R.id.building_name);
                Typeface typeFace = Typeface.createFromAsset(getAssets(), "learningcurve.otf");
                helper.textView.setTypeface(typeFace);
                convertView.setTag(helper);
            } else {
                helper = (Helper) convertView.getTag();
            }
            helper.textView.setText(buildingNames[position]);
            return convertView;
        }

        private class Helper {
            TextView textView;
        }
    }
}
