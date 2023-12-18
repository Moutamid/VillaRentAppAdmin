package com.moutimid.vellarentappadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.moutimid.vellarentappadmin.R;
import com.moutimid.vellarentappadmin.activities.AddVillaActivity;
import com.moutimid.vellarentappadmin.model.Owner;

import java.util.List;

public class OwnerAdapter extends RecyclerView.Adapter<OwnerAdapter.OwnerViewHolder> {

    private List<Owner> owners;
    Context context;

    public void setOwners(List<Owner> owners, Context context) {
        this.owners = owners;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OwnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_owner, parent, false);
        return new OwnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OwnerViewHolder holder, int position) {
        Owner owner = owners.get(position);
        holder.bind(owner);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, AddVillaActivity.class);
                Stash.put("id", owner.getOwnerId());
                Stash.put("name", owner.getName());
                Stash.put("image", owner.getImage());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return owners != null ? owners.size() : 0;
    }

    static class OwnerViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewName;
        private final TextView textViewPhone;
        private final TextView textViewEmail;

        public OwnerViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
        }

        public void bind(Owner owner) {
            textViewName.setText("Name: " + owner.getName());
            textViewPhone.setText("Phone: " + owner.getPhone());
            textViewEmail.setText("Email: " + owner.getEmail());
        }
    }
}
