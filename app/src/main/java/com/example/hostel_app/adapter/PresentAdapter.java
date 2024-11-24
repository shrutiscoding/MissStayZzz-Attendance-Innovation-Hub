package com.example.hostel_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hostel_app.R;

import java.util.ArrayList;

public class PresentAdapter extends RecyclerView.Adapter<PresentAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<Present> presentArrayList;
    private ArrayList<Present> originalPresentList;
    Context context;

    public PresentAdapter(Context ctx, ArrayList<Present> presentArrayList) {

        inflater = LayoutInflater.from(ctx);
        this.presentArrayList = presentArrayList;
        this.originalPresentList = new ArrayList<>(presentArrayList);
    }

    @Override
    public PresentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.present, parent, false);
        PresentAdapter.MyViewHolder holder = new PresentAdapter.MyViewHolder(view);
        return holder;
    }



    public void filterList(ArrayList<Present> filteredList) {
        presentArrayList = filteredList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override

    public void onBindViewHolder(PresentAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String enroll = presentArrayList.get(position).enroll;
        holder.txtEn.setText(enroll);


        String name = presentArrayList.get(position).name;
        holder.txtName.setText(name);

        String hostel = presentArrayList.get(position).hostel;
        holder.txthostel.setText(hostel);

        String department = presentArrayList.get(position).department;
        holder.txtdept.setText(department);
        String room_no = presentArrayList.get(position).room_no;
        holder.txtroom.setText(room_no);
        String year = presentArrayList.get(position).year;
        holder.txtyear.setText(year);


    }

    @Override
    public int getItemCount() {
        return presentArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtEn, txtName, txthostel,txtdept,txtroom,txtyear;



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
