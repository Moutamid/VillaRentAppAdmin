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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutimid.vellarentappadmin.R;
import com.moutimid.vellarentappadmin.model.Owner;

import java.util.Objects;

public class AddOwnerActivity extends AppCompatActivity {

    private EditText editTextName, editTextPhone, editTextEmail, editTextPassword;
    private Button btnAddOwner;

    private FirebaseAuth mAuth;
    private DatabaseReference ownersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_owner);

        mAuth = FirebaseAuth.getInstance();

        ownersRef = FirebaseDatabase.getInstance().getReference("RentApp").child("Owners");

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
        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please give complete details", Toast.LENGTH_SHORT).show();
        } else {
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

                                // Create Owner object and push to Firebase Realtime Database
                                            Owner owner = new Owner(name, phone, email, user.getUid());
                                            ownersRef.child(user.getUid()).setValue(owner);
                                            Toast.makeText(AddOwnerActivity.this, "Owner added successfully", Toast.LENGTH_SHORT).show();
                                            finish();

                            } else {
                                        Toast.makeText(AddOwnerActivity.this, "Failed to add owner", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    public void BackPress(View view) {
        onBackPressed();
    }
}
