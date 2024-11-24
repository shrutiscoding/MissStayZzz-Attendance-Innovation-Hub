package com.example.hostel_app.officer;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hostel_app.DBClass;
import com.example.hostel_app.R;
import com.example.hostel_app.adapter.Outpass;
import com.example.hostel_app.adapter.WardenOutpassAdapter;
import com.example.hostel_app.warden.OutpassActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Officer_OutpassesActivity extends AppCompatActivity {

    private ArrayList<Outpass> outpassArrayList;
    private WardenOutpassAdapter outpassAdapter;
    private EditText edtSearch,edtSearch1;
    String selectedYear = "", selectedHostel = "", selectedDept = "";
    Spinner spnDept, spnHostel, spnYear, spnRoom, spnAdd_Year;
    String[] dept = {"dept","IT", "CO", "EJ", "EE", "ME", "CE", "DD"};
    String[] year = {"year","FY", "SY", "TY"};
    String[] hostel = {"hostel","Krishna", "Varana", "Yerala", "Kaveri", "Godavari", "Chandrabhaga", "Indrayani"};
    RecyclerView rview;
    ProgressDialog pDialog;
    Context context = null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officer_outpasses);

        rview = findViewById(R.id.rview);
        edtSearch = findViewById(R.id.edtSearch);
        edtSearch1 = findViewById(R.id.edtSearch1);
        spnDept = findViewById(R.id.spnDept);
        spnHostel = findViewById(R.id.spnHostel);
        spnYear = findViewById(R.id.spnYear);

        lists();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Filter the outpassArrayList based on the entered name
                filterOutpasses(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Do nothing
            }
        });
        edtSearch1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Filter the outpassArrayList based on the entered name
                filterOutpasses1(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Do nothing
            }
        });
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, year);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> deptAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dept);
        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> hostelAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hostel);
        hostelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnYear.setAdapter(yearAdapter);
        spnHostel.setAdapter(hostelAdapter);
        spnDept.setAdapter(deptAdapter);
        spnYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedYear = parent.getItemAtPosition(position).toString();
                // Do something with the selected item
                filterAndDisplayStudents();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection if needed
            }
        });
        spnDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDept = parent.getItemAtPosition(position).toString();
                // Do something with the selected item
                filterAndDisplayStudents();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection if needed
            }
        });
        spnHostel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedHostel = parent.getItemAtPosition(position).toString();
                filterAndDisplayStudents();
                // Do something with the selected item
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection if needed
            }
        });
    }
    @Override
    public void onBackPressed() {
        // Check if you have a previous activity to navigate to
        // If you have a previous activity, use Intent to navigate to it
        Intent intent = new Intent(com.example.hostel_app.officer.Officer_OutpassesActivity.this, com.example.hostel_app.officer.OfficerHomeActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity
    }
    private void filterAndDisplayStudents() {
        ArrayList<Outpass> filteredList = new ArrayList<>();
        for (Outpass outpass : outpassArrayList) {
            boolean isYearMatch = outpass.year.equals(selectedYear);
            boolean isDeptMatch = outpass.department.equals(selectedDept);
            boolean isHostelMatch = outpass.hostel.equals(selectedHostel);

            if (isYearMatch && isDeptMatch && isHostelMatch) {
                filteredList.add(outpass);
            }
        }
        outpassAdapter.filterList(filteredList);
    }
    // Method to filter outpasses based on the entered name
    private void filterOutpasses(String searchText) {
        ArrayList<Outpass> filteredList = new ArrayList<>();
        for (Outpass outpass : outpassArrayList) {
            // Use containsIgnoreCase method for case-insensitive search
            if (containsIgnoreCase(outpass.name, searchText)) {
                filteredList.add(outpass);
            }
        }
        outpassAdapter.filterList(filteredList);
    }
    private void filterOutpasses1(String searchText) {
        ArrayList<Outpass> filteredList = new ArrayList<>();
        for (Outpass outpass : outpassArrayList) {
            // Use containsIgnoreCase method for case-insensitive search
            if (containsIgnoreCase(outpass.enroll, searchText)) {
                filteredList.add(outpass);
            }
        }
        outpassAdapter.filterList(filteredList);
    }

    // Helper method to check if a string contains another string (case-insensitive)
    private static boolean containsIgnoreCase(String haystack, String needle) {
        if (haystack == null || needle == null) {
            return false;
        }
        return haystack.toLowerCase(Locale.US).contains(needle.toLowerCase(Locale.US));
    }




    public void lists()
    {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("downloading, please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                finish();
            }
        });
        pDialog.show();

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, DBClass.urlOutpasses,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        Log.d("Response ", ">> " + response);
                        try  {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonData = jsonObject.getJSONArray("data");
                            outpassArrayList = new ArrayList<>();
                            ArrayList<Outpass> redItems = new ArrayList<>(); // Store red items separately
                            ArrayList<Outpass> blueItems = new ArrayList<>(); // Store blue items separately

                            for (int i = 0; i < jsonData.length(); i++) {
                                Outpass outpass = new Outpass();
                                JSONObject jo = jsonData.getJSONObject(i);
                                outpass.enroll = jo.getString("enroll");
                                outpass.name = jo.getString("name");
                                outpass.pmob = jo.getString("pmob");
                                outpass.reason = jo.getString("reason");
                                outpass.address = jo.getString("address");
                                outpass.outdate = jo.getString("outdate");
                                outpass.outtime = jo.getString("outtime");
                                outpass.indate = jo.getString("indate");
                                outpass.intime = jo.getString("intime");
                                outpass.communication = jo.getString("communication");
                                outpass.remark = jo.getString("remark");
                                outpass.department = jo.getString("department");
                                outpass.year = jo.getString("year");
                                outpass.hostel = jo.getString("hostel");

                                // Check if the indate is today's date
                                String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Calendar.getInstance().getTime());
                                outpass.isToday = todayDate.equals(outpass.indate);

                                if (outpass.isToday) {
                                    redItems.add(outpass); // Add red items to the separate list
                                } else if (outpass.outdate.compareTo(todayDate) > 0) {
                                    blueItems.add(outpass); // Add blue items to the separate list
                                } else {
                                    redItems.add(outpass);
                                }
                            }

                            // Sort the blue items based on indate
                            Collections.sort(blueItems, new Comparator<Outpass>() {
                                @Override
                                public int compare(Outpass o1, Outpass o2) {
                                    return o1.outdate.compareTo(o2.outdate);
                                }
                            });

                            // Add all red items at the top
                            outpassArrayList.addAll(0, redItems);
                            // Add all blue items after red items
                            outpassArrayList.addAll(redItems.size(), blueItems);

                            outpassAdapter = new WardenOutpassAdapter(getApplicationContext(), outpassArrayList);
                            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                            rview.setLayoutManager(layoutManager);
                            rview.setAdapter(outpassAdapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
//                params.put("userid", userid);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void btnAddOutpassClick(View view) {

        Intent intent = new Intent(com.example.hostel_app.officer.Officer_OutpassesActivity.this,com.example.hostel_app.officer.OfficerOutpassActivity.class);
        startActivity(intent);
    }

}