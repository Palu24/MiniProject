package com.android.palak.hackathonapplication;

import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnSubmit;

    String source,destination;
    double slongitude, dlongitude, slatitude,dlatitude,price;
    StringBuffer stringBuffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    void initViews(){

        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        stringBuffer = new StringBuffer();
        WeatherFetch weatherFetch = new WeatherFetch();
        weatherFetch.execute();

    }

    class WeatherFetch extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {

            try {

                //Create the connection

                URL url = new URL("https://samples.openweathermap.org/data/2.5/weather?lat=30.9141552&lon=75.8106216&appid=b6907d289e10d714a6e88b30761fae22");

                //Send Request to the Server

                URLConnection urlConnection = url.openConnection();

                //Read Response from the Server

                InputStream inputStream = urlConnection.getInputStream();

                //Read the data from server line by line

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line + "\n");
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Toast.makeText(MainActivity.this, stringBuffer.toString(), Toast.LENGTH_LONG).show();

            //Only Rain Condition Check Left

        }
    }



    @Override
    public void onClick(View view){
        if(view.getId() == R.id.btnSubmit){

            slatitude = 30.9141552;
            slongitude =75.8106216;

            dlatitude = 30.9022866;
            dlongitude =75.8201693;


            Location me   = new Location("");
            Location dest = new Location("");

            me.setLatitude(slatitude);
            me.setLongitude(slongitude);

            dest.setLatitude(dlatitude);
            dest.setLongitude(dlongitude);

            //In km
            float dist = me.distanceTo(dest)/1000;

            Toast.makeText(this, "Distance is "+dist,Toast.LENGTH_LONG).show();

            //           base rate
            //-Rates for estimated time and distance of the route
            //-The current demand for rides in the area
                        //1 mile= 1.609 kilometers.

                        //For Uber Go
            float baseFare = 25;
            float costKM = 7;
            float costMin = 1;
            double time = 1.0728966667;
            //float minFare = 50;

            //uber go rides with km/h 64.373800002
            //1.0728966667
            price = baseFare + (costMin*time)+(costKM*dist);
            Toast.makeText(this, "Price is "+(int)price,Toast.LENGTH_LONG).show();








        }

    }






}
