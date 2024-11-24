package com.example.hostel_app.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hostel_app.DBClass;
import com.example.hostel_app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Student {
    public String enroll = "";
    public String s_name = "";
    public String mob_no = "";
    public String parent_name = "";
    public String parent_mob_no = "";
    public String address = "";
    public String city = "";
    public String district = "";
    public String department = "";
    public String hostel = "";
    public String year = "";
    public String room_no = "";
    public String admission_year = "";


    public String getDepartment() {
        return  department;
    }

    public String getHostel() {
        return hostel;
    }

    public String getYear() {
        return year;
    }
}
