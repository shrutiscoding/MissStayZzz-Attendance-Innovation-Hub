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

public class OfficerActivity extends AppCompatActivity {

    EditText etxtName, etxtEmail, etxtPassword;

    String name, email, password;

    ProgressDialog pDialog;
    JSONArray jsonData = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officer);
        etxtName = findViewById(R.id.etxtName);
        etxtEmail = findViewById(R.id.etxtEmail);
        etxtPassword = findViewById(R.id.etxtPassword);


    }
    public void onBackPressed() {
        // Check if you have a previous activity to navigate to
        // If you have a previous activity, use Intent to navigate to it
        Intent intent = new Intent(OfficerActivity.this, OfficersActivity.class);
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

        password = etxtPassword.getText().toString();
        if(password.equals("")) {
            etxtPassword.setError("Please enter Password");
            etxtPassword.requestFocus();
            return;
        }


        pDialog = new ProgressDialog(OfficerActivity.this);
        pDialog.setMessage("validating your details, please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DBClass.urlAddOfficer,
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

                                Intent intent = new Intent(getApplicationContext(), OfficersActivity.class);
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
                params.put("r_name", name);
                params.put("email", email);
                params.put("pass", password);
                Log.e("Params", params.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

}