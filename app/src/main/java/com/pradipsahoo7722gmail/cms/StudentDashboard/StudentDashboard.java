package com.pradipsahoo7722gmail.cms.StudentDashboard;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pradipsahoo7722gmail.cms.BuildConfig;
import com.pradipsahoo7722gmail.cms.LoginUser.LoginScreen;
import com.pradipsahoo7722gmail.cms.R;
import com.pradipsahoo7722gmail.cms.SelectUserLogin.StudentLoginPage;
import com.pradipsahoo7722gmail.cms.StudentDashboard.Calendar.ViewAcademicCalendarToStudents;
import com.pradipsahoo7722gmail.cms.StudentDashboard.Notice.StudentViewNoticeRecView;
import com.pradipsahoo7722gmail.cms.StudentDashboard.Routine.ViewStudentRoutine;
import com.pradipsahoo7722gmail.cms.StudentDashboard.StudentAttrndance.SelectUserSubject;
import com.pradipsahoo7722gmail.cms.StudentDashboard.Syllabus.SelectUserStream;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static java.io.File.createTempFile;

public class StudentDashboard extends AppCompatActivity {

    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    CardView syllabus, calendar, notice;

    TextView firstName, middleName, lastName, email, firstnameInDrawer, middlenameInDrawer, lastnameInDrawer, EmailInDrawer;

    CircleImageView profileImage, profileImageUploadBtn, profileImageInDrawer;

    private BottomSheetDialog bottomSheetDialog;
    private ProgressDialog progressDialog;

    private final int GALLERY_OPEN_REQ = 101;
    private final int CAMERA_OPEN_REQ = 102;
    private Uri imageUri;

    FirebaseDatabase database;
    FirebaseUser currentUser;
    String registeredUserID;
    DatabaseReference root;
    private Bitmap GlideBitmapDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        toolbar = findViewById(R.id.student_dashboard_toolbar);
//        setSupportActionBar(toolbar);

        nav = findViewById(R.id.navmenu);
        View header = nav.getHeaderView(0);
        profileImageInDrawer = header.findViewById(R.id.profile_image_navmenu);
        firstnameInDrawer = header.findViewById(R.id.profile_firstname_navmenu);
        middlenameInDrawer = header.findViewById(R.id.profile_middlename_navmenu);
        lastnameInDrawer = header.findViewById(R.id.profile_lastname_navmenu);
        EmailInDrawer = header.findViewById(R.id.profile_email_navmenu);

        drawerLayout = findViewById(R.id.drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(), StudentUser.class));
                        Toast.makeText(StudentDashboard.this, "user is here", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

//                    case R.id.password:
//                        Toast.makeText(StudentDashboard.this, "password is match", Toast.LENGTH_SHORT).show();
//                        drawerLayout.closeDrawer(GravityCompat.START);
//                        break;

                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), StudentLoginPage.class));
                        Toast.makeText(StudentDashboard.this, "Successfully logout", Toast.LENGTH_SHORT).show();
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                return true;
            }
        });

        syllabus = findViewById(R.id.syllabus_CardView);
        calendar = findViewById(R.id.student_calendar_CardView);
        notice = findViewById(R.id.notice_CardView);

        firstName = findViewById(R.id.studentDashboard_firstname);
        middleName = findViewById(R.id.studentDashboard_middlename);
        lastName = findViewById(R.id.studentDashboard_lastname);
        email = findViewById(R.id.studentDashboard_email);
        profileImage = findViewById(R.id.profile_image);
        profileImageUploadBtn = findViewById(R.id.profile_image_upload_btn);


        progressDialog = new ProgressDialog(this);

        database = FirebaseDatabase.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        registeredUserID = currentUser.getUid();
        root = database.getReference().child("AdminData");
//        registeredUserID = currentUser.getUid();

//        root = database.getReference().child("Student").child(registeredUserID);


        if (currentUser != null) {
            getInfo();
        }


