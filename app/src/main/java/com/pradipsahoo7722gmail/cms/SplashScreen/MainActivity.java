package com.pradipsahoo7722gmail.cms.SplashScreen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.pradipsahoo7722gmail.cms.InternetConnectivity.InternetConnection;
import com.pradipsahoo7722gmail.cms.R;
import com.pradipsahoo7722gmail.cms.SelectUserLogin.SelectLoginProcess;

import static com.pradipsahoo7722gmail.cms.R.style.CustomDialogTheme;
public class MainActivity extends AppCompatActivity {

    ImageView logo, appName;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logo = findViewById(R.id.app_logo);
        appName = findViewById(R.id.group_img);
        progressBar = findViewById(R.id.splash_progressbar);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                InternetConnection checkInternet = new InternetConnection();
                if (!checkInternet.isConnected(MainActivity.this)) {
                    showCustomDialog();
                } else {
                    startActivity(new Intent(getApplicationContext(), SelectLoginProcess.class));
                    finish();
                }
            }
        }, 2000);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }

    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, CustomDialogTheme);
        builder.setMessage("Connection Error! Please connect to the internet")
                .setCancelable(false)
                .setPositiveButton("Connect", (dialogInterface, i) -> startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS)))
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MainActivity.this.onBackPressed();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}