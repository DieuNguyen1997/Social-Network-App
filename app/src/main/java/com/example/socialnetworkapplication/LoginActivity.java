package com.example.socialnetworkapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEdtUsername;
    private EditText mEdtPassword;
    private EditText mEdtConfirmPassword;
    private Button mBtnLogin;
    private Button mBtnReset;
    private ProgressDialog mProgressDialog;
    private CheckBox mCheckBox;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    private FirebaseAuth mAuth;
    private SignInButton mSignInButton;
    private GoogleSignInOptions mSignInOptions;
    private GoogleApiClient mGoogleApiClient;

    private final static int RC_SIGN_IN = 1;
    private final static String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        mSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                        Toast.makeText(LoginActivity.this, "You got an error", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, mSignInOptions)
                .build();
        mProgressDialog = new ProgressDialog(this);


        init();
    }


    private void init() {
        mEdtUsername = findViewById(R.id.edt_username_lg);
        mEdtPassword = findViewById(R.id.edt_password_lg);
        mEdtConfirmPassword = findViewById(R.id.edt_repassword_lg);
        mBtnLogin = findViewById(R.id.btn_login);
        mBtnReset = findViewById(R.id.btn_reset);
        mCheckBox = findViewById(R.id.cbx_nho_pass);
        mSharedPreferences = getSharedPreferences("data account", MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
        mSignInButton = findViewById(R.id.sign_in_button_gg);
        mSignInButton.setSize(SignInButton.SIZE_STANDARD);

        mSignInButton.setOnClickListener(this);
        mBtnReset.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);

        Boolean saveLogin = mSharedPreferences.getBoolean("check", false);
        if (saveLogin == true) {
            String email = mSharedPreferences.getString("email", "");
            String password = mSharedPreferences.getString("password", "");
            String repass = mSharedPreferences.getString("repass", "");
            mEdtUsername.setText(email);
            mEdtPassword.setText(password);
            mEdtConfirmPassword.setText(repass);
            mCheckBox.setChecked(saveLogin);
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.putExtra("email", user.getEmail());
                            intent.putExtra("password", user.getPhoneNumber());
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void resetData() {
        mEdtUsername.setText("");
        mEdtPassword.setText("");
        mEdtConfirmPassword.setText("");
        mCheckBox.setChecked(false);
    }

    private void checkAccount() {
        String email = mEdtUsername.getText().toString();
        String password = mEdtPassword.getText().toString();
        String repass = mEdtConfirmPassword.getText().toString();
        if (!TextUtils.isEmpty(email)) {
            if (!TextUtils.isEmpty(password)) {
                if (!TextUtils.isEmpty(repass)) {
                    if (repass.equals(password)) {
                        mProgressDialog.setMessage("Đang đăng nhập");
                        mProgressDialog.show();
                        mProgressDialog.setCanceledOnTouchOutside(true);
                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            mProgressDialog.dismiss();
                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                        } else {
                                            mProgressDialog.dismiss();
                                            String message = task.getException().getMessage();
                                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại. Lỗi:" + message, Toast.LENGTH_SHORT).show();
                                            resetData();
                                        }
                                    }
                                });
                        if (mCheckBox.isChecked()) {
                            mEditor.putString("email", email);
                            mEditor.putString("password", password);
                            mEditor.putString("repass", repass);
                            mEditor.putBoolean("check", true);
                            mEditor.commit();
                        } else {
                            mEditor.remove("email");
                            mEditor.remove("password");
                            mEditor.remove("repass");
                            mEditor.remove("check");
                            mEditor.apply();
                        }
                    } else
                        Toast.makeText(LoginActivity.this, "Mật khẩu không trùng khớp!", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(LoginActivity.this, "Bạn chưa nhập lại mật khẩu!", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(LoginActivity.this, "Bạn chưa nhập mật khẩu!", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(LoginActivity.this, "Bạn chưa nhập email!!", Toast.LENGTH_SHORT).show();
    }


    public void onClickSignUp(View view) {
        Intent intent = new Intent(this, ResigterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                checkAccount();
                break;
            case R.id.btn_reset:
                resetData();
                break;
            case R.id.sign_in_button_gg:
                signInWithGoogle();
                break;
        }
    }

    private void signInWithGoogle() {
        mProgressDialog.setMessage("Đang đăng nhập bằng google. Vui lòng đợi trong giây lát!!");
        mProgressDialog.show();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
//        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {

            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (googleSignInResult.isSuccess()) {

                GoogleSignInAccount account = googleSignInResult.getSignInAccount();

                firebaseAuthWithGoogle(account);
            }

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        Toast.makeText(LoginActivity.this, "" + authCredential.getProvider(), Toast.LENGTH_LONG).show();

        mAuth.signInWithCredential(authCredential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task AuthResultTask) {
                        if (AuthResultTask.isSuccessful()) {
                            mProgressDialog.dismiss();
                            // Getting Current Login user details.
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.putExtra("email", firebaseUser.getEmail());
                            intent.putExtra("password", firebaseUser.getPhoneNumber());
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}
