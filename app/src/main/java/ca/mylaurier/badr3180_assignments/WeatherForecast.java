package ca.mylaurier.badr3180_assignments;

import android.app.Activity;
import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;


import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;



import javax.net.ssl.HttpsURLConnection;
import  androidx.fragment.app.FragmentManager;

public class WeatherForecast extends AppCompatActivity {
    private final String ACTIVITY_NAME = "WeatherForecastActivity";
    public String strURL="https://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=710175576fd03c6ce7a6a1c40a934170&mode=xml&units=metric";
    public Context fragContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.commit();
       // }
        setContentView(R.layout.activity_weather_forecast);
        Spinner citySpinner = findViewById(R.id.spinner);
        String[] cityList= {"ottawa","toronto", "montreal", "waterloo", "vancouver"};
        citySpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,cityList));


        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            boolean first_trigger = true;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                switch (cityList[position]){
                    case "toronto":
                        strURL = "https://api.openweathermap.org/data/2.5/weather?q=toronto,ca&APPID=710175576fd03c6ce7a6a1c40a934170&mode=xml&units=metric";
                        Log.i(ACTIVITY_NAME,strURL);
                        break;
                    case "montreal":
                        strURL = "https://api.openweathermap.org/data/2.5/weather?q=montreal,ca&APPID=710175576fd03c6ce7a6a1c40a934170&mode=xml&units=metric";
                        break;
                    case "waterloo":
                        strURL = "https://api.openweathermap.org/data/2.5/weather?q=waterloo,ca&APPID=710175576fd03c6ce7a6a1c40a934170&mode=xml&units=metric";
                        break;
                    case "vancouver":
                        strURL = "https://api.openweathermap.org/data/2.5/weather?q=vancouver,ca&APPID=710175576fd03c6ce7a6a1c40a934170&mode=xml&units=metric";
                        break;
                    default:
                        strURL = "https://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=710175576fd03c6ce7a6a1c40a934170&mode=xml&units=metric";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                strURL = "https://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=710175576fd03c6ce7a6a1c40a934170&mode=xml&units=metric";;
            }
        });
        WeatherFragment weatherFragment = new WeatherFragment();
        Button showBTN = findViewById(R.id.showWeatherBTN);
        showBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(ACTIVITY_NAME, "User clicked weatherForecast");
                // The launcher with the Intent you want to start
                getSupportFragmentManager().beginTransaction().replace(R.id.weatherContainer, weatherFragment.newInstance(strURL,""),"fragment").addToBackStack(null).commit();
            }
        });
    }


}