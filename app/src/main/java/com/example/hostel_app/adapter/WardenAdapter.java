package com.example.hostel_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hostel_app.DBClass;
import com.example.hostel_app.R;
import com.example.hostel_app.UpdateWardenActivity;
import com.example.hostel_app.VolleySingleton;

import java.util.ArrayList;

public class WardenAdapter extends RecyclerView.Adapter<WardenAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Warden> wardenArrayList;
    private ArrayList<Warden> originalWardenList;
    Context context;

    public WardenAdapter(Context ctx, ArrayList<Warden> wardenArrayList){

        inflater = LayoutInflater.from(ctx);
        this.wardenArrayList = wardenArrayList;
        this.originalWardenList = new ArrayList<>(wardenArrayList);
    }
    public void filterList(ArrayList<Warden> filteredList) {
        wardenArrayList = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public WardenAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.warden, parent, false);
        WardenAdapter.MyViewHolder holder = new WardenAdapter.MyViewHolder(view);
        return holder;
    }


    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(WardenAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String name = wardenArrayList.get(position).w_name;
        holder.txtName.setText(name);

        String email = wardenArrayList.get(position).email;
        holder.txtEmail.setText(email);

        String designation = wardenArrayList.get(position).designation;
        holder.txtDesignation.setText(designation);


    }

    @Override
    public int getItemCount() {
        return wardenArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtDesignation, txtEmail;
        ImageView imageView;
        ImageButton btnUpdate, btnDelete;


        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtDesignation = (TextView) view.findViewById(R.id.txtDesignation);
            txtEmail = (TextView) view.findViewById(R.id.txtEmail);
            btnUpdate = (ImageButton) view.findViewById(R.id.btnUpdate);
            btnDelete = (ImageButton) view.findViewById(R.id.btnDelete);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        // Open UpdateOutpassActivity with relevant data
                        Intent intent = new Intent(context, UpdateWardenActivity.class);
                        intent.putExtra("w_name", wardenArrayList.get(getAdapterPosition()).w_name);
                        intent.putExtra("designation", wardenArrayList.get(getAdapterPosition()).designation);
                        intent.putExtra("pass", wardenArrayList.get(getAdapterPosition()).pass);
                        intent.putExtra("email", wardenArrayList.get(getAdapterPosition()).email);
                        intent.putExtra("department", wardenArrayList.get(getAdapterPosition()).department);
                        intent.putExtra("hostel", wardenArrayList.get(getAdapterPosition()).hostel);
                        intent.putExtra("br_year", wardenArrayList.get(getAdapterPosition()).br_year);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } catch (Exception ex) {
                        Log.e("Ex", "onClick: " + ex);
                    }
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        String w_nameToDelete = wardenArrayList.get(getAdapterPosition()).w_name;

                        // Call the method to handle the delete operation
                        deleteWarden(w_nameToDelete, getAdapterPosition());
                    } catch (Exception ex) {
                        Log.e("Ex", "onClick: " + ex);
                    }
                }
            });


        }

        private void deleteWarden(String w_nameToDelete, final int position) {
            String url = DBClass.urlWardenDelete + "?w_name=" + w_nameToDelete;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Handle the response from the server after successful deletion
                            // Remove the item from the ArrayList and notify the adapter
                            wardenArrayList.remove(position);
                            notifyItemRemoved(position);
                            Log.d("DeleteSuccess", "Response: " + response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle the error response from the server
                            Log.e("Volley Error", "Error: " + error.getMessage());
                            // You may want to show an error message to the user
                        }
                    });

            // Add the request to the VolleySingleton queue
            VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);

            // Log statements for debugging
            Log.d("DeleteRequest", "URL: " + url);
            Log.d("DeleteRequest", "Position: " + position);
        }
    }}