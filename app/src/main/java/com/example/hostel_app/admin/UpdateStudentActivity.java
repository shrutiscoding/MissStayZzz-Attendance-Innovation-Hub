package com.example.hostel_app.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hostel_app.DBClass;
import com.example.hostel_app.R;
import com.example.hostel_app.officer.AttendanceActivity;
import com.example.hostel_app.officer.OfficerHomeActivity;
import com.example.hostel_app.warden.WardenStudentsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UpdateStudentActivity extends AppCompatActivity {

    EditText etxtName, etxtmob, etxtpname, etxtpmob, etxtaddress, etxtcity, etxtdistrict, etxtEn;
    Spinner spnDept, spnHostel, spnYear, spnRoom, spnAdd_Year;
    String enroll, name, mob, pname, pmob, address, city, district, selectedYear = "", selectedHostel = "",
            selectedDept = "", selectedRoom = "", selectedAdd_Year = "";
    String[] dept = {"IT", "CO", "EJ", "EE", "ME", "CE", "DD"};
    String[] year = {"FY", "SY", "TY"};
    String[] hostel = {"Krishna", "Varana", "Yerala", "Kaveri", "Godavari", "Chandrabhaga", "Indrayani"};
    String[] room = {"G1", "G2", "G3", "G4", "G5", "G6", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "S1", "S2", "S3", "S4", "S5", "S6", "S7", "S8", "T1", "T2", "T3", "T4", "T5", "T6", "T7", "T8"};
    String[] add_year = {"2021-22", "2022-23", "2023-24"};

    ProgressDialog pDialog;
    JSONArray jsonData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student2);

        etxtName = findViewById(R.id.etxtName);
        etxtEn = findViewById(R.id.etxtEn);
        etxtmob = findViewById(R.id.etxtmob);
        etxtpname = findViewById(R.id.etxtpname);
        etxtpmob = findViewById(R.id.etxtpmob);
        etxtaddress = findViewById(R.id.etxtaddress);
        etxtcity = findViewById(R.id.etxtcity);
        etxtdistrict = findViewById(R.id.etxtdistrict);

        spnDept = findViewById(R.id.spnDept);
        spnHostel = findViewById(R.id.spnHostel);
        spnYear = findViewById(R.id.spnYear);
        spnRoom = findViewById(R.id.spnRoom);
        spnAdd_Year = findViewById(R.id.spnAdd_Year);

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, year);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> deptAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dept);
        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> hostelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, hostel);
        hostelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, room);
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> add_yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, add_year);
        add_yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnYear.setAdapter(yearAdapter);
        spnHostel.setAdapter(hostelAdapter);
        spnDept.setAdapter(deptAdapter);
        spnRoom.setAdapter(roomAdapter);
        spnAdd_Year.setAdapter(add_yearAdapter);

        Intent intent = getIntent();
        if (intent != null) {
            enroll = intent.getStringExtra("enroll");
            name = intent.getStringExtra("name");
            mob = intent.getStringExtra("mob_no");
            pname = intent.getStringExtra("parent_name");
            pmob = intent.getStringExtra("parent_mob_no");
            address = intent.getStringExtra("address");
            city = intent.getStringExtra("city");
            district = intent.getStringExtra("district");
            selectedHostel = intent.getStringExtra("hostel");
            selectedRoom = intent.getStringExtra("room_no");
            selectedAdd_Year = intent.getStringExtra("admission_year");
            selectedDept = intent.getStringExtra("department");
            selectedYear = intent.getStringExtra("year");

            etxtEn.setText(enroll);
            etxtName.setText(name);
            etxtaddress.setText(address);
            etxtpmob.setText(pmob);
            etxtmob.setText(mob);
            etxtpname.setText(pname);
            etxtcity.setText(city);

            etxtdistrict.setText(district);

            int roomPosition = Arrays.asList(room).indexOf(selectedRoom);
            spnRoom.setSelection(roomPosition);

            int deptPosition = Arrays.asList(dept).indexOf(selectedDept);
            spnDept.setSelection(deptPosition);

            int addYearPosition = Arrays.asList(add_year).indexOf(selectedAdd_Year);
            spnAdd_Year.setSelection(addYearPosition);

            int hostelPosition = Arrays.asList(hostel).indexOf(selectedHostel);
            spnHostel.setSelection(hostelPosition);

            int yearPosition = Arrays.asList(year).indexOf(selectedYear);
            spnYear.setSelection(yearPosition);
            spnYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedYear = parent.getItemAtPosition(position).toString();
                    // Do something with the selected item
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
                    // Do something with the selected item
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Handle no selection if needed
                }
            });
            spnRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedRoom = parent.getItemAtPosition(position).toString();
                    // Do something with the selected item
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Handle no selection if needed
                }
            });
            spnAdd_Year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedAdd_Year = parent.getItemAtPosition(position).toString();
                    // Do something with the selected item
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Handle no selection if needed
                }
            });

        }
    }
    public void onBackPressed() {
        // Check if you have a previous activity to navigate to
        // If you have a previous activity, use Intent to navigate to it
        Intent intent = new Intent(UpdateStudentActivity.this, AdminStudentsActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity
    }

    public void btnUpdateClick(View view) {
        enroll = etxtEn.getText().toString();
        if (enroll.equals("")) {
            etxtEn.setError("Please enter Enrollment No");
            etxtEn.requestFocus();
            return;
        }

        name = etxtName.getText().toString();
        if (name.equals("")) {
            etxtName.setError("Please enter Name");
            etxtName.requestFocus();
            return;
        }

        mob = etxtmob.getText().toString();
        if (mob.equals("")) {
            etxtmob.setError("Please enter Mobile Number");
            etxtmob.requestFocus();
            return;
        }

        pname = etxtpname.getText().toString();
        if (pname.equals("")) {
            etxtpname.setError("Please enter Parents Name");
            etxtpname.requestFocus();
            return;
        }

        pmob = etxtpmob.getText().toString();
        if (pmob.equals("")) {
            etxtpmob.setError("Please enter Parents Mobile No.");
            etxtpmob.requestFocus();
            return;
        }

        address = etxtaddress.getText().toString();
        if (address.equals("")) {
            etxtaddress.setError("Please enter Address");
            etxtaddress.requestFocus();
            return;
        }

        city = etxtcity.getText().toString();
        if (city.equals("")) {
            etxtcity.setError("Please enter City");
            etxtcity.requestFocus();
            return;
        }

        district = etxtdistrict.getText().toString();
        if (district.equals("")) {
            etxtdistrict.setError("Please enter District");
            etxtdistrict.requestFocus();
            return;
        }

        pDialog = new ProgressDialog(com.example.hostel_app.admin.UpdateStudentActivity.this);
        pDialog.setMessage("validating your details, please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DBClass.urlUpdateStudent,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            Log.e("onResponse: ", jsonObject.getString("status"));

                            if (jsonObject.getString("status").equals("success")) {
                                Intent intent = new Intent(com.example.hostel_app.admin.UpdateStudentActivity.this, AdminStudentsActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Update Detail Failed...", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Check Internet Connection...", Toast.LENGTH_LONG).show();
                            Log.e("Exception", ">> " + e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Log.e("Exception", error.toString());
                        Toast.makeText(getApplicationContext(), "Check Internet Connection...", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("enroll", enroll);
                params.put("name", name);
                params.put("mob_no", mob);
                params.put("parent_name", pname);
                params.put("parent_mob_no", pmob);
                params.put("address", address);
                params.put("city", city);
                params.put("district", district);
                params.put("hostel", selectedHostel);
                params.put("room_no", selectedRoom);
                params.put("department", selectedDept);
                params.put("year", selectedYear);
                params.put("admission_year", selectedAdd_Year);
                Log.e("Params", params.toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(com.example.hostel_app.admin.UpdateStudentActivity.this);
        requestQueue.add(stringRequest);
    }
}
