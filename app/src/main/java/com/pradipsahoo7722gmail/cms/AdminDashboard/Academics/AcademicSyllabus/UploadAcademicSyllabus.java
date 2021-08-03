package com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.AcademicSyllabus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.Academics;
import com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.StudyMaterial.UploadAcademicResourcesHelperClass;
import com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.StudyMaterial.UploadStudyMaterial;
import com.pradipsahoo7722gmail.cms.R;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UploadAcademicSyllabus extends AppCompatActivity {

    ImageView browseDoc, uploadDoc;
    ImageButton cancelButton;
    Button browseBtn, uploadBtn, viewPdf;

    TextView pdfTitle;
    String pdfName;
    Uri filepath;
    final static int RC_CODE = 101;
    ProgressDialog pd;

    FirebaseDatabase database;
    FirebaseUser AdminId;
    String registerAdminID;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    Spinner streamSpinner;
    String student_stream;
    String selectedStream;
    String[] streamList = new String[]{"Select Stream", "CSE", "ECE", "EE", "ME", "CE", "L.L.B", "M.A L.L.B"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_academic_syllabus);

        //testing one
//        database = FirebaseDatabase.getInstance();
//        AdminId = FirebaseAuth.getInstance().getCurrentUser();
//        registerAdminID = AdminId.getUid();
//        databaseReference = database.getReference().child("AdminData").child(registerAdminID).child("Academics").child("AcademicSyllabus");
//        storageReference = FirebaseStorage.getInstance().getReference();


        //right one
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference("Academics").child("AcademicSyllabus");

        pdfTitle = findViewById(R.id.syllabus_pdf_TextView);
        browseDoc = findViewById(R.id.syllabus_scan_Pdf);
        uploadDoc = findViewById(R.id.syllabus_upload_Pdf);
        cancelButton = findViewById(R.id.syllabus_cancel_btn);
        browseBtn = findViewById(R.id.syllabus_browse_Btn);
        uploadBtn = findViewById(R.id.syllabus_upload_btn);
        viewPdf = findViewById(R.id.viewSYL_doc);

        streamSpinner = findViewById(R.id.select_stream_spinner);

        uploadDoc.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);

        cancelButton.setOnClickListener(view -> {
            uploadDoc.setVisibility(View.INVISIBLE);
            cancelButton.setVisibility(View.INVISIBLE);
            browseDoc.setVisibility(View.VISIBLE);
        });

        //stream spinner
        final List<String> stream = new ArrayList<>(Arrays.asList(streamList));
        final ArrayAdapter<String> spinnerStreamAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, stream) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @com.google.firebase.database.annotations.NotNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        spinnerStreamAdapter.setDropDownViewResource(R.layout.spinner_item);
        streamSpinner.setAdapter(spinnerStreamAdapter);

        //  Item selector method for stream spinner
        streamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStream = (String) streamSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    public void syllabusBackToPreviousPage(View view) {
        Intent intent = new Intent(getApplicationContext(), Academics.class);
        startActivity(intent);
        finish();
    }

    public void browseSyllabusDoc(View view) {
        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent();
                        intent.setType("application/pdf" + "application/docx");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"Select file"), RC_CODE);
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

    public void uploadSyllabusDoc(View view) {
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

        student_stream = streamSpinner.getSelectedItem().toString();

        StorageReference reference = storageReference.child("Academics/" + "Academic Syllabus/" + UUID.randomUUID().toString() + "-" + pdfName);
        reference.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        UploadAcademicSyllabusModel data = new UploadAcademicSyllabusModel(pdfName, uri.toString(), student_stream);

                                        databaseReference.child(Objects.requireNonNull(databaseReference.push().getKey())).setValue(data);
                                        pd.dismiss();
                                        Toast.makeText(UploadAcademicSyllabus.this, "File Uploaded", Toast.LENGTH_SHORT).show();
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
                        pd.setMessage("Uploaded : " + percent + "%");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(UploadAcademicSyllabus.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void viewAcademicSyllabus(View view) {
        Intent intent = new Intent(getApplicationContext(), ViewAcademicSyllabus.class);
        startActivity(intent);
    }
}