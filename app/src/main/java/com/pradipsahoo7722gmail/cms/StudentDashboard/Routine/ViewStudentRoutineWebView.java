package com.pradipsahoo7722gmail.cms.StudentDashboard.Routine;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pradipsahoo7722gmail.cms.R;

import java.net.URLEncoder;

public class ViewStudentRoutineWebView extends AppCompatActivity {

    WebView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student_routine_web_view);

        pdfView = findViewById(R.id.ViewStudentRoutineWebView);
        pdfView.getSettings().setJavaScriptEnabled(true);

        String filename = getIntent().getStringExtra("filename");
        String fileUrl = getIntent().getStringExtra("fileUrl");

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle(filename);
        pd.setMessage("Opening...");

        pdfView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });

        String url = "";
        try {
            url = URLEncoder.encode(fileUrl,"UTF-8");
        } catch (Exception e) {
        }

        pdfView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);
    }
}