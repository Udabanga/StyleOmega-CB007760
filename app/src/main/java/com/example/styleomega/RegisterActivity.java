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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    private Button registerButton, cancelButton;
    private EditText inputEmail, inputFName, inputLName, inputPhone, inputPassword, inputCPassword;
    private ProgressDialog dialog;
    private User user;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerButton = (Button) findViewById(R.id.registerButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        inputEmail = (EditText) findViewById(R.id.registerEmail);
        inputFName = (EditText) findViewById(R.id.registerFName);
        inputLName = (EditText) findViewById(R.id.registerLName);
        inputPhone = (EditText) findViewById(R.id.registerPhone);
        inputPassword = (EditText) findViewById(R.id.registerPassword);
        inputCPassword = (EditText) findViewById(R.id.registerCPassword);



        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= inputEmail.getText().toString().trim();
                String fName= inputFName.getText().toString().trim();
                String lName= inputLName.getText().toString().trim();
                String phone = (inputPhone.getText().toString().trim());
                String password1= inputPassword.getText().toString().trim();
                String password2= inputCPassword.getText().toString().trim();
                dialog = new ProgressDialog(RegisterActivity.this);
                dialog.setTitle("Loading");
                dialog.setMessage("Setting up account");
                dialog.setCanceledOnTouchOutside(false);



                if(TextUtils.isEmpty(email)){
                    Toast.makeText(RegisterActivity.this, "Enter your E-mail", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(fName)){
                    Toast.makeText(RegisterActivity.this, "Enter your First Name", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(lName)){
                    Toast.makeText(RegisterActivity.this, "Enter your Last Name", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(RegisterActivity.this, "Enter your Phone number", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(password1)){
                    Toast.makeText(RegisterActivity.this, "Enter your Password", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(password2)){
                    Toast.makeText(RegisterActivity.this, "Enter your Confirmed Password", Toast.LENGTH_SHORT).show();
                }
                else {
                    dialog.show();
                    if(password1.equals(password2)){
                        validateUser(email, fName, lName, phone, password1, password2);


                    }
                    else {
                        dialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Passwords do not match, try again", Toast.LENGTH_SHORT).show();
                        inputPassword.getText().clear();
                        inputCPassword.getText().clear();

                    }
                }

            }
        });
    }

    public void validateUser(final String email, final String fName, final String lName, final String phone, final String password1, final String password2){
        final DatabaseReference myRef;
        myRef = FirebaseDatabase.getInstance().getReference();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("User").child(email).exists()){
                    dialog.dismiss();
                    Toast.makeText(RegisterActivity.this,"Account exists",Toast.LENGTH_LONG).show();
                }
                else{
                    User user = new User(email, fName, lName, password1, phone);
                    myRef.child("User").child(email).setValue(user);
                    dialog.dismiss();
                    Toast.makeText(RegisterActivity.this,"Account created",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
