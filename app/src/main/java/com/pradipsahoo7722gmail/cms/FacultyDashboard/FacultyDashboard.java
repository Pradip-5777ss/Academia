package com.pradipsahoo7722gmail.cms.FacultyDashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

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
import com.pradipsahoo7722gmail.cms.FacultyDashboard.AcademicCalendar.ViewAcademicCalendarToFaculty;
import com.pradipsahoo7722gmail.cms.FacultyDashboard.AcademicNotice.FacultyViewAcademicNotice;
import com.pradipsahoo7722gmail.cms.FacultyDashboard.Attendance.AttendanceTaking;
import com.pradipsahoo7722gmail.cms.FacultyDashboard.FacultyRoutine.FacultyViewRoutine;
import com.pradipsahoo7722gmail.cms.FacultyDashboard.FacultySyllabus.SelectFacultyStream;
import com.pradipsahoo7722gmail.cms.LoginUser.LoginScreen;
import com.pradipsahoo7722gmail.cms.R;
import com.pradipsahoo7722gmail.cms.SelectUserLogin.FacultyLoginPage;
import com.pradipsahoo7722gmail.cms.StudentDashboard.StudentUser;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class FacultyDashboard extends AppCompatActivity {

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
    private Uri imageUri;

    FirebaseDatabase database;
    FirebaseUser currentUser;
    String registeredUserID;
    DatabaseReference root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_dashboard);

        toolbar = findViewById(R.id.faculty_dashboard_toolbar);
//        setSupportActionBar(toolbar);

        nav = findViewById(R.id.faculty_navmenu);
        View header = nav.getHeaderView(0);
        profileImageInDrawer = header.findViewById(R.id.profile_image_navmenu);
        firstnameInDrawer = header.findViewById(R.id.profile_firstname_navmenu);
        middlenameInDrawer = header.findViewById(R.id.profile_middlename_navmenu);
        lastnameInDrawer = header.findViewById(R.id.profile_lastname_navmenu);
        EmailInDrawer = header.findViewById(R.id.profile_email_navmenu);

        drawerLayout = findViewById(R.id.faculty_drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.user:
                        startActivity(new Intent(getApplicationContext(), FacultyUser.class));
                        Toast.makeText(FacultyDashboard.this, "user is here", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

//                    case R.id.password:
//                        Toast.makeText(FacultyDashboard.this, "password is match", Toast.LENGTH_SHORT).show();
//                        drawerLayout.closeDrawer(GravityCompat.START);
//                        break;

                    case R.id.logout:
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), FacultyLoginPage.class));
                        Toast.makeText(FacultyDashboard.this, "Successfully logout", Toast.LENGTH_SHORT).show();
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                return true;
            }
        });

        syllabus = findViewById(R.id.faculty_syllabus_CardView);
        calendar = findViewById(R.id.faculty_calendar_CardView);
        notice = findViewById(R.id.faculty_notice_CardView);

        firstName = findViewById(R.id.facultyDashboard_firstname);
        middleName = findViewById(R.id.facultyDashboard_middlename);
        lastName = findViewById(R.id.facultyDashboard_lastname);
        email = findViewById(R.id.facultyDashboard_email);
        profileImage = findViewById(R.id.faculty_profile_image);
        profileImageUploadBtn = findViewById(R.id.faculty_profile_image_upload_btn);

        profileImage.setVisibility(View.VISIBLE);
        progressDialog = new ProgressDialog(this);

        database = FirebaseDatabase.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        registeredUserID = currentUser.getUid();
        root = database.getReference().child("AdminData");

        if (currentUser != null) {
            getInfo();
        }

    }

    // get info of the faculty
    private void getInfo() {

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot adminData : snapshot.getChildren()) {
                    String adminID = adminData.getKey();

                    Query query = root.child(adminID).child("Faculty");
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for (DataSnapshot userData : snapshot.getChildren()) {
                                String userKey = userData.getKey();

                                if (userKey.equals(registeredUserID)) {
                                    Query newQuery = root.child(adminID).child("Faculty").child(userKey);
                                    newQuery.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            String user_Type = snapshot.child("userType").getValue().toString();
                                            if (user_Type.equals("Faculty")) {
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
//        database.getReference().child("Faculty").child(currentUser.getUid())
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                        String user_Type = snapshot.child("userType").getValue().toString();
//                        if (user_Type.equals("Faculty")) {
//                            String firstname = snapshot.child("first_Name").getValue().toString();
//                            String middlename = snapshot.child("middle_Name").getValue().toString();
//                            String lastname = snapshot.child("last_Name").getValue().toString();
//                            String emailID = snapshot.child("email_id").getValue().toString();
//                            String profilePhoto = Objects.requireNonNull(snapshot.child("profile_Image").getValue()).toString();
//
//                            firstName.setText(firstname);
//                            firstnameInDrawer.setText(firstname);
//                            middleName.setText(middlename);
//                            middlenameInDrawer.setText(middlename);
//                            lastName.setText(lastname);
//                            lastnameInDrawer.setText(lastname);
//                            email.setText(emailID);
//                            EmailInDrawer.setText(emailID);
//                            Glide.with(FacultyDashboard.this).load(profilePhoto).into(profileImage);
//                            Glide.with(FacultyDashboard.this).load(profilePhoto).into(profileImageInDrawer);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//                    }
//                });

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
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
//                profileImage.setImageBitmap(bitmap);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

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
                    .child("FacultyProfileImage/" + UUID.randomUUID().toString() + "." + getFileExtention(imageUri));

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
                            database.getReference().child("Faculty").child(currentUser.getUid())
                                    .updateChildren(image)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(FacultyDashboard.this, "Upload Successfully", Toast.LENGTH_SHORT).show();
                                            getInfo();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Toast.makeText(FacultyDashboard.this, "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }
    }

    /**
     * Faculty academic Calendar view button
     * It take @param view
     */
    public void academicCalendarViewToFaculty(View view) {
        startActivity(new Intent(getApplicationContext(), ViewAcademicCalendarToFaculty.class));
    }

    /**
     * Faculty notice view button
     * It take @param view
     */
    public void facultyViewNotice(View view) {
        startActivity(new Intent(getApplicationContext(), FacultyViewAcademicNotice.class));
    }

    public void facultySyllabus(View view) {
        startActivity(new Intent(getApplicationContext(), SelectFacultyStream.class));
    }

    public void facultyAttendancetaking(View view) {
        startActivity(new Intent(getApplicationContext(), AttendanceTaking.class));
    }

    public void facultyViewRoutine(View view) {
        startActivity(new Intent(getApplicationContext(), FacultyViewRoutine.class));
    }
}