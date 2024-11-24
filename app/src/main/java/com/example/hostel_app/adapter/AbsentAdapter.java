package com.example.hostel_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel_app.R;

import java.util.ArrayList;

public class AbsentAdapter extends RecyclerView.Adapter<AbsentAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Absent> absentArrayList;
    private ArrayList<Absent> originalAbsentList;
    Context context;

    public AbsentAdapter(Context ctx, ArrayList<Absent> absentArrayList) {

        inflater = LayoutInflater.from(ctx);
        this.absentArrayList = absentArrayList;
        this.originalAbsentList = new ArrayList<>(absentArrayList);
    }

    @Override
    public AbsentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.absent, parent, false);
        AbsentAdapter.MyViewHolder holder = new AbsentAdapter.MyViewHolder(view);
        return holder;
    }
    public void filterList(ArrayList<Absent> filteredList) {
        absentArrayList = filteredList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(AbsentAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String enroll = absentArrayList.get(position).enroll;
        holder.txtEn.setText(enroll);

        String name = absentArrayList.get(position).name;
        holder.txtName.setText(name);

        String hostel = absentArrayList.get(position).hostel;
        holder.txthostel.setText(hostel);

        String department = absentArrayList.get(position).department;
        holder.txtdept.setText(department);
        String room_no = absentArrayList.get(position).room_no;
        holder.txtroom.setText(room_no);
        String year = absentArrayList.get(position).year;
        holder.txtyear.setText(year);


    }

    @Override
    public int getItemCount() {
        return absentArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView  txtEn, txtName, txthostel,txtdept,txtroom,txtyear;



        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            txtEn = (TextView) view.findViewById(R.id.txtEnroll);
            txtName = (TextView) view.findViewById(R.id.txtName);
            txthostel = (TextView) view.findViewById(R.id.txthostel);
            txtroom = (TextView) view.findViewById(R.id.txtroom);
            txtdept = (TextView) view.findViewById(R.id.txtdepartment);
            txtyear = (TextView) view.findViewById(R.id.txtyear);


        }
    }
}