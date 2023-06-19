package com.oguzhan.adenpearl;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Anasayfa_forAdmin extends AppCompatActivity {
    private FirebaseAuth auth;
    private ListView messagesListView;
    private List<String> messageList;
    String mesag;
    private FirebaseFirestore db;
    TextView mesage;
    private FirebaseUser user;
    LinearLayout conteiner_layout,conteiner_layout2,conteiner_layout3,conteiner_layout4;
    int marginInPx;
    int textColor ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa_for_admin);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        textColor= Color.parseColor("#2550BE");
         // Boşluk için ayarlanacak piksel değeri
        //messagesListView = findViewById(R.id.messagesListView);
        marginInPx = 50;
        messageList = new ArrayList<>(); // Önceden tanımlanmış bir liste oluşturun





    }
    public void tur_rezervasyonlari(View view){

        setContentView(R.layout.tur_rezervasyonlari);
        conteiner_layout4 = findViewById(R.id.linearlayout4);

        db.collection("tur_rezervasyonları").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Her belge için işlemleri gerçekleştirin


                    TextView textView = new TextView(this);
                    textView.setText("Tur : " + document.getString("Tur")
                            + "\nİsim - Soyisim : " + document.getString("rezervasyon")
                            + "\nKişi Sayısı : " + document.getString("kişi")
                            + "\nTelefon: " + document.getString("Tel")+
                            "\nÜcret : " + document.getString("Ücret"));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, marginInPx, 0, 0);
                    textView.setLayoutParams(layoutParams);
                    textView.setTextColor(textColor);

                    conteiner_layout4.addView(textView);

                }

            } else {

            }
        });



    }
    public void yardim_cagrilari(View view){

        setContentView(R.layout.yardim_cagrilari);
        conteiner_layout = findViewById(R.id.linearlayout);

        db.collection("mesajlar").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Her belge için işlemleri gerçekleştirin


                    TextView textView = new TextView(this);
                    textView.setText("Konu: " + document.getString("konu")
                            + "\nİsim - Soyisim: " + document.getString("İsim - Soyisim")
                            + "\nTelefon Numarası: " + document.getString("phoneNumber")
                            + "\nMail: " + document.getString("mail")+
                            "\nMesaj: " + document.getString("Mesaj"));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, marginInPx, 0, 0);
                    textView.setLayoutParams(layoutParams);
                    textView.setTextColor(textColor);

                    conteiner_layout.addView(textView);

                }

            } else {

            }
        });

    }

    public void arac_cagrilari(View view){
        setContentView(R.layout.arac_cagrilari);
        conteiner_layout2 = findViewById(R.id.linearlayout2);
        db.collection("araç_çağrıları").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Her belge için işlemleri gerçekleştirin


                    TextView textView = new TextView(this);
                    textView.setText("Lokasyon : " + document.getString("lokasyon")
                            + "\nHedef : " + document.getString("hedef")
                            + "\nTelefon Numarası: " + document.getString("tel")
                            + "\nMail : " + document.getString("e-mail")+
                            "\nAraç " + document.getString("araç türü")+
                            "\nKişi :" + document.getString("kişi sayısı")
                            + "\nValiz : " + document.getString("valiz sayısı")
                                    + "\nTarih : " + document.getString("tarih")
                                    + "\nSaat : " + document.getString("saat")
                                    + "\nFiyat : " + document.getString("fiyat"));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, marginInPx, 0, 0);
                    textView.setLayoutParams(layoutParams);
                    textView.setTextColor(textColor);

                    conteiner_layout2.addView(textView);

                }

            } else {

            }
        });

    }

    public void geriDon(View view){
        setContentView(R.layout.activity_anasayfa_for_admin);
    }

    public void kullanicilar(View view){
        setContentView(R.layout.kullanicilar);
        conteiner_layout3 = findViewById(R.id.linearlayout3);

        db.collection("Phones").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Her belge için işlemleri gerçekleştirin


                    TextView textView = new TextView(this);
                    textView.setText("E-Mail : "+ document.getId()
                            +"\nTelefon : "+document.getString("phoneNumber"));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, marginInPx, 0, 0);
                    textView.setLayoutParams(layoutParams);
                    textView.setTextColor(textColor);

                    conteiner_layout3.addView(textView);

                }

            } else {

            }
        });



    }

    public void CikisYap(View view){
        auth.signOut();
        Intent intent = new Intent(Anasayfa_forAdmin.this,MainActivity.class);
        startActivity(intent);
        finish();
    }


}