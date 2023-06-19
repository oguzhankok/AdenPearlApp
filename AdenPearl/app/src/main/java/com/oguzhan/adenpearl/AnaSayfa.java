package com.oguzhan.adenpearl;




import static com.oguzhan.adenpearl.MainActivity.userData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.FirestoreClient;


import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnaSayfa extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    int sayac =0,i=0;
    public static String name,username,phoneNumber;
    String userId;
    ImageView imageView;
    FirebaseUser user;
    TextView kullaniciAdii,textView7, emailtext, teltext,mevcutCagri;





    DocumentSnapshot snapshot;

    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_sayfa);





        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user = auth.getCurrentUser();
        userId=user.getUid();
        kullaniciAdii=findViewById(R.id.kullaniciAdii);
        textView7 = findViewById(R.id.textView7);
        mevcutCagri = findViewById(R.id.mevcut_cagri);
        emailtext = findViewById(R.id.emailadresi);
        teltext=findViewById(R.id.telno);


        //araç siparişi varsa
        DocumentReference userRef2 = firebaseFirestore.collection("araç_çağrıları").document(user.getEmail());
        userRef2.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Firestore'dan telefon numarasını alın

                    mevcutCagri.setText("\nAraç çağrınız alınmıştır, Şoförünüz en kısa sürede sizinle iletişime geçecektir. İptal etmek isterseniz yardım bölümünden bize ulaşabilirsiniz...");

                    // Telefon numarasını kullanarak TextView'ı güncelleyin

                }
                else{
                    //mevcutCagri.setText("\n\nMevcut araç çağrınız bulunmamaktadır.");
                }
            }
        });





        DocumentReference userRef = firebaseFirestore.collection("Phones").document(user.getEmail());
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    // Firestore'dan telefon numarasını alın
                    phoneNumber = document.getString("phoneNumber");
                    teltext.setText("+90 "+document.getString("phoneNumber"));

                    // Telefon numarasını kullanarak TextView'ı güncelleyin

                }
            }
        });
        name = user.getEmail();
        //getData();

        int atIndex = name.indexOf('@');
        username = name.substring(0,atIndex);


        kullaniciAdii.setText(username);
        textView7.setText("Hoşgeldiniz "+username);
        emailtext.setText(name);

    }

    public void yardimGecis(View view){
        Intent intent = new Intent(AnaSayfa.this,Yardim.class);
        startActivity(intent);

    }
    public void turlarGecis(View view){
        Intent intent = new Intent(AnaSayfa.this,Turlar.class);
        startActivity(intent);
    }


    public void arabaCagirGecis (View view) {
        Intent intent = new Intent(AnaSayfa.this,YolculukBilgileri.class);
        startActivity(intent);


    }
    public void CikisYap(View view){
        auth.signOut();
        Intent intent = new Intent(AnaSayfa.this,MainActivity.class);
        startActivity(intent);
        finish();


    }
}