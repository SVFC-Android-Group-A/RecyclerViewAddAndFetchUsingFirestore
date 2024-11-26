package com.example.recyclerviewaddandfetchusingfirestore.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewaddandfetchusingfirestore.R;
import com.example.recyclerviewaddandfetchusingfirestore.university.University;

import java.util.List;

public class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.UniversityViewHolder> {

    // This is the adapter for the university page
    private final List<University> universityList;

    public UniversityAdapter(List<University> universityList) {
        this.universityList = universityList;
    }

    @NonNull
    @Override
    public UniversityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_university_card, parent, false);
        return new UniversityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UniversityViewHolder holder, int position) {
        University university = universityList.get(position);

        holder.tvName.setText(university.getName());
        holder.tvType.setText(university.getType());
        holder.tvWebsite.setText(university.getWebsite());
    }

    @Override
    public int getItemCount() {
        return universityList.size();
    }

    public static class UniversityViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvType, tvWebsite;

        public UniversityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.txt_name);
            tvType = itemView.findViewById(R.id.txt_type);
            tvWebsite = itemView.findViewById(R.id.txt_website);
        }
    }
}