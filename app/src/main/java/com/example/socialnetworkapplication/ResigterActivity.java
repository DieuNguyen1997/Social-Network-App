package com.example.socialnetworkapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ResigterActivity extends AppCompatActivity {


    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private EditText mEdtConfirmPassword;
    private Button mBtnSignUp;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resigter);
        mAuth = FirebaseAuth.getInstance();
        mProgressDialog = new ProgressDialog(this);
        init();
    }

    private void init() {
        mEdtUsername = findViewById(R.id.edt_username_rs);
        mEdtPassword = findViewById(R.id.edt_password_rs);
        mEdtConfirmPassword = findViewById(R.id.edt_repassword_rs);
        mBtnSignUp = findViewById(R.id.btn_signup);


        mBtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewAccount();
                mProgressDialog.setTitle("Đăng ký tài khoản");
                mProgressDialog.setMessage("Vui lòng đợi trong giây lát");
                mProgressDialog.show();
            }
        });
    }

    private void createNewAccount() {
        final String email = mEdtUsername.getText().toString();
        final String password = mEdtPassword.getText().toString();
        String repass = mEdtConfirmPassword.getText().toString();
        if (!TextUtils.isEmpty(email)) {
            if (!TextUtils.isEmpty(password)) {
                if (!TextUtils.isEmpty(repass)) {
                    if (repass.equals(password)) {
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            mProgressDialog.dismiss();
                                            Intent intent = new Intent(ResigterActivity.this, LoginActivity.class);
                                            intent.putExtra("email", email);
                                            intent.putExtra("pass", password);
                                            startActivity(intent);
                                        } else {
                                            String message = task.getException().getMessage();
                                            Toast.makeText(ResigterActivity.this, "Bạn gặp vấn đề :" + message, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    } else
                        Toast.makeText(ResigterActivity.this, "Mật khẩu không trùng khớp!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(ResigterActivity.this, "Bạn chưa nhập lại mật khẩu!", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(ResigterActivity.this, "Bạn chưa nhập mật khẩu!", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(ResigterActivity.this, "Bạn chưa nhập email!!", Toast.LENGTH_SHORT).show();
    }


    public void OnClickTxt(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
