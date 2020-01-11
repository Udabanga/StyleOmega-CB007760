package com.example.styleomega;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    private Button registerButton,cancelButton;
    private EditText inputEmail,inputFName,inputLName,inputPhone,inputPassword,inputCPassword;
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
    }



}
