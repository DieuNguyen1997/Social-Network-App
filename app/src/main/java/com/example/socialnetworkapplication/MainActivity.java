package com.example.socialnetworkapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnLogin;
    private Button mBtnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        mBtnLogin = findViewById(R.id.btn_login_main);
        mBtnSignUp = findViewById(R.id.btn_signup_main);

        mBtnLogin.setOnClickListener(this);
        mBtnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login_main:
                Intent intent1 = new Intent(this, LoginActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.btn_signup_main:
                Intent intent2 = new Intent(this, ResigterActivity.class);
                startActivity(intent2);
                finish();
                break;
        }
    }
}
