package com.example.hostel_app.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hostel_app.DBClass;
import com.example.hostel_app.R;
import com.example.hostel_app.VolleySingleton;
import com.example.hostel_app.warden.UpdateOutpassActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class OfficerOutpassAdapter extends RecyclerView.Adapter<OfficerOutpassAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private ArrayList<Outpass> outpassArrayList;
    private ArrayList<Outpass> originalOutpassList;
    TextView txtElapsedTime;

    Context context;


    public OfficerOutpassAdapter(Context ctx, ArrayList<Outpass> outpassArrayList){

        inflater = LayoutInflater.from(ctx);
        this.outpassArrayList = outpassArrayList;
        this.originalOutpassList = new ArrayList<>(outpassArrayList);
        this.context=ctx;

    }

    public void filterList(ArrayList<Outpass> filteredList) {
        outpassArrayList = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public OfficerOutpassAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.outpass, parent, false);
        OfficerOutpassAdapter.MyViewHolder holder = new OfficerOutpassAdapter.MyViewHolder(view);
        return holder;
    }


    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(OfficerOutpassAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String enroll = outpassArrayList.get(position).enroll;
        holder.txtEn.setText(enroll);

        String name = outpassArrayList.get(position).name;
        holder.txtName.setText(name);

        String pmob = outpassArrayList.get(position).pmob;
        holder.txtpmob.setText(pmob);

        String address = outpassArrayList.get(position).address;
        holder.txtaddress.setText(address);

        String reason = outpassArrayList.get(position).reason;
        holder.txtreason.setText(reason);

        String indate = outpassArrayList.get(position).indate;
        holder.txtindate.setText(indate);
        String intime = outpassArrayList.get(position).intime;
        holder.txtintime.setText(intime);
        String outdate = outpassArrayList.get(position).outdate;
        holder.txtDepartment.setText(outpassArrayList.get(position).getDepartment());
        holder.txtYear.setText(outpassArrayList.get(position).getYear());
        holder.txtHostel.setText(outpassArrayList.get(position).getHostel());

        // Get today's date
        String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Calendar.getInstance().getTime());
        int dateComparisonResult = indate.compareTo(todayDate);
        int datec=outdate.compareTo(todayDate);
        // Compare indate with today's date



        if (datec <=  0) {
            // Date is before today (color it red)
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorToday));
        } else if (datec > 0) {
            // Date is after today (color it blue)
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorFuture));
        } else {
            // Date is today (color it red)
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorToday));
        }



    }



    @Override
    public int getItemCount() {
        return outpassArrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtEn, txtName, txtaddress, txtreason, txtindate, txtintime,txtpmob;
        ImageButton btnUpdate, btnDelete;
        LinearLayout layoutOutpass;
        TextView txtDepartment, txtYear, txtHostel;
        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            txtEn = (TextView) view.findViewById(R.id.txtEnroll);
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtpmob=(TextView)view.findViewById(R.id.etxtpmob);
            txtaddress = (TextView) view.findViewById(R.id.txtaddress);
            txtreason = (TextView) view.findViewById(R.id.txtreason);
            txtindate = (TextView) view.findViewById(R.id.txtindate);
            txtElapsedTime = (TextView) view.findViewById(R.id.txtElapsedTime);
            txtintime = (TextView) view.findViewById(R.id.txtintime);
            txtDepartment = (TextView) view.findViewById(R.id.txtDepartment);  // Initialize TextViews
            txtYear = (TextView) view.findViewById(R.id.txtYear);
            txtHostel = (TextView) view.findViewById(R.id.txtHostel);

            layoutOutpass = (LinearLayout) view.findViewById(R.id.layoutOutpass);
            btnUpdate = (ImageButton) view.findViewById(R.id.btnUpdate);
            btnDelete = (ImageButton) view.findViewById(R.id.btnDelete);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        // Open UpdateOutpassActivity with relevant data
                        Intent intent = new Intent(context, com.example.hostel_app.officer.UpdateOutpassActivity.class);
                        intent.putExtra("enroll", outpassArrayList.get(getAdapterPosition()).enroll);
                        intent.putExtra("name", outpassArrayList.get(getAdapterPosition()).name);
                        intent.putExtra("pmob", outpassArrayList.get(getAdapterPosition()).pmob);
                        intent.putExtra("reason", outpassArrayList.get(getAdapterPosition()).reason);
                        intent.putExtra("address", outpassArrayList.get(getAdapterPosition()).address);
                        intent.putExtra("outdate", outpassArrayList.get(getAdapterPosition()).outdate);
                        intent.putExtra("outtime", outpassArrayList.get(getAdapterPosition()).outtime);
                        intent.putExtra("indate", outpassArrayList.get(getAdapterPosition()).indate);
                        intent.putExtra("intime", outpassArrayList.get(getAdapterPosition()).intime);
                        intent.putExtra("communication", outpassArrayList.get(getAdapterPosition()).communication);
                        intent.putExtra("remark", outpassArrayList.get(getAdapterPosition()).remark);
                        // Add other data you want to pass to UpdateOutpassActivity
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
                        String enrollToDelete = outpassArrayList.get(getAdapterPosition()).enroll;

                        // Call the method to handle the delete operation
                        deleteOutpass(enrollToDelete, getAdapterPosition());
                    } catch (Exception ex) {
                        Log.e("Ex", "onClick: " + ex);
                    }
                }
            });


        }

        private void deleteOutpass(String enrollToDelete, final int position) {
            String url = DBClass.urlOutpassDelete + "?enroll=" + enrollToDelete;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Handle the response from the server after successful deletion
                            // Remove the item from the ArrayList and notify the adapter
                            outpassArrayList.remove(position);
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