package com.oguzhan.adenpearl;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class YolculukBilgileri extends AppCompatActivity {

    private EditText editTextDate;
    private SimpleDateFormat dateFormatter;

    MapView mapView;
    double hesapVip;
    Button buton;
    public static String hedefSaat,lokasyon,hedef,secilenArac, hata_yazisi,valizSayisi,kisiSayisi;
    EditText etSource, etDestination;
    public static EditText editTextDate2;
    TextView textView,dogruluk_text;
    String sType;

    Spinner spinnerYolcu, spinnerValiz, spinnerSaat;
    ArrayList<Integer> YolcuSayisiList, ValizSayisiList, saattxt;
    ArrayList<String> SaatList;
    ArrayAdapter<Integer> adapterYolcu, adapterValiz;
    ArrayAdapter<String> adapterSaat;
    TextView txtYolcu,txtValiz,txtSaat, textViewHG;
    public static TextView fiyat1,fiyat4_1,fiyat3_1,fiyat2_1;
    double lat1 = 0, long1 = 0, lat2 = 0, long2 = 0;
    int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yolculuk_bilgileri);
        etSource = findViewById(R.id.rez_isim);
        etDestination = findViewById(R.id.etDestination);
        textView = findViewById(R.id.textView);
        spinnerVerileri();
        Places.initialize(getApplicationContext(),"AIzaSyBSOF9JRVhaICUP938D3PI13ChmA_S2LFY");
        etSource.setFocusable(false);


        etSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sType ="source";
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS
                        , Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY,fields
                ).build(YolculukBilgileri.this);
                startActivityForResult(intent,100);
            }
        });
        etDestination.setFocusable(false);
        etDestination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sType = "destination";
                List<Place.Field> fields = Arrays.asList(Place.Field.ADDRESS
                        , Place.Field.LAT_LNG);
                Intent intent = new Autocomplete.IntentBuilder(
                        AutocompleteActivityMode.OVERLAY, fields
                ).build(YolculukBilgileri.this);
                startActivityForResult(intent, 100);
            }
        });
        textView.setText("-----");


        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

        editTextDate2 = findViewById(R.id.editText_Date);
        editTextDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });



    }
    private void showDatePicker() {
        final Calendar today = Calendar.getInstance();
        final Calendar newCalendar = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, monthOfYear, dayOfMonth);

                        if (selectedDate.after(today) || selectedDate.equals(today)) {
                            String formattedDate = dateFormatter.format(selectedDate.getTime());
                            editTextDate2.setText(formattedDate);
                        }
                    }
                },
                newCalendar.get(Calendar.YEAR),
                newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(today.getTimeInMillis());
        datePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode==RESULT_OK){
            Place place = Autocomplete.getPlaceFromIntent(data);
            if(sType.equals("source")){
                flag++;
                etSource.setText(place.getAddress());
                lokasyon = etSource.getText().toString();
                String sSource = String.valueOf(place.getLatLng());
                sSource = sSource.replaceAll("lat/lng: ","");
                sSource = sSource.replace("(","");
                sSource = sSource.replace(")","");
                String[] split = sSource.split(",");
                lat1 = Double.parseDouble(split[0]);
                long1 = Double.parseDouble(split[1]);
            }else{
                flag++;
                etDestination.setText(place.getAddress());
                hedef = etDestination.getText().toString();
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

    public void MenuDon(View view){
        Intent intent = new Intent(YolculukBilgileri.this,AnaSayfa.class);
        startActivity(intent);
        finish();
    }

    private void spinnerVerileri(){
        spinnerYolcu = findViewById(R.id.yolcuSayisi);
        spinnerValiz = findViewById(R.id.valizSayisi);
        spinnerSaat = findViewById(R.id.saat);
        txtSaat = findViewById(R.id.saattxt);
        txtYolcu = findViewById(R.id.YolcuSayisitxt);
        txtValiz = findViewById(R.id.ValizSayisitxt);
        YolcuSayisiList = new ArrayList<>();
        for(int i = 0; i<=16;i++){
            YolcuSayisiList.add(i);
        }
        ValizSayisiList = new ArrayList<>();
        for(int i = 0;i<=16;i++){
            ValizSayisiList.add(i);
        }
        SaatList = new ArrayList<>();
        for (int i = 0;i<=24;i++){
            for(int j = 0;j<=55;j=j+5){
                if (i < 10 && j>=10) {
                    SaatList.add("0"+i+":"+j);
                }
                else if(i<10 && j<10){
                    SaatList.add("0"+i+":"+"0"+j);
                }
                else if(i>=10 && j<10){
                    SaatList.add(i+":"+"0"+j);
                }

                else {
                    SaatList.add(i + ":" + j);
                }
            }
        }
        adapterYolcu = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,YolcuSayisiList);
        adapterYolcu.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYolcu.setAdapter(adapterYolcu);
        spinnerYolcu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtYolcu.setText(adapterView.getItemAtPosition(i).toString());
                kisiSayisi = txtYolcu.getText().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                txtYolcu.setText(adapterView.getItemAtPosition(0).toString());
            }
        });
        adapterSaat = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,SaatList);
        adapterSaat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSaat.setAdapter(adapterSaat);
        spinnerSaat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtSaat.setText(adapterView.getItemAtPosition(i).toString());
                hedefSaat = txtSaat.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                txtSaat.setText(adapterView.getItemAtPosition(0).toString());
            }
        });
        adapterValiz = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item,ValizSayisiList);
        adapterValiz.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerValiz.setAdapter(adapterValiz);
        spinnerValiz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                txtValiz.setText(adapterView.getItemAtPosition(i).toString());
                valizSayisi = txtValiz.getText().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                txtValiz.setText(adapterView.getItemAtPosition(0).toString());
            }
        });
    }

    public void arabaSecc (View view){

        hata_yazisi="Lütfen tüm alanları doldurun...";
        int select_index=spinnerYolcu.getSelectedItemPosition();
        int select_index2=spinnerSaat.getSelectedItemPosition();
        int select_index3=spinnerValiz.getSelectedItemPosition();
        String text = editTextDate2.getText().toString();
        String text2 = etDestination.getText().toString();
        String text3 = etSource.getText().toString();
        if(select_index==0 || text.equals(null) || text2.equals(null) || text3.equals(null)){
            Toast.makeText(YolculukBilgileri.this,hata_yazisi,Toast.LENGTH_LONG);
            arabaSecc(view);
        }



        if (Integer.parseInt(String.valueOf(txtYolcu.getText())) <= 2 && Integer.parseInt(String.valueOf(txtValiz.getText())) <= 2) {
            setContentView(R.layout.arac_secme);
            fiyat1 = findViewById(R.id.fiyat1);
            fiyat2_1 = findViewById(R.id.fiyat2_1);
            fiyat4_1 = findViewById(R.id.fiyat4_1);
            fiyat3_1 = findViewById(R.id.fiyat3_1);
            fiyat1.setText(String.valueOf(Double.parseDouble(String.valueOf(txtValiz.getText())) * (90/100) * Double.parseDouble(String.valueOf(txtYolcu.getText())) * 5 + Double.parseDouble(String.valueOf(textView.getText()).substring(0, 3))) + "$");
            fiyat3_1.setText(String.valueOf(Double.parseDouble(String.valueOf(txtValiz.getText()))* (90/100) * Double.parseDouble(String.valueOf(txtYolcu.getText())) * 5 + Double.parseDouble(String.valueOf(textView.getText()).substring(0, 3))) + "$");
            hesapVip = (((Integer.parseInt(String.valueOf(txtValiz.getText())) * Integer.parseInt(String.valueOf(txtYolcu.getText())) * 5 + Double.parseDouble(String.valueOf(textView.getText()).substring(0, 3))) / 3) + (Integer.parseInt(String.valueOf(txtValiz.getText())) * Integer.parseInt(String.valueOf(txtYolcu.getText())) * 5 + Double.parseDouble(String.valueOf(textView.getText()).substring(0, 3))));

            fiyat2_1.setText(String.valueOf(Double.parseDouble(String.valueOf(txtValiz.getText())) *(90/100) * Double.parseDouble(String.valueOf(txtYolcu.getText())) * 5 + Double.parseDouble(String.valueOf(textView.getText()).substring(0, 3))) + "$");
            if(String.valueOf(hesapVip).length()>5){
                fiyat4_1.setText(String.valueOf(hesapVip).substring(0,5)+"$");
            }
            else{
                fiyat4_1.setText(hesapVip + "$");
            }

        }
        if (Integer.parseInt(String.valueOf(txtYolcu.getText())) > 2 || Integer.parseInt(String.valueOf(txtValiz.getText())) > 2 && Integer.parseInt(String.valueOf(txtYolcu.getText())) <= 4 && Integer.parseInt(String.valueOf(txtValiz.getText())) <= 4) {
            setContentView(R.layout.arac_secme_ikiden_fazla);
            fiyat1 = findViewById(R.id.fiyat1);
            fiyat2_1 = findViewById(R.id.fiyat2_1);
            fiyat4_1 = findViewById(R.id.fiyat4_1);
            fiyat1.setText(String.valueOf(Double.parseDouble(String.valueOf(txtValiz.getText())) *(90/100) * Double.parseDouble(String.valueOf(txtYolcu.getText())) * 5 + Double.parseDouble(String.valueOf(textView.getText()).substring(0, 3))) + "$");
            fiyat2_1.setText(String.valueOf(Double.parseDouble(String.valueOf(txtValiz.getText())) *(90/100) * Double.parseDouble(String.valueOf(txtYolcu.getText())) * 5 + Double.parseDouble(String.valueOf(textView.getText()).substring(0, 3))) + "$");
            hesapVip = (((Double.parseDouble(String.valueOf(txtValiz.getText())) * Double.parseDouble(String.valueOf(txtYolcu.getText())) * 5 + Double.parseDouble(String.valueOf(textView.getText()).substring(0, 3))) / 3) + (Integer.parseInt(String.valueOf(txtValiz.getText())) * Integer.parseInt(String.valueOf(txtYolcu.getText())) * 5 + Double.parseDouble(String.valueOf(textView.getText()).substring(0, 3))));

            if(String.valueOf(hesapVip).length()>5){
                fiyat4_1.setText(String.valueOf(hesapVip).substring(0,5)+"$");
            }
            else{
                fiyat4_1.setText(hesapVip + "$");
            }

        }
        if (Integer.parseInt(String.valueOf(txtYolcu.getText())) > 4 || Integer.parseInt(String.valueOf(txtValiz.getText())) > 4 && Integer.parseInt(String.valueOf(txtYolcu.getText())) <= 6 && Integer.parseInt(String.valueOf(txtValiz.getText())) <= 6) {
            setContentView(R.layout.arac_secme_dortten_fazla);
            fiyat1 = findViewById(R.id.fiyat1);
            fiyat2_1 = findViewById(R.id.fiyat2_1);
            fiyat1.setText(String.valueOf(Double.parseDouble(String.valueOf(txtValiz.getText())) *(90/100) * Double.parseDouble(String.valueOf(txtYolcu.getText())) * 5 + Double.parseDouble(String.valueOf(textView.getText()).substring(0, 3))) + "$");
            fiyat2_1.setText(String.valueOf(Double.parseDouble(String.valueOf(txtValiz.getText())) * (90/100) *Double.parseDouble(String.valueOf(txtYolcu.getText())) * 5 + Double.parseDouble(String.valueOf(textView.getText()).substring(0, 3))) + "$");

        }
        if (Integer.parseInt(String.valueOf(txtYolcu.getText())) > 6 || Integer.parseInt(String.valueOf(txtValiz.getText())) > 6) {
            setContentView(R.layout.arac_secme_altidan_fazla);
            fiyat2_1 = findViewById(R.id.fiyat2_1);
            fiyat2_1.setText(String.valueOf(Double.parseDouble(String.valueOf(txtValiz.getText())) *(90/100) * Double.parseDouble(String.valueOf(txtYolcu.getText())) * 5 + Double.parseDouble(String.valueOf(textView.getText()).substring(0, 3))) + "$");
        }

    }
    public void aracSecildi_vip (View view){
        secilenArac="VİP";
        Intent intent = new Intent(YolculukBilgileri.this, Arac_onay.class);
        startActivity(intent);

    }
    public void geriDon(View view){
        Intent intent = new Intent(YolculukBilgileri.this,YolculukBilgileri.class);
        startActivity(intent);
        finish();
    }
    public void aracSecildi_vito(View view){
        secilenArac="vito";
        Intent intent = new Intent(YolculukBilgileri.this, Arac_onay.class);
        startActivity(intent);
    }
    public void aracSecildi_sprinter(View view){
        secilenArac="sprinter";
        Intent intent = new Intent(YolculukBilgileri.this, Arac_onay.class);
        startActivity(intent);
    }
    public void aracSecildi_maisto(View view){
        secilenArac="maisto";
        Intent intent = new Intent(YolculukBilgileri.this, Arac_onay.class);
        startActivity(intent);
    }


}