//        root.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                String user_Type = snapshot.child("userType").getValue().toString();
//                if (user_Type.equals("Student")) {
//                    String firstname = snapshot.child("first_Name").getValue().toString();
//                    firstName.setText(firstname);
//                    String middlename = snapshot.child("middle_Name").getValue().toString();
//                    middleName.setText(middlename);
//                    String lastname = snapshot.child("last_Name").getValue().toString();
//                    lastName.setText(lastname);
//                    String emailID = snapshot.child("email_id").getValue().toString();
//                    email.setText(emailID);
////                    String profilePhoto = snapshot.child("profile_Image").getValue().toString();
////                    Glide.with(StudentDashboard.this).load(profilePhoto).into(profileImage);
////                    String profilePhotoInDrawer = snapshot.child("profile_Image").getValue().toString();
////                    Glide.with(StudentDashboard.this).load(profilePhotoInDrawer).into(profileImageInDrawer);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });

    }

    public void studentSyllabus(View view) {
        Intent intent = new Intent(getApplicationContext(), SelectUserStream.class);
        startActivity(intent);
    }

    public void academicCalendarViewToStudent(View view) {
        Intent intent = new Intent(getApplicationContext(), ViewAcademicCalendarToStudents.class);
        startActivity(intent);

    }

    public void studentViewNotice(View view) {
        Intent intent = new Intent(getApplicationContext(), StudentViewNoticeRecView.class);
        startActivity(intent);
    }

    public void selectImage(View view) {
        showBottomSheetPickerPhoto();
    }

    private void showBottomSheetPickerPhoto() {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_picker, null);

        ((View) view.findViewById(R.id.open_gallery)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
                bottomSheetDialog.dismiss();
            }
        });

        ((View) view.findViewById(R.id.open_camera)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkCameraPermission();
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);

        bottomSheetDialog.show();

        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                bottomSheetDialog = null;
            }
        });
    }

    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_OPEN_REQ);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    222);
        } else {
            openCamera();
        }
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String timeStamp = new SimpleDateFormat("yyyyMMDD_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + ".jpg";

        try {
            File file = createTempFile(imageFileName, String.valueOf(getExternalFilesDir(Environment.DIRECTORY_PICTURES)));
            imageUri = FileProvider.getUriForFile(this, "com.pradipsahoo7722gmail.cms.provider", file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            intent.putExtra("listPhotoName", imageFileName);
            startActivityForResult(intent, 440);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_OPEN_REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_OPEN_REQ && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            imageUri = data.getData();

            imageUploadToFirebase();
        }

        if (requestCode == 440 && resultCode == RESULT_OK) {

            imageUploadToFirebase();
        }

    }

    private String getFileExtention(Uri imageUri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }

    private void imageUploadToFirebase() {
        if (imageUri != null) {
            progressDialog.setMessage("Uploading...");
            progressDialog.show();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                    .child("StudentProfileImage/" + UUID.randomUUID().toString() + "." + getFileExtention(imageUri));

            storageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;
                            Uri downloadUrl = uriTask.getResult();

                            final String sdownload_url = String.valueOf(downloadUrl);

                            HashMap<String, Object> image = new HashMap<>();
                            image.put("profile_Image", sdownload_url);

                            progressDialog.dismiss();
                            database.getReference().child("Student").child(currentUser.getUid())
                                    .updateChildren(image)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(StudentDashboard.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                                            getInfo();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(StudentDashboard.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }
    }

    private void getInfo() {

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot adminData : snapshot.getChildren()) {
                    String adminID = adminData.getKey();

                    Query query = root.child(adminID).child("Student");
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for (DataSnapshot userData : snapshot.getChildren()) {
                                String userKey = userData.getKey();

                                if (userKey.equals(registeredUserID)) {
                                    Query newQuery = root.child(adminID).child("Student").child(userKey);
                                    newQuery.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            String user_Type = snapshot.child("userType").getValue().toString();
                                            if (user_Type.equals("Student")) {
                                                String firstname = snapshot.child("first_Name").getValue().toString();
                                                String middlename = snapshot.child("middle_Name").getValue().toString();
                                                String lastname = snapshot.child("last_Name").getValue().toString();
                                                String emailID = snapshot.child("email_id").getValue().toString();
//                                                String profilePhoto = Objects.requireNonNull(snapshot.child("profile_Image").getValue()).toString();

                                                firstName.setText(firstname);
                                                firstnameInDrawer.setText(firstname);
                                                middleName.setText(middlename);
                                                middlenameInDrawer.setText(middlename);
                                                lastName.setText(lastname);
                                                lastnameInDrawer.setText(lastname);
                                                email.setText(emailID);
                                                EmailInDrawer.setText(emailID);
//                                                Glide.with(StudentDashboard.this).load(profilePhoto).into(profileImage);
//                                                Glide.with(StudentDashboard.this).load(profilePhoto).into(profileImageInDrawer);
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    public void StudentAttendance(View view) {
        startActivity(new Intent(getApplicationContext(), SelectUserSubject.class));
    }

    public void StudentViewRoutine(View view) {
        startActivity(new Intent(getApplicationContext(), ViewStudentRoutine.class));
    }

//    public void viewImage(View view) {
//        Common.IMAGE_BITMAP = ((GlideBitmapDrawable))
//    }
}