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
import com.example.hostel_app.adapter.Absent;
import com.example.hostel_app.adapter.AbsentAdapter;
import com.example.hostel_app.adapter.Student;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AbsentStudentActivity extends AppCompatActivity {

    private ArrayList<Absent> absentArrayList;
    private AbsentAdapter absentAdapter;
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
        setContentView(R.layout.activity_absent_student);

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
                filterAbsents(charSequence.toString());
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
                filterAbsents1(charSequence.toString());
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
    public void onBackPressed() {
        // Check if you have a previous activity to navigate to
        // If you have a previous activity, use Intent to navigate to it
        Intent intent = new Intent(AbsentStudentActivity.this, AttendanceActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity
    }

    private void filterAndDisplayStudents() {
        ArrayList<Absent> filteredList = new ArrayList<>();
        for (Absent absent : absentArrayList) {
            boolean isYearMatch = selectedYear.equals("year") || absent.year.equals(selectedYear);
            boolean isDeptMatch = selectedDept.equals("dept") || absent.department.equals(selectedDept);
            boolean isHostelMatch = selectedHostel.equals("hostel") || absent.hostel.equals(selectedHostel);

            if (isYearMatch && isDeptMatch && isHostelMatch) {
                filteredList.add(absent);
            }
        }
        absentAdapter.filterList(filteredList);
    }

    // Method to filter outpasses based on the entered name
    private void filterAbsents(String searchText) {
        ArrayList<Absent> filteredList = new ArrayList<>();
        for (Absent absent : absentArrayList) {
            // Use containsIgnoreCase method for case-insensitive search
            if (containsIgnoreCase(absent.name, searchText)) {
                filteredList.add(absent);
            }
        }
        absentAdapter.filterList(filteredList);
    }
    private void filterAbsents1(String searchText) {
        ArrayList<Absent> filteredList = new ArrayList<>();
        for (Absent absent : absentArrayList) {
            // Use containsIgnoreCase method for case-insensitive search
            if (containsIgnoreCase(absent.enroll, searchText)) {
                filteredList.add(absent);
            }
        }
        absentAdapter.filterList(filteredList);
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

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, DBClass.absentsud,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        Log.d("Response ", ">> " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonData = jsonObject.getJSONArray("data");
                            absentArrayList = new ArrayList<>();
                            for (int i = 0; i < jsonData.length(); i++) {
                                Absent absent=new Absent();
                                JSONObject jo = jsonData.getJSONObject(i);
                                absent.enroll = jo.getString("enroll");
                                absent.name = jo.getString("name");
                                absent.hostel = jo.getString("hostel");
                                absent.room_no = jo.getString("room_no");
                                absent.department = jo.getString("department");
                                absent.year = jo.getString("year");

                                absentArrayList.add(absent);
                            }
                            absentAdapter = new AbsentAdapter(getApplicationContext(), absentArrayList);
                            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                            rview.setLayoutManager(layoutManager);
                            rview.setAdapter(absentAdapter);
                        } catch (Exception e) {
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



}