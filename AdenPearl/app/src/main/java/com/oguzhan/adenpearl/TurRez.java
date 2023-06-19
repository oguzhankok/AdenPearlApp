package com.oguzhan.adenpearl;

import static com.oguzhan.adenpearl.AnaSayfa.phoneNumber;
import static com.oguzhan.adenpearl.Turlar.tur;
import static com.oguzhan.adenpearl.YolculukBilgileri.hata_yazisi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TurRez extends AppCompatActivity {

    EditText Isim;
    Spinner spinnerkisi;
    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String dokumanId, isim,turTürü,fiyat="1",fiyatSon,mevcut_kisi="1";
    TextView kis_sayisi_belirtec,bilgilendirme,Ucret;
    String kisi_sayisi="1";
    ArrayList<Integer> KisiSayisiList;
    ArrayAdapter<Integer> adapterKisi;
    String yeni_kisi="1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tur_rez);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        dokumanId = user.getEmail();
        firestore = FirebaseFirestore.getInstance();
        Isim = findViewById(R.id.rez_isim);
        kis_sayisi_belirtec = findViewById(R.id.kisiSayisitxt);
        spinnerkisi = findViewById(R.id.kisiSayisi2);
        bilgilendirme = findViewById(R.id.bilgilendirmeYazisi);
        Ucret = findViewById(R.id.fiyatYazisi);


       if(tur==2) {
           DocumentReference userRef = firestore.collection("Turlar").document("Batı Karadeniz");
           userRef.get().addOnCompleteListener(task -> {
               if (task.isSuccessful()) {
                   DocumentSnapshot document = task.getResult();
                   if (document.exists()) {
                       // Firestore'dan telefon numarasını alın

                       bilgilendirme.setText(document.getString("bilgilendirme"));
                       fiyat = document.getString("fiyat");
                       mevcut_kisi = document.getString("mevcut_kayit");


                       // Telefon numarasını kullanarak TextView'ı güncelleyin

                   } else {

                   }
               }
           });
       }
        if(tur==3) {
            DocumentReference userRef = firestore.collection("Turlar").document("Pamukkale");
            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Firestore'dan telefon numarasını alın

                        bilgilendirme.setText(document.getString("bilgilendirme"));
                        fiyat = document.getString("fiyat");
                        mevcut_kisi = document.getString("mevcut_kayit");
                        // Telefon numarasını kullanarak TextView'ı güncelleyin

                    } else {

                    }
                }
            });
        }
        if(tur==1) {
            DocumentReference userRef = firestore.collection("Turlar").document("adrasan");
            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Firestore'dan telefon numarasını alın

                        bilgilendirme.setText(document.getString("bilgilendirme"));
                        fiyat = document.getString("fiyat");
                        mevcut_kisi = document.getString("mevcut_kayit");
                        // Telefon numarasını kullanarak TextView'ı güncelleyin

                    } else {

                    }
                }
            });
        }

        KisiSayisiList = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            KisiSayisiList.add(i);
        }
        adapterKisi = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, KisiSayisiList);
        adapterKisi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerkisi.setAdapter(adapterKisi);
        spinnerkisi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kis_sayisi_belirtec.setText(adapterView.getItemAtPosition(i).toString());
                kisi_sayisi = kis_sayisi_belirtec.getText().toString();
                fiyatSon = String.valueOf(Integer.parseInt(fiyat)*(Integer.parseInt(kisi_sayisi)));
                Ucret.setText(fiyatSon+" TL");

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                kis_sayisi_belirtec.setText(adapterView.getItemAtPosition(0).toString());
                kisi_sayisi = "1";

            }
        });



    }
    public void geriDon(View view){
        Intent intent = new Intent(TurRez.this,Turlar.class);
        startActivity(intent);
        finish();

    }
    public void tur_rez_alindi(View view){





        fiyatSon = String.valueOf((Integer.parseInt(fiyat))*(Integer.parseInt(kisi_sayisi)));






        if(tur==1) {
            turTürü = "Adrasan";
        }
        else if(tur==2){
            turTürü = "Batı Karadeniz";
        }
        else{
            turTürü = "Pamukkale";
        }
        isim = Isim.getText().toString();

        if (isim.length() == 0 || kisi_sayisi.equals("0")) {
            Toast.makeText(this,"lütfen tüm alanları doldurunuz...",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(TurRez.this,TurRez.class);
            startActivity(intent);
            finish();

        }
        else if(mevcut_kisi.equals("60")){
            Toast.makeText(this,"Maalesef bu turumuz tamamen doludur...",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(TurRez.this,TurRez.class);
            startActivity(intent);
            finish();

        }
        else {
            HashMap<String, Object> rezData = new HashMap<>();
            rezData.put("rezervasyon", isim);
            rezData.put("kişi", kisi_sayisi);
            rezData.put("Tur", turTürü);
            rezData.put("Ücret", fiyatSon);
            rezData.put("Tel",phoneNumber);
            firestore.collection("tur_rezervasyonları").document(dokumanId)
                    .set(rezData).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(TurRez.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                        }
                    });


            if(tur==1){

                DocumentReference userRef7 = firestore.collection("Turlar").document("adrasan");
                userRef7.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {

                            yeni_kisi =String.valueOf((Integer.parseInt(kisi_sayisi))+(Integer.parseInt(document.getString("mevcut_kayit"))));
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("mevcut_kayit", yeni_kisi);
                            userRef7.update(updates)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Güncelleme başarılı
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Güncelleme başarısız
                                        }
                                    });
                        } else {

                        }
                    }
                });
            }
            else if(tur==2){

                DocumentReference userRef8 = firestore.collection("Turlar").document("Batı Karadeniz");
                userRef8.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            yeni_kisi =String.valueOf((Integer.parseInt(kisi_sayisi))+(Integer.parseInt(document.getString("mevcut_kayit"))));
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("mevcut_kayit", yeni_kisi);
                            userRef8.update(updates)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Güncelleme başarılı
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Güncelleme başarısız
                                        }
                                    });


                        } else {

                        }
                    }
                });
            }
            else{

                DocumentReference userRef9 = firestore.collection("Turlar").document("Pamukkale");
                userRef9.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            yeni_kisi =String.valueOf((Integer.parseInt(kisi_sayisi))+(Integer.parseInt(document.getString("mevcut_kayit"))));
                            Map<String, Object> updates = new HashMap<>();
                            updates.put("mevcut_kayit", yeni_kisi);
                            userRef9.update(updates)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            // Güncelleme başarılı
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Güncelleme başarısız
                                        }
                                    });

                        } else {

                        }
                    }
                });
            }
            Toast.makeText(this,"Tur kaydınız alınmıştır...",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(TurRez.this,AnaSayfa.class);
            startActivity(intent);
            finish();
        }




        }

}
