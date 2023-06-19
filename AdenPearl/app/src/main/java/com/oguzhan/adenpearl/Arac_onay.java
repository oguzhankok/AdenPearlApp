package com.oguzhan.adenpearl;

import static com.oguzhan.adenpearl.AnaSayfa.name;
import static com.oguzhan.adenpearl.AnaSayfa.phoneNumber;
import static com.oguzhan.adenpearl.YolculukBilgileri.kisiSayisi;
import static com.oguzhan.adenpearl.YolculukBilgileri.valizSayisi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsStep;
import com.google.maps.model.TravelMode;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Arac_onay extends AppCompatActivity implements OnMapReadyCallback{
    MapView mapView;
    GoogleMap googlemap;
    TextView fiyat, fiyatb, tarih, tarihb, saat,saatb,lokasyon,lokasyonb,hedef,hedefb;
    private String hedefSaat,secilenArac;
    public String fiyatf,tarihf,lokasyonf,hedeff,saatf,telf,mailf,kisif,valizf;
    private double lat1,long1,lat2,long2;
    LatLng location1, location2;
    FirebaseFirestore firestore;
    public String dokumanId;
    FirebaseAuth auth;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arac_onay);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        dokumanId = user.getEmail();
        mapView = findViewById(R.id.mapView);
        mapView.getMapAsync( this);
        mapView.onCreate(savedInstanceState);
        lat1 = YolculukHesaplama.latitude1;
        lat2 = YolculukHesaplama.latitude2;
        long1 = YolculukHesaplama.longtitude1;
        long2 = YolculukHesaplama.longtitude2;
        hedefSaat=YolculukBilgileri.hedefSaat;
        secilenArac = YolculukBilgileri.secilenArac;


        fiyat = findViewById(R.id.fiyatBelirtec);
        tarih = findViewById(R.id.tarihBelirtec);
        saat = findViewById(R.id.saatBelirtec);
        fiyatb = findViewById(R.id.fiyatTxt);
        saatb = findViewById(R.id.saatTxt);
        tarihb = findViewById(R.id.tarihTxt);
        lokasyon = findViewById(R.id.lokasyonBelirtec);
        lokasyonb = findViewById(R.id.lokasyonTxt);
        hedef = findViewById(R.id.hedefBelirtec);
        hedefb = findViewById(R.id.hedefTxt);
        saatb.setText(hedefSaat);
        lokasyonb.setText(YolculukBilgileri.lokasyon);
        hedefb.setText(YolculukBilgileri.hedef);
        tarihb.setText(YolculukBilgileri.editTextDate2.getText());
        if(secilenArac.equals("VİP")){
            fiyatb.setText(YolculukBilgileri.fiyat4_1.getText());
        }
        else if(secilenArac.equals("maisto")){
            fiyatb.setText(YolculukBilgileri.fiyat1.getText());
        }
        else if(secilenArac.equals("sprinter")){
            fiyatb.setText(YolculukBilgileri.fiyat1.getText());
        }
        else if(secilenArac.equals("vito")){
            fiyatb.setText(YolculukBilgileri.fiyat1.getText());
        }


    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googlemap=map;
        location1 = new LatLng(lat1, long1);
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.title("Konum1");
        markerOptions1.position(location1);
        googlemap.addMarker(markerOptions1);



        location2 = new LatLng(lat2, long2);
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.title("Konum2");
        markerOptions2.position(location2);
        googlemap.addMarker(markerOptions2);


        /*CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(location1,15);
        googlemap.animateCamera(cameraUpdate);*/


        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(location1);
        builder.include(location2);
        LatLngBounds bounds = builder.build();
        int padding = 100; // İsteğe bağlı iç boşluk miktarı
        googlemap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
        drawRoute();
    }



    private void drawRoute() {
        // GeoApiContext oluşturun ve Google Directions API anahtarınızı ayarlayın
        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey("AIzaSyBSOF9JRVhaICUP938D3PI13ChmA_S2LFY")
                .build();

        // Directions API'ye istek yaparak yol bilgisini alın
        DirectionsApiRequest request = DirectionsApi.newRequest(geoApiContext);
        request.origin(new com.google.maps.model.LatLng(location1.latitude, location1.longitude));
        request.destination(new com.google.maps.model.LatLng(location2.latitude, location2.longitude));
        request.mode(TravelMode.DRIVING);

        try {
            DirectionsResult result = request.await();

            // Directions API'den gelen cevaptaki yol bilgisini alın
            if (result != null && result.routes != null && result.routes.length > 0) {
                DirectionsRoute route = result.routes[0];
                if (route != null && route.legs != null && route.legs.length > 0) {
                    DirectionsLeg leg = route.legs[0];
                    if (leg != null && leg.steps != null && leg.steps.length > 0) {
                        List<LatLng> points = new ArrayList<>();

                        for (DirectionsStep step : leg.steps) {
                            com.google.maps.model.LatLng stepStart = step.startLocation;
                            com.google.maps.model.LatLng stepEnd = step.endLocation;

                            points.add(new LatLng(stepStart.lat, stepStart.lng));


                            List<com.google.maps.model.LatLng> polyline = step.polyline.decodePath();
                            for (com.google.maps.model.LatLng polylinePoint : polyline) {
                                points.add(new LatLng(polylinePoint.lat, polylinePoint.lng));
                            }



                        }

                        // PolylineOptions nesnesi oluşturuldu ve özellikleri ayarlandı
                        PolylineOptions options = new PolylineOptions();
                        options.color(Color.RED);
                        options.width(10);

                        // location1 ve location2 konumlarını polyline'a ekle
                        options.addAll(points);

                        // Polyline'ı haritaya ekleyin
                        Polyline polyline = googlemap.addPolyline(options);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void arac_onaylandi(View view){
        firestore = FirebaseFirestore.getInstance();
        fiyatf= fiyatb.getText().toString();
        tarihf = tarihb.getText().toString();
        hedeff= hedefb.getText().toString();
        lokasyonf = lokasyonb.getText().toString();
        saatf= saatb.getText().toString();
        mailf = user.getEmail();
        telf = phoneNumber;
        kisif = kisiSayisi;
        valizf = valizSayisi;

        HashMap<String, Object> roadData = new HashMap<>();
        roadData.put("lokasyon",lokasyonf);
        roadData.put("hedef",hedeff);
        roadData.put("fiyat",fiyatf);
        roadData.put("tarih",tarihf);
        roadData.put("saat",saatf);
        roadData.put("e-mail",mailf);
        roadData.put("tel",telf);
        roadData.put("araç türü",secilenArac);
        roadData.put("kişi sayısı",kisif);
        roadData.put("valiz sayısı",valizf);

        firestore.collection("araç_çağrıları").document(dokumanId)
                .set(roadData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Arac_onay.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();

                    }
                });

        Toast.makeText(this,"Araç çağrınız alınmıştır, Şoförümüz sizinle iletişime geçecektir.",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Arac_onay.this,AnaSayfa.class);
        startActivity(intent);
        finish();
    }

    public void arac_iptal_edildi(View view){

        Intent intent = new Intent(Arac_onay.this,YolculukBilgileri.class);
        startActivity(intent);
        finish();

    }






    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}