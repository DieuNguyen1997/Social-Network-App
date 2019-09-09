package com.example.socialnetworkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HomeActivity extends AppCompatActivity {

    private TextView mTxtEmail;
    private TextView mTxtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }

    private void init() {
//        mTxtEmail = findViewById(R.id.username);
//        mTxtName=  findViewById(R.id.password);
//        Intent intent = getIntent();
//        mTxtName.setText(intent.getStringExtra("email"));
//        mTxtEmail.setText(intent.getStringExtra("name"));
    }
}
