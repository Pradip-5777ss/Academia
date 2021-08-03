package com.pradipsahoo7722gmail.cms.AdminDashboard.Notice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.AcademicCalendar.UploadAcademicCalendar;
import com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.AcademicCalendar.UploadCalendarHelperText;
import com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.Academics;
import com.pradipsahoo7722gmail.cms.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

public class UploadNotice extends AppCompatActivity {

    ImageView browseDoc, uploadDoc;
    ImageButton cancelButton;
    Button browseBtn, uploadBtn;

    TextView pdfTitle;
    private String pdfName;
    Uri filepath;
    private final static int RC_CODE = 101;
    ProgressDialog pd;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Notice");

        pdfTitle = findViewById(R.id.notice_pdf_TextView);
        browseDoc = findViewById(R.id.notice_scan_Pdf);
        uploadDoc = findViewById(R.id.notice_upload_Pdf);
        cancelButton = findViewById(R.id.notice_cancel_btn);
        browseBtn = findViewById(R.id.notice_browse_Btn);
        uploadBtn = findViewById(R.id.notice_upload_btn);

        uploadDoc.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);

        cancelButton.setOnClickListener(view -> {
            uploadDoc.setVisibility(View.INVISIBLE);
            cancelButton.setVisibility(View.INVISIBLE);
            browseDoc.setVisibility(View.VISIBLE);
        });
    }

    public void backToPreviousPage(View view) {
        Intent intent = new Intent(getApplicationContext(), Academics.class);
        startActivity(intent);
        finish();
    }

    public void browseNoticeDoc(View view) {
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent();
                        intent.setType("application/pdf" + " application/docx");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Pdf Files"), RC_CODE);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @SuppressLint("Recycle")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            if (data.getData() != null) {
                filepath = data.getData();
                uploadDoc.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.VISIBLE);
                browseDoc.setVisibility(View.INVISIBLE);
            }

            if (filepath.toString().startsWith("content://")) {
                Cursor cursor;
                try {
                    cursor = this.getContentResolver().query(filepath, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        pdfName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (filepath.toString().startsWith("file://")) {
                pdfName = new File(filepath.toString()).getName();
            }
            pdfTitle.setText(pdfName);
        }
    }

    public void uploadNoticeDoc(View view) {
        if (filepath == null) {
            Toast.makeText(this, "Please select pdf", Toast.LENGTH_SHORT).show();
            return;
        }
        uploadProcess(filepath);
    }

    private void uploadProcess(Uri filepath) {

        pd = new ProgressDialog(this);
        pd.setTitle("File Uploading");
        pd.show();

        StorageReference reference = storageReference.child("Notice/" + UUID.randomUUID().toString() + "-" + pdfName);
        reference.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        UploadNoticeModel data = new UploadNoticeModel(pdfName, uri.toString());

                                        databaseReference.child(Objects.requireNonNull(databaseReference.push().getKey())).setValue(data);
                                        pd.dismiss();
                                        Toast.makeText(UploadNotice.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                                        uploadDoc.setVisibility(View.INVISIBLE);
                                        cancelButton.setVisibility(View.INVISIBLE);
                                        browseDoc.setVisibility(View.VISIBLE);
                                        pdfTitle.setText("");
                                    }

                                });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull @NotNull UploadTask.TaskSnapshot snapshot) {
                        long percent = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                        pd.setMessage("Uploaded : " + (int) percent + "%");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(UploadNotice.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void viewAcademicNotice(View view) {
        startActivity(new Intent(getApplicationContext(), ViewNotice.class));
    }
}