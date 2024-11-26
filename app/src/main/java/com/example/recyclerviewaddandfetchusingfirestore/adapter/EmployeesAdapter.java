package com.example.recyclerviewaddandfetchusingfirestore.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recyclerviewaddandfetchusingfirestore.R;
import com.example.recyclerviewaddandfetchusingfirestore.employee.Employee;

import java.util.List;

public class EmployeesAdapter extends RecyclerView.Adapter<EmployeesAdapter.EmployeeViewHolder> {

    // Employees page adapter
    List<Employee> employeeList;

    public EmployeesAdapter(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_employees_card, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employeeList.get(position);

        holder.tvName.setText(employee.getName());
        holder.tvDepartment.setText(employee.getDepartment());
        holder.tvEmail.setText(employee.getEmail());
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvDepartment, tvEmail;

        public EmployeeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.txt_name); // Corrected ID
            tvDepartment = itemView.findViewById(R.id.txt_department); // Corrected ID
            tvEmail = itemView.findViewById(R.id.txt_email); // Corrected ID
        }
    }
}
