package com.example.gouvernorats_meteo;

import androidx.appcompat.app.AppCompatActivity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.androdocs.httprequest.HttpRequest;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    String CITY;
    String API = "8118ed6ee68db2debfaaa5a44c832918";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fullscreen -> hide status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //check the internet connectivity
        isConnected();
    }

    //on card clicked -> call function to show the weather's info by setting the city string to its new value
    public void onButtonClicked(View view) {

        switch (view.getId()) {
            case R.id.ariana:
                CITY = "ariana";
                new weatherTask().execute();
                break;
            case R.id.beja:
                CITY = "beja";
                new weatherTask().execute();
                break;
            case R.id.benarous:
                CITY = "ben arous";
                new weatherTask().execute();
                break;
            case R.id.bizerte:
                CITY = "bizerte";
                new weatherTask().execute();
                break;
            case R.id.elkef:
                CITY = "el kef";
                new weatherTask().execute();
                break;
            case R.id.gabes:
                CITY = "gabes";
                new weatherTask().execute();
                break;
            case R.id.gafsa:
                CITY = "gafsa";
                new weatherTask().execute();
                break;
            case R.id.jendouba:
                CITY = "jendouba";
                new weatherTask().execute();
                break;
            case R.id.kairouan:
                CITY = "kairouan";
                new weatherTask().execute();
                break;
            case R.id.kasserine:
                CITY = "kasserine";
                new weatherTask().execute();
                break;
            case R.id.kebili:
                CITY = "kebili";
                new weatherTask().execute();
                break;
            case R.id.mahdia:
                CITY = "mahdia";
                new weatherTask().execute();
                break;
            case R.id.manouba:
                CITY = "manouba";
                new weatherTask().execute();
                break;
            case R.id.medenine:
                CITY = "medenine";
                new weatherTask().execute();
                break;
            case R.id.monastir:
                CITY = "monastir";
                new weatherTask().execute();
                break;
            case R.id.nabeul:
                CITY = "nabeul";
                new weatherTask().execute();
                break;
            case R.id.sfax:
                CITY = "sfax";
                new weatherTask().execute();
                break;
            case R.id.sidibouzid:
                CITY = "sidi bouzid";
                new weatherTask().execute();
                break;
            case R.id.siliana:
                CITY = "siliana";
                new weatherTask().execute();
                break;
            case R.id.sousse:
                CITY = "sousse";
                new weatherTask().execute();
                break;
            case R.id.tataouine:
                CITY = "tataouine";
                new weatherTask().execute();
                break;
            case R.id.tozeur:
                CITY = "tozeur";
                new weatherTask().execute();
                break;
            case R.id.tunis:
                CITY = "tunis";
                new weatherTask().execute();
                break;
            case R.id.zaghouan:
                CITY = "zaghouan";
                new weatherTask().execute();
                break;
            default:
                showToast("weather");
                break;
        }}

    //check if the device is connected to the internet (we need it to retrieve the weather info) else show toast
    public void isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(this.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }

        // Check for Internet Connection
        if (connected) {
            Log.d("OKAY","Internet Connected");
            //Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("NO","NO Internet Connected");
            showToast("No Internet Connection");
        }

    }

    //Toast
    public void showToast(String msg){
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    //getting the weather info
    class weatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override //exeute the get request from "openweathermap" website by giving it our API & city name
        protected String doInBackground(String... args) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                // retrieve the weather information wanted and put them into strings with the format we want
                JSONObject jsonObj = new JSONObject(result);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                Long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = new SimpleDateFormat("dd/MM hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temp = "Current: "+ main.getString("temp") + "°C";
                String tempMin = "Min: "+main.getString("temp_min") + "°C";
                String tempMax = "Max: "+main.getString("temp_max") + "°C";
                String pressure = main.getString("pressure")+" mb";
                String humidity = main.getString("humidity")+"%";
                String sunrise = "Sunrise: "+ new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sys.getLong("sunrise") * 1000));
                String sunset = "Sunset: "+ new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sys.getLong("sunset") * 1000));
                String windSpeed = wind.getString("speed")+"km/h";
                String weatherDescription = "- "+weather.getString("description")+" -";

                //create a bottom sheet dialog instance and populate it with the taped city weather info
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this,R.style.BottomSheetDialogTheme);
                View bottomSheetView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_bottomsheet, (LinearLayout) findViewById(R.id.mybottomsheet));

                // populate the sheet
                TextView lastupdated = bottomSheetView.findViewById(R.id.last_updated);
                lastupdated.setText(lastupdated.getText()+updatedAtText);

                TextView description = bottomSheetView.findViewById(R.id.description);
                description.setText(weatherDescription);

                TextView cityname = bottomSheetView.findViewById(R.id.city);
                cityname.setText(CITY.toUpperCase());

                TextView temper = bottomSheetView.findViewById(R.id.temperature);
                temper.setText(temp);

                TextView tempermin = bottomSheetView.findViewById(R.id.temperature_min);
                tempermin.setText(tempMin);

                TextView tempermax = bottomSheetView.findViewById(R.id.temperature_max);
                tempermax.setText(tempMax);

                TextView windspeed = bottomSheetView.findViewById(R.id.windspeed);
                windspeed.setText(windSpeed);

                TextView Humidity = bottomSheetView.findViewById(R.id.humidity);
                Humidity.setText(humidity);

                TextView Pressure = bottomSheetView.findViewById(R.id.pressure);
                Pressure.setText(pressure);

                TextView sunRise = bottomSheetView.findViewById(R.id.sunrise);
                sunRise.setText(sunrise);

                TextView sunSet = bottomSheetView.findViewById(R.id.sunset);
                sunSet.setText(sunset);

                //set the sheet's view and show it
                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            } catch (Exception e) {
                showToast("Error: " + e.toString());
            }
        }
    }
}
