package com.oguzhan.adenpearl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class Turlar extends AppCompatActivity {


    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore db;
    public static int tur;
    TextView bolgetxt, kategoritxt, yemektxt, saattxt, aractxt, tarihtxt,fiyattxt;
    TextView bolgetxt2, kategoritxt2, yemektxt2, saattxt2, aractxt2, tarihtxt2,fiyattxt2;
    TextView bolgetxt3, kategoritxt3, yemektxt3, saattxt3, aractxt3, tarihtxt3,fiyattxt3;
    TextView ozellik1,ozellik2,ozellik3,ozellik4,fiyattxt_inceleme,aciklamatxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turlar);
        db = FirebaseFirestore.getInstance();
        bolgetxt = findViewById(R.id.bolge_detay);
        kategoritxt = findViewById(R.id.kategori_detay);
        yemektxt = findViewById(R.id.yemek_detay);
        saattxt = findViewById(R.id.zaman_detay);
        fiyattxt = findViewById(R.id.tur_fiyat);
        aractxt = findViewById(R.id.oto_detay);
        tarihtxt = findViewById(R.id.tarih_detay);

        bolgetxt2 = findViewById(R.id.bolge_detay2);
        kategoritxt2 = findViewById(R.id.kategori_detay2);
        yemektxt2 = findViewById(R.id.yemek_detay2);
        saattxt2 = findViewById(R.id.zaman_detay2);
        fiyattxt2 = findViewById(R.id.tur_fiyat2);
        aractxt2 = findViewById(R.id.oto_detay2);
        tarihtxt2 = findViewById(R.id.tarih_detay2);

        bolgetxt3 = findViewById(R.id.bolge_detay3);
        kategoritxt3 = findViewById(R.id.kategori_detay3);
        yemektxt3 = findViewById(R.id.yemek_detay3);
        saattxt3 = findViewById(R.id.zaman_detay3);
        fiyattxt3 = findViewById(R.id.tur_fiyat3);
        aractxt3 = findViewById(R.id.oto_detay3);
        tarihtxt3 = findViewById(R.id.tarih_detay3);



        DocumentReference userRef = db.collection("Turlar").document("adrasan");
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Firestore'dan telefon numarasını alın

                    bolgetxt.setText(document.getString("bolge"));
                    kategoritxt.setText(document.getString("kategori"));
                    yemektxt.setText(document.getString("yemek"));
                    saattxt.setText(document.getString("saat"));
                    fiyattxt.setText(document.getString("fiyat")+"TL");
                    aractxt.setText(document.getString("araç"));
                    tarihtxt.setText(document.getString("tarih"));

                    // Telefon numarasını kullanarak TextView'ı güncelleyin

                }
                else{

                }
            }
        });
        DocumentReference userRef2 = db.collection("Turlar").document("Batı Karadeniz");
        userRef2.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Firestore'dan telefon numarasını alın

                    bolgetxt2.setText(document.getString("bolge"));
                    kategoritxt2.setText(document.getString("kategori"));
                    yemektxt2.setText(document.getString("yemek"));
                    saattxt2.setText(document.getString("saat"));
                    fiyattxt2.setText(document.getString("fiyat")+"TL");
                    aractxt2.setText(document.getString("araç"));
                    tarihtxt2.setText(document.getString("tarih"));

                    // Telefon numarasını kullanarak TextView'ı güncelleyin

                }
                else{

                }
            }
        });
        DocumentReference userRef3 = db.collection("Turlar").document("Pamukkale");
        userRef3.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Firestore'dan telefon numarasını alın

                    bolgetxt3.setText(document.getString("bolge"));
                    kategoritxt3.setText(document.getString("kategori"));
                    yemektxt3.setText(document.getString("yemek"));
                    saattxt3.setText(document.getString("saat"));
                    fiyattxt3.setText(document.getString("fiyat")+"TL");
                    aractxt3.setText(document.getString("araç"));
                    tarihtxt3.setText(document.getString("tarih"));

                    // Telefon numarasını kullanarak TextView'ı güncelleyin

                }
                else{

                }
            }
        });



    }
    public void turBirIncele(View view){

        setContentView(R.layout.adrasan_detay);
        ozellik1 = findViewById(R.id.oz);
        ozellik2 = findViewById(R.id.oz1);
        ozellik3 = findViewById(R.id.oz2);
        ozellik4 = findViewById(R.id.oz3);
        fiyattxt_inceleme = findViewById(R.id.fiyat_kisi_basi);
        aciklamatxt = findViewById(R.id.aciklamaYazisi);



        DocumentReference userRef = db.collection("Turlar").document("adrasan");
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Firestore'dan telefon numarasını alın

                    ozellik1.setText(document.getString("ozellik1"));
                    ozellik2.setText(document.getString("ozellik2"));
                    ozellik3.setText(document.getString("ozellik3"));
                    ozellik4.setText(document.getString("ozellik4"));
                    fiyattxt_inceleme.setText(document.getString("fiyat")+"TL");
                    aciklamatxt.setText(document.getString("açıklama"));

                    // Telefon numarasını kullanarak TextView'ı güncelleyin

                }
                else{

                }
            }
        });



    }
    public void turIkiIncele(View view){

        setContentView(R.layout.bati_karadeniz_detay);
        ozellik1 = findViewById(R.id.oz);
        ozellik2 = findViewById(R.id.oz1);
        ozellik3 = findViewById(R.id.oz2);
        ozellik4 = findViewById(R.id.oz3);
        fiyattxt_inceleme = findViewById(R.id.fiyat_kisi_basi);
        aciklamatxt = findViewById(R.id.aciklamaYazisi);



        DocumentReference userRef = db.collection("Turlar").document("Batı Karadeniz");
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Firestore'dan telefon numarasını alın

                    ozellik1.setText(document.getString("ozellik1"));
                    ozellik2.setText(document.getString("ozellik2"));
                    ozellik3.setText(document.getString("ozellik3"));
                    ozellik4.setText(document.getString("ozellik4"));
                    fiyattxt_inceleme.setText(document.getString("fiyat")+"TL");
                    aciklamatxt.setText(document.getString("açıklama"));

                    // Telefon numarasını kullanarak TextView'ı güncelleyin

                }
                else{

                }
            }
        });


    }
    public void turUcIncele(View view){

        setContentView(R.layout.pamukkale_detay);
        ozellik1 = findViewById(R.id.oz);
        ozellik2 = findViewById(R.id.oz1);
        ozellik3 = findViewById(R.id.oz2);
        ozellik4 = findViewById(R.id.oz3);
        fiyattxt_inceleme = findViewById(R.id.fiyat_kisi_basi);
        aciklamatxt = findViewById(R.id.aciklamaYazisi);



        DocumentReference userRef = db.collection("Turlar").document("Pamukkale");
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Firestore'dan telefon numarasını alın

                    ozellik1.setText(document.getString("ozellik1"));
                    ozellik2.setText(document.getString("ozellik2"));
                    ozellik3.setText(document.getString("ozellik3"));
                    ozellik4.setText(document.getString("ozellik4"));
                    fiyattxt_inceleme.setText(document.getString("fiyat")+"TL");
                    aciklamatxt.setText(document.getString("açıklama"));

                    // Telefon numarasını kullanarak TextView'ı güncelleyin

                }
                else{

                }
            }
        });

    }
    public void rezIlktur(View view){

        tur = 1;
        Intent intent = new Intent(Turlar.this,TurRez.class);
        startActivity(intent);
        finish();


    }
    public void rezIkitur(View view){

        tur = 2;
        Intent intent = new Intent(Turlar.this,TurRez.class);
        startActivity(intent);
        finish();


    }

    public void rezUctur(View view){


        tur = 3;
        Intent intent = new Intent(Turlar.this,TurRez.class);
        startActivity(intent);
        finish();

    }
    public void geriDon(View view){
        Intent intent = new Intent(Turlar.this,AnaSayfa.class);
        startActivity(intent);
        finish();

    }

}