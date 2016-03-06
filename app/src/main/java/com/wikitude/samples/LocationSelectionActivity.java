package com.wikitude.samples;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.wikitude.nativesdksampleapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LocationSelectionActivity extends AppCompatActivity {
    @Bind(R.id.search_box)
    AutoCompleteTextView autoCompleteTextView;
    @Bind(R.id.next_button)
    Button nextButton;
    @Bind(R.id.welcome)
    TextView welcomeText;
    @Bind(R.id.name)
    TextView nameText;
    String[] countries = {"Bangalore", "Mysore", "Delhi", "Mumbai"};
    String positionString = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selection);
        ButterKnife.bind(this);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "learningcurve.otf");
        nameText.setTypeface(typeFace);
        welcomeText.setTypeface(typeFace);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                View im = LocationSelectionActivity.this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm.isActive())
                        imm.hideSoftInputFromWindow(im.getWindowToken(), 0);
                }
                LocationSelectionActivity.this.positionString = (String)parent.getItemAtPosition(position);
                nextButton.setVisibility(View.VISIBLE);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!positionString.isEmpty()) {
                    SharedPreferences sharedpreferences = getSharedPreferences("HoneyHack", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("location", positionString);
                    editor.commit();
                    startActivity(new Intent(LocationSelectionActivity.this,CategoryGridActivity.class));
                    finish();
                }
            }
        });
    }
}
