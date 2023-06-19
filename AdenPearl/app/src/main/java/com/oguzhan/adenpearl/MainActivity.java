package com.oguzhan.adenpearl;

import static java.lang.Integer.getInteger;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.rpc.context.AttributeContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    String email,password,userName;
    private static final int PERMISSION_PHONE_STATE = 1;
    private boolean permissionRequested = false;
    public static String phoneNumber;
    private FirebaseFirestore firestore;
    public static Map<String, Object> userData = new HashMap<>();


    EditText EmailAdress,sifre,eMailtext,passwordText,phonetxt,kulid;
    EditText isim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        EmailAdress = findViewById(R.id.EmailAdress);

        sifre = findViewById(R.id.sifre);
        firestore = FirebaseFirestore.getInstance();



        FirebaseUser user = auth.getCurrentUser();
        if(user != null){

            Intent intent = new Intent(MainActivity.this,AnaSayfa.class);
            startActivity(intent);
            finish();
        }



    }




    public void KayitEkraniGecis(View view){

        setContentView(R.layout.activity_main2);
        // İzin istendi mi?
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            // İzin verilmediyse kullanıcıdan izin iste
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    PERMISSION_PHONE_STATE);
            if (!permissionRequested || shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        PERMISSION_PHONE_STATE);
                permissionRequested = true;
            }
        }

        else {
            auth = FirebaseAuth.getInstance();
            eMailtext = findViewById(R.id.eMailtext);
            passwordText = findViewById(R.id.passwordText);

            FirebaseUser user = auth.getCurrentUser();
        }



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // İzin verildiyse devam et
                    // Telefon numarasını almak için gerekli işlemleri yapabilirsiniz
                } else {
                    // İzin reddedildiyse kullanıcıya gerekli bildirimi gösterebilirsiniz
                    Toast.makeText(this, R.string.permission_phone, Toast.LENGTH_SHORT).show();
                    setContentView(R.layout.activity_main);
                }
                break;
            }
        }
    }


    public void KayitOl(View view){
        email = eMailtext.getText().toString();
        password = passwordText.getText().toString();

        phonetxt = findViewById(R.id.editTextPhone);
        phoneNumber = phonetxt.getText().toString();


        if(email.equals("")||password.equals("")||phoneNumber.length()!=10){
            Toast.makeText(this,"Geçerli bir parola, telefon ve email girin.",Toast.LENGTH_LONG).show();
        }else{
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                FirebaseUser user = auth.getCurrentUser();


                @Override
                public void onSuccess(AuthResult authResult) {

                    userName = authResult.getUser().getEmail();
                    String dokumanId = userName;
                    HashMap<String, Object> phoneData = new HashMap<>();
                    phoneData.put("phoneNumber",phoneNumber);



                    firestore.collection("Phones").document(dokumanId)
                            .set(phoneData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();

                                }
                            });


                    userName = authResult.getUser().getEmail();



                    Intent intent = new Intent(MainActivity.this, AnaSayfa.class);
                    startActivity(intent);
                    finish();



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }

    }


    public void GirisEkraniGecis (View view){
        setContentView(R.layout.activity_main);
    }
    public void GirisYap(View view){

        String email = EmailAdress.getText().toString();
        String password = sifre.getText().toString();
        if(email.equals("")||password.equals("")){
            Toast.makeText(this,"Geçerli bir e mail ve şifre girin",Toast.LENGTH_LONG).show();
        }
        else if(email.equals("ouzhankk@hotmail.com")){
            Intent intent = new Intent(MainActivity.this,Anasayfa_forAdmin.class);
            startActivity(intent);
            finish();
        }
        else{
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(MainActivity.this,AnaSayfa.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}