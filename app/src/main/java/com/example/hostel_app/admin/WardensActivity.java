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
import com.example.hostel_app.adapter.Outpass;
import com.example.hostel_app.adapter.Warden;
import com.example.hostel_app.adapter.WardenAdapter;
import com.example.hostel_app.officer.AttendanceActivity;
import com.example.hostel_app.officer.OfficerHomeActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class WardensActivity extends AppCompatActivity {

    private ArrayList<Warden> wardenArrayList;
    private WardenAdapter wardenAdapter;
    RecyclerView rview;
    private EditText edtSearch;
    ProgressDialog pDialog;
    Context context = null;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wardens);

        rview = findViewById(R.id.rview);
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
                filterwardens(charSequence.toString());
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
        Intent intent = new Intent(WardensActivity.this, AdminHomeActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity
    }
    private void filterwardens(String searchText) {
        ArrayList<Warden> filteredList = new ArrayList<>();
        for (Warden warden : wardenArrayList) {
            // Use containsIgnoreCase method for case-insensitive search
            if (containsIgnoreCase(warden.w_name, searchText)) {
                filteredList.add(warden);
            }
        }
        wardenAdapter.filterList(filteredList);
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

        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, DBClass.urlWardens,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.dismiss();
                        Log.d("Response ", ">> " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonData = jsonObject.getJSONArray("data");
                            wardenArrayList = new ArrayList<>();
                            for (int i = 0; i < jsonData.length(); i++) {
                                Warden warden = new Warden();
                                JSONObject jo = jsonData.getJSONObject(i);
                                warden.id = jo.getString("id");
                                warden.w_name = jo.getString("w_name");
                                warden.designation = jo.getString("designation");
                                warden.email = jo.getString("email");
                                warden.pass = jo.getString("pass");
                                warden.department = jo.getString("department");
                                warden.hostel = jo.getString("hostel");
                                warden.br_year = jo.getString("br_year");
                                wardenArrayList.add(warden);
                            }
                            wardenAdapter = new WardenAdapter(getApplicationContext(), wardenArrayList);
                            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
                            rview.setLayoutManager(layoutManager);
                            rview.setAdapter(wardenAdapter);
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

    public void btnAddWardenClick(View view) {

        Intent intent = new Intent(WardensActivity.this, WardenActivity.class);
        startActivity(intent);
    }
}