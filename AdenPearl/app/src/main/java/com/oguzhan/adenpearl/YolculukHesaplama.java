package com.oguzhan.adenpearl;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.model.DirectionsResult;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.Distance;
import com.google.maps.model.Duration;
import com.google.maps.model.TravelMode;

import java.util.Arrays;
import java.util.List;

public class YolculukHesaplama extends AsyncTask<Void, Void, Void> {


    private final String TAG = YolculukHesaplama.class.getSimpleName();
    /*private final LatLng konum1;
    private final LatLng konum2;*/
    public static double latitude1,longtitude1,latitude2,longtitude2;
    public String mesafeMetni;
    private String sureMetni;

    public YolculukHesaplama(double latitude1, double longtitude1,double latitude2,double longtitude2) {
        /*this.konum1 = konum1;
        this.konum2 = konum2;*/
        this.latitude1 = latitude1;
        this.latitude2 = latitude2;
        this.longtitude1 = longtitude1;
        this.longtitude2 = longtitude2;

    }
    @Override
    protected Void doInBackground(Void... voids) {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyBSOF9JRVhaICUP938D3PI13ChmA_S2LFY")
                .build();
        try {
            DirectionsApiRequest request = DirectionsApi.newRequest(context)
                    .mode(TravelMode.DRIVING)
                    .origin(new com.google.maps.model.LatLng(latitude1, longtitude1))
                    .destination(new com.google.maps.model.LatLng(latitude2, longtitude2));

            DirectionsResult result = request.await();

            if (result.routes.length > 0) {
                DirectionsRoute route = result.routes[0];
                if (route.legs.length > 0) {
                    DirectionsLeg leg = route.legs[0];
                    Distance distance = leg.distance;
                    Duration duration = leg.duration;
                    mesafeMetni = distance.humanReadable;
                    sureMetni = duration.humanReadable;
                }
            }

        } catch (Exception e) {
            Log.e(TAG, "Hata: " + e.getMessage());
        }

        return null;
    }


}

