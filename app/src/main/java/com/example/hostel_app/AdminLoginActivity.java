package com.example.hostel_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hostel_app.admin.AdminHomeActivity;
import com.example.hostel_app.officer.OfficerHomeActivity;
import com.example.hostel_app.warden.WardenHomeActivity;
import com.example.hostel_app.warden.WardenStudentsActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class AdminLoginActivity extends AppCompatActivity {

    EditText etxtEmail, etxtPassword;
    String email, password;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        etxtEmail = findViewById(R.id.etxtEmail);
        etxtPassword = findViewById(R.id.etxtPassword);
        etxtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
    @Override
    public void onBackPressed() {
        // Check if you have a previous activity to navigate to
        // If you have a previous activity, use Intent to navigate to it
        Intent intent = new Intent(AdminLoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity
    }
    public void btnLoginClick(View view) {
        String email = etxtEmail.getText().toString().trim();
        String pass = etxtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etxtEmail.setError("Please enter email or mobile number");
            etxtEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            etxtPassword.setError("Please enter Password");
            etxtPassword.requestFocus();
            return;
        }

        pDialog = new ProgressDialog(AdminLoginActivity.this);
        pDialog.setMessage("Validating your details, please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DBClass.adminLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        Log.d("Response", ">> " + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean loginSuccess = jsonObject.getString("status").equals("success");

                            if (loginSuccess) {
                                // Clear existing configuration
                                String query = "DELETE FROM Configuration";
                                DBClass.execNonQuery(query);

                                // Insert new configuration
                                query = "INSERT INTO Configuration(CName, CValue) ";
                                query += "VALUES('email', '" + email.replace("'", "''") + "')";
                                DBClass.execNonQuery(query);

                                query = "INSERT INTO Configuration(CName, CValue) ";
                                query += "VALUES('pass', '" + pass.replace("'", "''") + "')";
                                DBClass.execNonQuery(query);

                                query = "INSERT INTO Configuration(CName, CValue) ";
                                query += "VALUES('usertype', 'admin')";
                                DBClass.execNonQuery(query);
                                // Start the WardenHomeActivity
                                Intent intent = new Intent(AdminLoginActivity.this, AdminHomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Email or Password not found...", Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Check Internet Connection...", Toast.LENGTH_LONG).show();
                            etxtEmail.setText("");
                            etxtPassword.setText("");
                        }
                    }
                }, new Response.ErrorListener() {
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
                params.put("email", email);
                params.put("pass", pass);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }


}