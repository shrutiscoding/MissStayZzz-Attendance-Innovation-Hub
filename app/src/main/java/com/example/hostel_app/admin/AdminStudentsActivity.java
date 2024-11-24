package com.example.hostel_app.admin;

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
import com.example.hostel_app.adapter.AdminStudentAdapter;
import com.example.hostel_app.adapter.Student;
import com.example.hostel_app.adapter.WardenStudentAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AdminStudentsActivity extends AppCompatActivity {
    private ArrayList<Student> studentArrayList;
    private AdminStudentAdapter studentAdapter;
    RecyclerView rview;
    ProgressDialog pDialog;
    String selectedYear = "", selectedHostel = "", selectedDept = "";
    Spinner spnDept, spnHostel, spnYear, spnRoom, spnAdd_Year;
    String[] dept = {"dept","IT", "CO", "EJ", "EE", "ME", "CE", "DD"};
    String[] year = {"year","FY", "SY", "TY"};
    String[] hostel = {"hostel","Krishna", "Varana", "Yerala", "Kaveri", "Godavari", "Chandrabhaga", "Indrayani"};
    private EditText edtSearch,edtSearch1;
    Context context = null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_students);

        rview = findViewById(R.id.rview);
        edtSearch = findViewById(R.id.edtSearch);
        edtSearch1= findViewById(R.id.edtSearch1);
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
                filterstudents(charSequence.toString());
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
                filterstudents1(charSequence.toString());
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
        Intent intent = new Intent(AdminStudentsActivity.this, AdminHomeActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity
    }
    private void filterAndDisplayStudents() {
        ArrayList<Student> filteredList = new ArrayList<>();
        for (Student student : studentArrayList) {
            boolean isYearMatch =  student.year.equals(selectedYear);
            boolean isDeptMatch =  student.department.equals(selectedDept);
            boolean isHostelMatch =  student.hostel.equals(selectedHostel);

            if (isYearMatch && isDeptMatch && isHostelMatch) {
                filteredList.add(student);
            }
        }
        studentAdapter.filterList(filteredList);
    }
    private void filterstudents(String searchText) {
        ArrayList<Student> filteredList = new ArrayList<>();
        for (Student student : studentArrayList) {
            boolean isMatch = containsIgnoreCase(student.s_name, searchText);
            if (isMatch) {
                filteredList.add(student);
            }
        }
        studentAdapter.filterList(filteredList);
    }
    private void filterstudents1(String searchText) {
        ArrayList<Student> filteredList = new ArrayList<>();
        for (Student student : studentArrayList) {
            boolean isMatch = containsIgnoreCase(student.enroll, searchText);
            if (isMatch) {
                filteredList.add(student);
            }
        }
        studentAdapter.filterList(filteredList);
    }

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

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, DBClass.urlStudents,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        Log.d("Response ", ">> " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonData = jsonObject.getJSONArray("data");
                            studentArrayList = new ArrayList<>();
                            for (int i = 0; i < jsonData.length(); i++) {
                                Student student = new Student();
                                JSONObject jo = jsonData.getJSONObject(i);
                                student.enroll = jo.getString("enroll");
                                student.s_name = jo.getString("name");
                                student.mob_no = jo.getString("mob_no");
                                student.parent_name = jo.getString("parent_name");
                                student.parent_mob_no = jo.getString("parent_mob_no");
                                student.address = jo.getString("address");
                                student.city = jo.getString("city");
                                student.district = jo.getString("district");
                                student.department = jo.getString("department");
                                student.hostel = jo.getString("hostel");
                                student.year = jo.getString("year");
                                student.room_no = jo.getString("room_no");
                                student.admission_year = jo.getString("admission_year");
                                studentArrayList.add(student);
                            }
                            studentAdapter = new AdminStudentAdapter(getApplicationContext(), studentArrayList);
                            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                            rview.setLayoutManager(layoutManager);
                            rview.setAdapter(studentAdapter);
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



    public void btnAddStudentClick(View view) {

        Intent intent = new Intent(AdminStudentsActivity.this, StudentActivity.class);
        startActivity(intent);
    }

}