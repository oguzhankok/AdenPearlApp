package com.oguzhan.adenpearl;

import android.content.Intent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArabaCagir extends AppCompatActivity {



    String sType;
    EditText etSource, etDestination;
    TextView textView;

    public ArabaCagir(String sType,EditText etSource,EditText etDestination,TextView textView) {
        this.sType=sType;
        this.etSource=etSource;
        this.etDestination=etDestination;
        this.textView=textView;
        etSource = findViewById(R.id.rez_isim);
        etDestination = findViewById(R.id.etDestination);
        textView = findViewById(R.id.textView);

        spinnerVerileri();
        Places.initialize(getApplicationContext(),"AIzaSyB5-nEC4JVRO_N9I6o43q8mIWUAzTyheXw");
        etSource.setFocusable(false);
        etSource.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //sType ="source";
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS
                        , Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY,fields
                ).build(ArabaCagir.this);
                startActivityForResult(intent,100);
            }
        });
        etDestination.setFocusable(false);
        etDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //sType = "destination";
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS
                        , Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, fields
                ).build(ArabaCagir.this);
                startActivityForResult(intent, 100);
            }
        });
        textView.setText("-----");


    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        int flag = 0;
        double lat1 = 0, long1 = 0, lat2 = 0, long2 = 0;
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            if(sType.equals("source")){
                flag++;
                etSource.setText(place.getAddress());
                String sSource = String.valueOf(place.getLatLng());
                sSource = sSource.replaceAll("lat/lng: ","");
                sSource = sSource.replace("(","");
                sSource = sSource.replace(")","");
                String[] split = sSource.split(",");
                lat1 = Double.parseDouble(split[0]);
                long1 = Double.parseDouble(split[1]);
            }else if(sType.equals("destination")){
                flag++;
                etDestination.setText(place.getAddress());
                String sDestination = String.valueOf(place.getLatLng());
                sDestination = sDestination.replaceAll("lat/lng: ","");
                sDestination = sDestination.replace("(","");
                sDestination = sDestination.replace(")","");
                String[] split = sDestination.split(",");
                lat2 = Double.parseDouble(split[0]);
                long2 = Double.parseDouble(split[1]);
            }
            if(flag >=2){
                distance(lat1,long1,lat2,long2);
            }else if(requestCode== AutocompleteActivity.RESULT_ERROR){
                com.google.android.gms.common.api.Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(getApplicationContext(),status.getStatusMessage(),Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void distance(double lat1, double long1, double lat2, double long2) {
        YolculukHesaplama yolculukHesaplama = new YolculukHesaplama(lat1,long1,lat2,long2);
        yolculukHesaplama.doInBackground();
        textView.setText(yolculukHesaplama.mesafeMetni);
    }
    private void spinnerVerileri(){
        Spinner spinnerYolcu, spinnerValiz;
        ArrayAdapter<Integer> adapterYolcu, adapterValiz;
        TextView txtYolcu, txtValiz;
        ArrayList<Integer> YolcuSayisiList, ValizSayisiList;
        YolcuSayisiList = YolcuSayisiList = new ArrayList<>();
        ValizSayisiList = ValizSayisiList = new ArrayList<>();

        spinnerYolcu = findViewById(R.id.yolcuSayisi);
        spinnerValiz = findViewById(R.id.valizSayisi);
        txtYolcu = findViewById(R.id.YolcuSayisitxt);
        txtValiz = findViewById(R.id.ValizSayisitxt);

        for(int i = 0; i<=5;i++){
            YolcuSayisiList.add(i);
        }
        ValizSayisiList = new ArrayList<>();
        for(int i = 0;i<=10;i++){
            ValizSayisiList.add(i);
        }
        adapterYolcu = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,YolcuSayisiList);
        adapterYolcu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYolcu.setAdapter(adapterYolcu);
        spinnerYolcu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtYolcu.setText(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                txtYolcu.setText(adapterView.getItemAtPosition(0).toString());
            }
        });
        adapterValiz = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,ValizSayisiList);
        adapterValiz.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerValiz.setAdapter(adapterValiz);
        spinnerValiz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtValiz.setText(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                txtValiz.setText(adapterView.getItemAtPosition(0).toString());
            }
        });
    }

}