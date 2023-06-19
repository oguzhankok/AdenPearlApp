package com.oguzhan.adenpearl;

import static com.oguzhan.adenpearl.AnaSayfa.name;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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


public class Yardim extends AppCompatActivity {


    TextView textViewHelpMessage;
    private ListView listViewMessages;
    private EditText editTextMessage;
    private Button buttonSend;

    EditText messageEditText;

    private ArrayList<String> messageList;
    private ArrayAdapter<String> messageAdapter;

    private String messageText;


    public String phoneNumberr,dokumanId,adSoyad, yardim_mesajiTxt;
    private FirebaseAuth auth;
    FirebaseUser user;
    private FirebaseFirestore firestore;
    EditText yardim_mesaji,yardim_icin_isim;
    Button sendButton,callbutton;
    private static final int REQUEST_CALL_PHONE_PERMISSION = 1;
    private static final String PHONE_NUMBER = "5069060177";
    Button arama_butonu;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yardim);
        sendButton = findViewById(R.id.mail_gonder_button);
        callbutton = findViewById(R.id.ara_button);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        yardim_mesaji = findViewById(R.id.yardim_message);
        yardim_icin_isim = findViewById(R.id.yardim_isim_Soyisim);
        arama_butonu = findViewById(R.id.ara_button);

        auth=FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        dokumanId = user.getEmail();


        DocumentReference userRef = firestore.collection("Phones").document(user.getEmail());
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Firestore'dan telefon numarasını alın
                    phoneNumberr = document.getString("phoneNumber");


                    // Telefon numarasını kullanarak TextView'ı güncelleyin

                }
            }
        });




        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gonder();
            }
        });
        arama_butonu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCallPhonePermission();
            }
        });




    }

    public void gonder(){


        adSoyad = yardim_icin_isim.getText().toString();
        yardim_mesajiTxt = yardim_mesaji.getText().toString();
        HashMap<String, Object> messagedata = new HashMap<>();
        messagedata.put("phoneNumber",phoneNumberr);
        messagedata.put("mail",name);
        messagedata.put("konu","yardım çağrısı");
        messagedata.put("İsim - Soyisim",adSoyad);
        messagedata.put("Mesaj",yardim_mesajiTxt);

        firestore.collection("mesajlar").document(dokumanId)
                .set(messagedata).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Yardim.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();

                    }
                });
        Toast.makeText(this, "Destek talebi alınmıştır...", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Yardim.this,AnaSayfa.class);
        startActivity(intent);
        finish();



    }

    // Arama yapma işlemi
    private void makePhoneCall() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + PHONE_NUMBER));
        startActivity(intent);
    }

    // İzin durumu kontrolü ve izin isteği
    private void checkCallPhonePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // İzin verilmediyse, izin isteği gönder
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE_PERMISSION);
        } else {
            // İzin zaten verilmişse, arama yapma işlemini gerçekleştir
            makePhoneCall();
        }
    }

    // İzin isteği sonucunu ele alma
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PHONE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // İzin verildiyse, arama yapma işlemini gerçekleştir
                makePhoneCall();
            } else {
                // İzin reddedildiyse, kullanıcıya bir geri bildirimde bulunabilirsiniz
                Toast.makeText(this, "Arama izni reddedildi.", Toast.LENGTH_SHORT).show();
            }
        }
    }





    public void geriDon(View view){
        Intent intent = new Intent(Yardim.this,AnaSayfa.class);
        startActivity(intent);
        finish();
    }



}
