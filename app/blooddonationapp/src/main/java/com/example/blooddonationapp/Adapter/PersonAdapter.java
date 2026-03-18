package com.example.blooddonationapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonationapp.DonorProfileActivity;
import com.example.blooddonationapp.R;
import com.example.blooddonationapp.UpdateActivity;
import com.example.blooddonationapp.Database.DatabaseHelper;
import com.example.blooddonationapp.Model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<Person> donorList;
    private List<Person> donorListFull;
    private DatabaseHelper db;

    public PersonAdapter(Context context, List<Person> donorList) {
        this.context = context;
        this.donorList = donorList;
        this.donorListFull = new ArrayList<>(donorList);
        db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_person, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Person person = donorList.get(position);

        holder.tvName.setText(person.getName());
        holder.tvBlood.setText("Blood Group: " + person.getBloodGroup());
        holder.tvPhone.setText("Phone: " + person.getPhone());

        // Click → Update
        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(context, DonorProfileActivity.class);

            intent.putExtra("id", person.getId());
            intent.putExtra("name", person.getName());
            intent.putExtra("blood", person.getBloodGroup());
            intent.putExtra("phone", person.getPhone());
            intent.putExtra("location", person.getArea());

            context.startActivity(intent);

        });

        // Long Click → Delete
        holder.itemView.setOnLongClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Donor")
                    .setMessage("Are you sure you want to delete this donor?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        db.deleteDonor(person.getId());
                        Toast.makeText(context, "Donor Deleted", Toast.LENGTH_SHORT).show();
                        donorList.remove(position);
                        donorListFull.remove(person);
                        notifyItemRemoved(position);
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return donorList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvBlood, tvPhone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvBlood = itemView.findViewById(R.id.tvBlood);
            tvPhone = itemView.findViewById(R.id.tvPhone);
        }
    }

    // SEARCH FILTER
    @Override
    public Filter getFilter() {
        return donorFilter;
    }

    private Filter donorFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Person> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(donorListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Person person : donorListFull) {
                    if (person.getBloodGroup().toLowerCase().contains(filterPattern)) {
                        filteredList.add(person);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            donorList.clear();
            donorList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    public void updateList(List<Person> newList){
        donorList.clear();
        donorList.addAll(newList);

        donorListFull.clear(); // important for search
        donorListFull.addAll(newList);

        notifyDataSetChanged();
    }
    // ADD THIS METHOD INSIDE CLASS

    public void filterByBlood(String bloodGroup) {
        List<Person> filteredList = new ArrayList<>();

        for (Person person : donorListFull) {
            if (person.getBloodGroup().equalsIgnoreCase(bloodGroup)) {
                filteredList.add(person);
            }
        }

        donorList.clear();
        donorList.addAll(filteredList);
        notifyDataSetChanged();
    }
}