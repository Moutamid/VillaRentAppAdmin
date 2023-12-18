package com.moutimid.vellarentappadmin.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.moutimid.vellarentappadmin.R;
import com.moutimid.vellarentappadmin.model.Owner;

import java.util.Objects;

public class AddOwnerActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_GALLERY = 111;
    ImageView profile_pic;
    Uri image_profile_str = null;

    private EditText editTextName, editTextPhone, editTextEmail, editTextPassword;
    private Button btnAddOwner;

    private FirebaseAuth mAuth;
    private DatabaseReference ownersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_owner);
        profile_pic = findViewById(R.id.image_view);

        mAuth = FirebaseAuth.getInstance();

        ownersRef = FirebaseDatabase.getInstance().getReference("owners");

        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        btnAddOwner = findViewById(R.id.btnAddOwner);
        btnAddOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOwner();
            }
        });
    }

    private void addOwner() {
        String name = editTextName.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            Dialog lodingbar = new Dialog(AddOwnerActivity.this);
                            lodingbar.setContentView(R.layout.loading);
                            Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
                            lodingbar.setCancelable(false);
                            lodingbar.show();
                            String filePathName = "villas/";
                            final String timestamp = "" + System.currentTimeMillis();
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathName + timestamp);
                            UploadTask urlTask = storageReference.putFile(image_profile_str);
                            Task<Uri> uriTask = urlTask.continueWithTask(task1 -> {
                                if (!task1.isSuccessful()) {
                                    Toast.makeText(AddOwnerActivity.this, task1.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                return storageReference.getDownloadUrl();
                            }).addOnCompleteListener(task2 -> {
                                if (task2.isSuccessful()) {
                                    Uri downloadImageUri = task2.getResult();
                                    if (downloadImageUri != null) {
                                        // Create Owner object and push to Firebase Realtime Database
                                        Owner owner = new Owner(name, phone, email, downloadImageUri.toString(), user.getUid());
                                        ownersRef.push().setValue(owner);

                                        Toast.makeText(AddOwnerActivity.this, "Owner added successfully", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                } else {
                                    Toast.makeText(AddOwnerActivity.this, "Failed to add owner", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_GALLERY && resultCode == RESULT_OK) {
            image_profile_str = data.getData();
            profile_pic.setImageURI(image_profile_str);
            profile_pic.setVisibility(View.VISIBLE);
        }
    }

    public void profile_image(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_GALLERY);
    }

    public void BackPress(View view) {
        onBackPressed();
    }
}
