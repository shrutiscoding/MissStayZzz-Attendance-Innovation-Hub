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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class WardenActivity extends AppCompatActivity {

    EditText etxtName, etxtEmail, etxtDesignation, etxtPassword;
    Spinner spnDept, spnHostel, spnYear;
    String name, email, designation, password, selectedYear="", selectedHostel="", selectedDept="";
    String[] dept = {"IT", "CO", "EJ", "EE", "ME","CE","DD"};
    String[] year = {"FY", "SY", "TY"};
    String[] hostel = { "Krishna","Varana", "Yerala", "Kaveri", "Godavari", "Chandrabhaga","Indrayani"};

    ProgressDialog pDialog;
    JSONArray jsonData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warden);
        etxtName = findViewById(R.id.etxtName);
        etxtDesignation = findViewById(R.id.etxtDesignation);
        etxtEmail = findViewById(R.id.etxtEmail);
        etxtPassword = findViewById(R.id.etxtPassword);

        spnDept = findViewById(R.id.spnDept);
        spnHostel = findViewById(R.id.spnHostel);
        spnYear = findViewById(R.id.spnYear);

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


    }
    public void onBackPressed() {
        // Check if you have a previous activity to navigate to
        // If you have a previous activity, use Intent to navigate to it
        Intent intent = new Intent(WardenActivity.this, WardensActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity
    }

    public void btnSubmitClick(View view) {


        name = etxtName.getText().toString();
        if(name.equals("")) {
            etxtName.setError("Please enter Name");
            etxtName.requestFocus();
            return;
        }
        email = etxtEmail.getText().toString();
        if(email.equals("")) {
            etxtEmail.setError("Please enter Email");
            etxtEmail.requestFocus();
            return;
        }
        designation = etxtDesignation.getText().toString();
        if(designation.equals("")) {
            etxtDesignation.setError("Please enter Designation");
            etxtDesignation.requestFocus();
            return;
        }
        password = etxtPassword.getText().toString();
        if(password.equals("")) {
            etxtPassword.setError("Please enter Password");
            etxtPassword.requestFocus();
            return;
        }


        pDialog = new ProgressDialog(WardenActivity.this);
        pDialog.setMessage("validating your details, please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DBClass.urlAddWarden,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        Log.d("Response", ">> "+response);
                        pDialog.dismiss();
                        try {
                            jsonObject = new JSONObject(response);

                            Log.e( "onResponse: ", jsonObject.getString("status") );

                            if(jsonObject.getString("status").equals("success")) {

                                Intent intent = new Intent(getApplicationContext(), WardensActivity.class);
                                startActivity(intent);
                                finish();

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Registration Failed...", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Check Internet Connection...", Toast.LENGTH_LONG).show();
                            Log.e("Exception", ">> "+e);
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
                }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();
                params.put("w_name", name);
                params.put("email", email);
                params.put("designation", designation);
                params.put("pass", password);
                params.put("department", selectedDept);
                params.put("br_year", selectedYear);
                params.put("hostel", selectedHostel);
                Log.e("Params", params.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }
}