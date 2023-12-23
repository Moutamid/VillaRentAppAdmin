package com.moutimid.vellarentappadmin.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutimid.vellarentappadmin.R;
import com.moutimid.vellarentappadmin.adapter.OwnVillaAdapter;
import com.moutimid.vellarentappadmin.adapter.PendingVillaAdapter;
import com.moutimid.vellarentappadmin.helper.Config;
import com.moutimid.vellarentappadmin.helper.Constants;
import com.moutimid.vellarentappadmin.model.HouseRules;
import com.moutimid.vellarentappadmin.model.PropertyAmenities;
import com.moutimid.vellarentappadmin.model.Villa;

import java.util.ArrayList;
import java.util.List;

public class PendingVillaActivity extends AppCompatActivity {

    PendingVillaAdapter ownVillaAdapter;
    RecyclerView content_rcv1;
    public List<Villa> productModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_villa);
        // Fetching views from XML file
        content_rcv1 = findViewById(R.id.content_rcv1);
        content_rcv1.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        ownVillaAdapter = new PendingVillaAdapter(this, productModelList);
        content_rcv1.setAdapter(ownVillaAdapter);
        if (Config.isNetworkAvailable(this)) {
            getRecommendedProducts();
        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }

    }
    private void getRecommendedProducts() {
//        Config.showProgressDialog(getContext());
        Constants.databaseReference().child(Config.villa).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productModelList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Villa villaModel = ds.getValue(Villa.class);
                    Log.d("dataa", villaModel.isBills_included() + "");
                    if (!villaModel.verified) {
                        DataSnapshot propertyAmenities1 = ds.child("PropertyAmenities");
                        DataSnapshot houseRules1 = ds.child("HouseRules");
                        PropertyAmenities propertyAmenities = propertyAmenities1.getValue(PropertyAmenities.class);
                        HouseRules houseRules = houseRules1.getValue(HouseRules.class);
                        villaModel.setPropertyAmenities(propertyAmenities);
                        villaModel.setHouseRules(houseRules);

                        productModelList.add(villaModel);
                    }
                    ownVillaAdapter.notifyDataSetChanged();
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });


    }
    public void BackPress(View view) {
        onBackPressed();
    }
}