package com.moutimid.vellarentappadmin.activities;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutimid.vellarentappadmin.R;
import com.moutimid.vellarentappadmin.adapter.OwnerAdapter;
import com.moutimid.vellarentappadmin.model.Owner;

import java.util.ArrayList;
import java.util.List;

public class OwnersListActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOwners;
    private OwnerAdapter ownerAdapter;

    private DatabaseReference ownersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owners_list);

        ownersRef = FirebaseDatabase.getInstance().getReference("owners");

        recyclerViewOwners = findViewById(R.id.recyclerViewOwners);
        recyclerViewOwners.setLayoutManager(new LinearLayoutManager(this));

        // Initialize and set up the adapter
        ownerAdapter = new OwnerAdapter();
        recyclerViewOwners.setAdapter(ownerAdapter);

        // Retrieve owners from Firebase Realtime Database
        ownersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Owner> owners = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Owner owner = snapshot.getValue(Owner.class);
                    owners.add(owner);
                }
                ownerAdapter.setOwners(owners, OwnersListActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }
}
