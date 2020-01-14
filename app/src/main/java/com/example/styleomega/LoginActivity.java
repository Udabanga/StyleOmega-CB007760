package com.example.styleomega;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.styleomega.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    private Button loginButton, registerButton;
    private EditText inputEmail, inputPassword;
    private  ProgressDialog dialog;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fStore;
    private String userID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        registerButton = (Button) findViewById(R.id.registerButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.loginPassword);

        mAuth = FirebaseAuth.getInstance();
        this.fStore = FirebaseFirestore.getInstance();

        /*if(mAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }*/

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= inputEmail.getText().toString().trim();
                String password= inputPassword.getText().toString().trim();
                dialog = new ProgressDialog(LoginActivity.this);
                dialog.setTitle("Loading");
                dialog.setMessage("Checking logging credentials");
                dialog.setCanceledOnTouchOutside(false);


                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Enter your E-mail and Password", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "Enter your E-mail", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Enter your Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    dialog.show();
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //Toast.makeText(LoginActivity.this, "Logged in", Toast.LENGTH_LONG).show();

                                userID = mAuth.getCurrentUser().getUid();
                                //DocumentReference documentReferenceUser = fStore.collection("User").document();
                                fStore.collection("Admin").document(userID).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.getResult().exists()) {
                                                    Toast.makeText(LoginActivity.this, "Admin login", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                                                    startActivity(intent);
                                                    dialog.dismiss();
                                                }
                                                //Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                                                //startActivity(intent);
                                                else{
                                                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                                                    dialog.dismiss();

                                                }
                                            }
                                        });
                                fStore.collection("User").document(userID).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.getResult().exists()) {
                                                    Toast.makeText(LoginActivity.this, "User login", Toast.LENGTH_LONG).show();
                                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                    startActivity(intent);
                                                    dialog.dismiss();
                                                }
                                                //Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                                                //startActivity(intent);
                                                else{
                                                    Toast.makeText(LoginActivity.this, "Error", Toast.LENGTH_LONG).show();
                                                    dialog.dismiss();

                                                }
                                            }
                                        });
                                /*if(documentReferenceAdmin.equals(userID)) {
                                    Toast.makeText(LoginActivity.this, "Admin login", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                    Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this, "Not Admin", Toast.LENGTH_LONG).show();
                                    dialog.dismiss();
                                }*/

                                //Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                //startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                dialog.dismiss();
                            }
                        }
                    });
                }
            }
        });

        /*loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email= inputEmail.getText().toString().trim();
                String password= inputPassword.getText().toString().trim();
                dialog = new ProgressDialog(LoginActivity.this);
                dialog.setTitle("Loading");
                dialog.setMessage("Checking logging credentials");
                //dialog.setCanceledOnTouchOutside(false);


                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Enter your E-mail and Password", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(email)){
                    Toast.makeText(LoginActivity.this, "Enter your E-mail", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Enter your Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    dialog.show();
                    validateLogin(email, password);
                }
            }
        });*/
    }

    /*public void validateLogin(final String email, final String password){
        final DatabaseReference myRef;

        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("User").child(email).exists()){
                    User loginUser = dataSnapshot.child("User").child(email).getValue(User.class);
                    if(loginUser.getPassword().equals(password)){
                        dialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this,"Logged in",Toast.LENGTH_LONG).show();
                    }
                    else{
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this,"Incorrect Password",Toast.LENGTH_LONG).show();
                        inputPassword.getText().clear();
                    }
                }
                else if(dataSnapshot.child("Admin").child(email).exists()){
                    Admin loginUser = dataSnapshot.child("Admin").child(email).getValue(Admin.class);
                    if(loginUser.getPassword().equals(password)){
                        dialog.dismiss();
                        Intent intent = new Intent(LoginActivity.this,AdminHomeActivity.class);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this,"Admin Logged in",Toast.LENGTH_LONG).show();
                    }
                    else{
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this,"Incorrect Password",Toast.LENGTH_LONG).show();
                        inputPassword.getText().clear();
                    }
                }
                else{
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this,"Incorrect Email",Toast.LENGTH_LONG).show();
                    inputPassword.getText().clear();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*//*
    }*/

}
