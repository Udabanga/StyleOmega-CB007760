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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {
    private Button loginButton, registerButton;
    private EditText inputEmail, inputPassword;
    private  ProgressDialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        registerButton = (Button) findViewById(R.id.registerButton);
        loginButton = (Button) findViewById(R.id.loginButton);
        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.loginPassword);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener(){
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
        });
    }

    public void validateLogin(final String email, final String password){
        final DatabaseReference myRef;

        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("User").child(email).exists()){
                    User loginUser = dataSnapshot.child("User").child(email).getValue(User.class);
                    if(loginUser.getPassword().equals(password)){
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this,"Logged in",Toast.LENGTH_LONG).show();
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
        });
    }

}
