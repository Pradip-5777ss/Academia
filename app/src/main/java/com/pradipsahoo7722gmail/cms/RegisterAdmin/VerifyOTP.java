package com.pradipsahoo7722gmail.cms.RegisterAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.pradipsahoo7722gmail.cms.LoginUser.LoginScreen;
import com.pradipsahoo7722gmail.cms.R;

import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {

    PinView pinFromUser;
    Button signUpBtn;
    ImageView backBtn;
    String codeBySystem;
    ProgressBar progressBar;
    TextView showPhoneNo;

    String fullName, username, email, password, gender, date, phone, uniqueKey;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        //  Nav bar design
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS,
                    WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        // hooks
        pinFromUser = findViewById(R.id.pin_view);
        signUpBtn = findViewById(R.id.verify_code);
        showPhoneNo = findViewById(R.id.ph_no_view);
        backBtn = findViewById(R.id.back_btn);
        progressBar = findViewById(R.id.progressBar);

//        dbRoot = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
//        uid = mAuth.getCurrentUser().getUid();

        //  Get all the data from the previous screen
        fullName = getIntent().getStringExtra("fullName");
        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");
        gender = getIntent().getStringExtra("gender");
        date = getIntent().getStringExtra("age");
        phone = getIntent().getStringExtra("phoneNo");

        //  Show the Current user phone number
        showPhoneNo.setText(getIntent().getStringExtra("phoneNo"));

        //  Method call for send the verification code on user given phone number
        sendVerificationCodeToUser(phone);
    }

    private void sendVerificationCodeToUser(String phoneNo) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pinFromUser.setText(code);
                        verifyCode(code);
                    }
                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {
                    Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeBySystem, code);
        signInWithPhoneAuthCredential(credential);
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(VerifyOTP.this, "Verification Complete", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                        Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
                        storeNewUserData();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            Toast.makeText(VerifyOTP.this, "Verification Not Completed! Try Again", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void storeNewUserData() {

//        DatabaseReference reference = dbRoot.getReference("AdminData");
//        uniqueKey = reference.push().getKey();
//        UserHelperClass addNewUser = new UserHelperClass(fullName, username, email, password, gender, date, phone, uniqueKey);
//
//        reference.child(uniqueKey).setValue(addNewUser);
    }
    public void signUpSuccessFull(View view) {

        String code = pinFromUser.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.VISIBLE);
                            if (!code.isEmpty()) {
                                verifyCode(code);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Error! in Verification", Toast.LENGTH_SHORT).show();
                            pinFromUser.setText("");
                        }
                    }
                });
    }

    public void backScreenBtn(View view) {
        Intent intent = new Intent(getApplicationContext(), SignupAdminThirdScreen.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);
        finish();
    }

}