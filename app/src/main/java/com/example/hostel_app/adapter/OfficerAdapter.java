package com.example.hostel_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hostel_app.DBClass;
import com.example.hostel_app.R;
import com.example.hostel_app.UpdateOfficerActivity;
import com.example.hostel_app.VolleySingleton;

import java.util.ArrayList;

public class OfficerAdapter extends RecyclerView.Adapter<OfficerAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Officer> officerArrayList;
    private ArrayList<Officer> originalOfficerList;
    Context context;

    public OfficerAdapter(Context ctx, ArrayList<Officer> officerArrayList) {

        inflater = LayoutInflater.from(ctx);
        this.officerArrayList = officerArrayList;
        this.originalOfficerList = new ArrayList<>(officerArrayList);
    }

    @Override
    public OfficerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.warden, parent, false);
        OfficerAdapter.MyViewHolder holder = new OfficerAdapter.MyViewHolder(view);
        return holder;
    }
    public void filterList(ArrayList<Officer> filteredList) {
        officerArrayList = filteredList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(OfficerAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String name = officerArrayList.get(position).r_name;
        holder.txtName.setText(name);

        String email = officerArrayList.get(position).email;
        holder.txtEmail.setText(email);




    }

    @Override
    public int getItemCount() {
        return officerArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtEmail;
        ImageButton btnUpdate, btnDelete;



        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtEmail = (TextView) view.findViewById(R.id.txtEmail);
            btnUpdate = (ImageButton) view.findViewById(R.id.btnUpdate);
            btnDelete = (ImageButton) view.findViewById(R.id.btnDelete);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        // Open UpdateOutpassActivity with relevant data
                        Intent intent = new Intent(context, UpdateOfficerActivity.class);
                        intent.putExtra("r_name", officerArrayList.get(getAdapterPosition()).r_name);
                        intent.putExtra("email", officerArrayList.get(getAdapterPosition()).email);
                        intent.putExtra("pass", officerArrayList.get(getAdapterPosition()).pass);

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
                        String r_nameToDelete = officerArrayList.get(getAdapterPosition()).r_name;

                        // Call the method to handle the delete operation
                        deleteWarden(r_nameToDelete, getAdapterPosition());
                    } catch (Exception ex) {
                        Log.e("Ex", "onClick: " + ex);
                    }
                }
            });


        }

        private void deleteWarden(String r_nameToDelete, final int position) {
            String url = DBClass.urlOfficerDelete + "?r_name=" + r_nameToDelete;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Handle the response from the server after successful deletion
                            // Remove the item from the ArrayList and notify the adapter
                            officerArrayList.remove(position);
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