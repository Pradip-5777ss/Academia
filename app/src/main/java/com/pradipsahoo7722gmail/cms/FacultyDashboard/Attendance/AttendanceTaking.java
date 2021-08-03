package com.pradipsahoo7722gmail.cms.FacultyDashboard.Attendance;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;
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
import com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.AcademicSyllabus.UploadAcademicSyllabus;
import com.pradipsahoo7722gmail.cms.AdminDashboard.Academics.AcademicSyllabus.UploadAcademicSyllabusModel;
import com.pradipsahoo7722gmail.cms.FacultyDashboard.FacultyDashboard;
import com.pradipsahoo7722gmail.cms.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class AttendanceTaking extends AppCompatActivity {

    ImageView browseDoc, uploadDoc;
    ImageButton cancelButton;
    Button browseBtn, uploadBtn, viewPdf;

    TextView pdfTitle;
    String pdfName;
    Uri filepath;
    final static int RC_CODE = 101;
    ProgressDialog pd;

    StorageReference storageReference;
    DatabaseReference databaseReference;

    Spinner streamSpinner, semesterSpinner, subjectSpinner;
    String student_stream, student_semester, student_subject;
    String selectedStream, selectedSemester, selectedSubject;
    String[] streamList = new String[]{"Select Stream", "CSE", "ECE", "EE", "ME", "CE", "L.L.B", "M.A L.L.B"};
    String[] semesterList = new String[]{"Select Semester", "1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th"};
    String[] subjectList = new String[]{"Select Semester", "Data Structure", "Algorithm", "DBMS", "Operating System", "Computer Architecture", "Automata Theory", "Compiler Design", "Machine Learning"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_taking);

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Attendance");

        pdfTitle = findViewById(R.id.attendance_pdf_TextView);
        browseDoc = findViewById(R.id.attendance_scan_Pdf);
        uploadDoc = findViewById(R.id.attendance_upload_Pdf);
        cancelButton = findViewById(R.id.attendance_cancel_btn);
        browseBtn = findViewById(R.id.attendance_browse_Btn);
        uploadBtn = findViewById(R.id.attendance_upload_btn);
        viewPdf = findViewById(R.id.viewATT_doc);

        streamSpinner = findViewById(R.id.select_Att_stream_spinner);
        semesterSpinner = findViewById(R.id.select_semester_spinner);
        subjectSpinner = findViewById(R.id.select_subject_spinner);

        uploadDoc.setVisibility(View.INVISIBLE);
        cancelButton.setVisibility(View.INVISIBLE);

        cancelButton.setOnClickListener(view -> {
            uploadDoc.setVisibility(View.INVISIBLE);
            cancelButton.setVisibility(View.INVISIBLE);
            browseDoc.setVisibility(View.VISIBLE);
        });

        //  Stream Spinner
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


        //  Semester Spinner
        final List<String> semester = new ArrayList<>(Arrays.asList(semesterList));
        final ArrayAdapter<String> spinnerSemesterAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, semester) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
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

        spinnerSemesterAdapter.setDropDownViewResource(R.layout.spinner_item);
        semesterSpinner.setAdapter(spinnerSemesterAdapter);

        semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSemester = (String) semesterSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        //  subject spinner
        final List<String> subject = new ArrayList<>(Arrays.asList(subjectList));
        final ArrayAdapter<String> spinnerSubjectAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, subject) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
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

        spinnerSubjectAdapter.setDropDownViewResource(R.layout.spinner_item);
        subjectSpinner.setAdapter(spinnerSubjectAdapter);

        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSubject = (String) subjectSpinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    public void uploadAttendancesBackToPreviousPage(View view) {
        startActivity(new Intent(getApplicationContext(), FacultyDashboard.class));
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
        student_semester = semesterSpinner.getSelectedItem().toString();
        student_subject = subjectSpinner.getSelectedItem().toString();

        StorageReference reference = storageReference.child("Faculty/" + "Attendance/" + UUID.randomUUID().toString() + "-" + pdfName);
        reference.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl()
                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        AttendanceTakingModel data = new AttendanceTakingModel(pdfName, uri.toString(), student_stream, student_semester, student_subject);

                                        databaseReference.child(Objects.requireNonNull(databaseReference.push().getKey())).setValue(data);
                                        pd.dismiss();
                                        Toast.makeText(AttendanceTaking.this, "File Uploaded", Toast.LENGTH_SHORT).show();
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
                    public void onProgress(@NonNull @org.jetbrains.annotations.NotNull UploadTask.TaskSnapshot snapshot) {
                        long percent = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                        pd.setMessage("Uploaded : " + percent + "%");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @org.jetbrains.annotations.NotNull Exception e) {
                        Toast.makeText(AttendanceTaking.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void viewAttendance(View view) {
        startActivity(new Intent(getApplicationContext(), ViewAttendance.class));
    }



}