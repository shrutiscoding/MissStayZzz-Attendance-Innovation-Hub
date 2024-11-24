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

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hostel_app.DBClass;
import com.example.hostel_app.R;
import com.example.hostel_app.warden.UpdateStudentActivity;
import com.example.hostel_app.VolleySingleton;

import java.util.ArrayList;

public class WardenStudentAdapter extends RecyclerView.Adapter<WardenStudentAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Student> studentArrayList;

    private ArrayList<Student> originalStudentList;
    Context context;



    public WardenStudentAdapter(Context ctx, ArrayList<Student> studentArrayList){

        inflater = LayoutInflater.from(ctx);
        this.studentArrayList = studentArrayList;
        this.originalStudentList = new ArrayList<>(studentArrayList);


    }

    @Override
    public WardenStudentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.student, parent, false);
        WardenStudentAdapter.MyViewHolder holder = new WardenStudentAdapter.MyViewHolder(view);
        return holder;


    }
    public void filterList(ArrayList<Student> filteredList) {
        studentArrayList = filteredList;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position)
    {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(WardenStudentAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        String enroll = studentArrayList.get(position).enroll;
        holder.txtEn.setText(enroll);
        String name = studentArrayList.get(position).s_name;
        holder.txtName.setText(name);

        String mob_no = studentArrayList.get(position).mob_no;
        holder.txtmob.setText(mob_no);

        String pname = studentArrayList.get(position).parent_name;
        holder.txtpname.setText(pname);

        String pmob = studentArrayList.get(position).parent_mob_no;
        holder.txtpmob.setText(pmob);




    }

    public int getItemCount() {
        return studentArrayList.size();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtEn,txtName, txtmob, txtpname,txtpmob;
        LinearLayout layoutExpert;
        ImageButton btnUpdate, btnDelete;
        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            txtEn = (TextView) view.findViewById(R.id.txtEnroll);
            txtName = (TextView) view.findViewById(R.id.txtName);
            txtmob = (TextView) view.findViewById(R.id.txtmob);
            txtpname = (TextView) view.findViewById(R.id.txtpname);
            txtpmob = (TextView) view.findViewById(R.id.txtpmob);
            btnUpdate = (ImageButton) view.findViewById(R.id.btnUpdate);
            btnDelete = (ImageButton) view.findViewById(R.id.btnDelete);
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        // Open UpdateOutpassActivity with relevant data
                        Intent intent = new Intent(context, UpdateStudentActivity.class);
                        intent.putExtra("enroll", studentArrayList.get(getAdapterPosition()).enroll);
                        intent.putExtra("name", studentArrayList.get(getAdapterPosition()).s_name);
                        intent.putExtra("mob_no", studentArrayList.get(getAdapterPosition()).mob_no);
                        intent.putExtra("parent_name", studentArrayList.get(getAdapterPosition()).parent_name);
                        intent.putExtra("parent_mob_no", studentArrayList.get(getAdapterPosition()).parent_mob_no);
                        intent.putExtra("address", studentArrayList.get(getAdapterPosition()).address);
                        intent.putExtra("city", studentArrayList.get(getAdapterPosition()).city);
                        intent.putExtra("district", studentArrayList.get(getAdapterPosition()).district);
                        intent.putExtra("hostel", studentArrayList.get(getAdapterPosition()).hostel);
                        intent.putExtra("room_no", studentArrayList.get(getAdapterPosition()).room_no);
                        intent.putExtra("department", studentArrayList.get(getAdapterPosition()).department);
                        intent.putExtra("year", studentArrayList.get(getAdapterPosition()).year);
                        intent.putExtra("admission_year", studentArrayList.get(getAdapterPosition()).admission_year);
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
                        String enrollToDelete = studentArrayList.get(getAdapterPosition()).enroll;

                        // Call the method to handle the delete operation
                        deleteStudent(enrollToDelete, getAdapterPosition());
                    } catch (Exception ex) {
                        Log.e("Ex", "onClick: " + ex);
                    }
                }
            });


        }

        private void deleteStudent(String enrollToDelete, final int position) {
            String url = DBClass.urlStudentDelete + "?enroll=" + enrollToDelete;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Handle the response from the server after successful deletion
                            // Remove the item from the ArrayList and notify the adapter
                            studentArrayList.remove(position);
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