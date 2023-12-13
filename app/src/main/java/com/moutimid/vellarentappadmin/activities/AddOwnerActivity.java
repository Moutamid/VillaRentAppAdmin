package com.moutimid.vellarentappadmin.activities;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutimid.vellarentappadmin.R;
import com.moutimid.vellarentappadmin.model.Owner;

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
                            // Create Owner object and push to Firebase Realtime Database
                            Owner owner = new Owner(name, phone, email, user.getUid());
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
