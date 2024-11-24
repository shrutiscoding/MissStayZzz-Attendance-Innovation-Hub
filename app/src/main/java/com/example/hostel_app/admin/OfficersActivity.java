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
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hostel_app.DBClass;
import com.example.hostel_app.R;
import com.example.hostel_app.adapter.Officer;
import com.example.hostel_app.adapter.OfficerAdapter;
import com.example.hostel_app.adapter.Warden;
import com.example.hostel_app.adapter.WardenAdapter;
import com.example.hostel_app.officer.AttendanceActivity;
import com.example.hostel_app.officer.OfficerHomeActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class OfficersActivity extends AppCompatActivity {

    private ArrayList<Officer> officerArrayList;
    private OfficerAdapter officerAdapter;
    RecyclerView rview;
    private EditText edtSearch;
    ProgressDialog pDialog;
    Context context = null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officers);

        rview= findViewById(R.id.rview);
        edtSearch = findViewById(R.id.edtSearch);

        lists();
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Filter the outpassArrayList based on the entered name
                filterofficers(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Do nothing
            }
        });

    }
    public void onBackPressed() {
        // Check if you have a previous activity to navigate to
        // If you have a previous activity, use Intent to navigate to it
        Intent intent = new Intent(OfficersActivity.this, AdminHomeActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity
    }
    private void filterofficers(String searchText) {
        ArrayList<Officer> filteredList = new ArrayList<>();
        for (Officer officer: officerArrayList) {
            // Use containsIgnoreCase method for case-insensitive search
            if (containsIgnoreCase(officer.r_name, searchText)) {
                filteredList.add(officer);
            }
        }
        officerAdapter.filterList(filteredList);
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

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, DBClass.urlOfficers,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        Log.d("Response ", ">> " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonData = jsonObject.getJSONArray("data");
                            officerArrayList = new ArrayList<>();
                            for (int i = 0; i < jsonData.length(); i++) {
                                Officer officer = new Officer();
                                JSONObject jo = jsonData.getJSONObject(i);
                                officer.id = jo.getString("id");
                                officer.r_name = jo.getString("r_name");
                                officer.email = jo.getString("email");
                                officer.pass = jo.getString("pass");
                                officerArrayList.add(officer);
                            }
                            officerAdapter = new OfficerAdapter(getApplicationContext(), officerArrayList);
                            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                            rview.setLayoutManager(layoutManager);
                            rview.setAdapter(officerAdapter);
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

    public void btnAddOfficerClick(View view) {

        Intent intent = new Intent(OfficersActivity.this, OfficerActivity.class);
        startActivity(intent);
    }
}