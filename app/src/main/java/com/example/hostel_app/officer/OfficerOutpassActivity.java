package com.example.hostel_app.officer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hostel_app.DBClass;
import com.example.hostel_app.R;
import com.example.hostel_app.warden.OutpassesActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class OfficerOutpassActivity extends AppCompatActivity {

    Button indbutton,intbutton,outdbutton,outtbutton;
    private Calendar calendar;
    ProgressDialog pDialog;
    String[] comm = {"SMS","MSG","Call"};
    EditText etxtName,etxtReason,etxtAddress,etxtEnroll,etxtRemark,etxtpmob;
    Spinner spncomm;
    String enroll,name,reason,address,outdate,outtime,indate,intime,remark,pmob;
    String communication=" ";
    private SimpleDateFormat dateFormatter, timeFormatter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officer_outpass);
        indbutton = findViewById(R.id.indate);
        intbutton = findViewById(R.id.intime);
        outdbutton = findViewById(R.id.outdate);
        outtbutton = findViewById(R.id.outtime);
        etxtName = findViewById(R.id.etxtName);
        etxtEnroll = findViewById(R.id.etxtEn);
        etxtReason = findViewById(R.id.etxtReason);
        etxtAddress = findViewById(R.id.etxtAddress);
        etxtRemark = findViewById(R.id.etxtRemark);
        etxtpmob=findViewById(R.id.etxtpmob);
        spncomm=findViewById(R.id.spncomm);

        ArrayAdapter<String> commAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, comm);
        commAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        calendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
        timeFormatter = new SimpleDateFormat("HH:mm:ss", Locale.US);

        spncomm.setAdapter(commAdapter);

        spncomm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                communication = parent.getItemAtPosition(position).toString();
                // Do something with the selected item
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection if needed
            }
        });

        indbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInDatePickerDialog();
            }
        });
        intbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInTimePickerDialog();
            }
        });
        outdbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOutDatePickerDialog();
            }
        });
        outtbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOutTimePickerDialog();
            }
        });
    }

    private void showOutDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateOutDate();
            }
        };

        new DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }
    @Override
    public void onBackPressed() {
        // Check if you have a previous activity to navigate to
        // If you have a previous activity, use Intent to navigate to it
        Intent intent = new Intent(OfficerOutpassActivity.this, Officer_OutpassesActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity
    }

    private void showOutTimePickerDialog() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                updateOutTime();

            }
        };

        new TimePickerDialog(
                this,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),

                true
        ).show();
    }
    private void showInDatePickerDialog() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateInDate();
            }
        };

        new DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void showInTimePickerDialog() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                updateInTime();

            }
        };

        new TimePickerDialog(
                this,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),

                true
        ).show();
    }
    private void updateInTime() {
        intbutton.setText(timeFormatter.format(calendar.getTime()));

    }
    private void updateInDate() {
        indbutton.setText(dateFormatter.format(calendar.getTime()));

    }
    private void updateOutTime() {
        outtbutton.setText(timeFormatter.format(calendar.getTime()));

    }
    private void updateOutDate() {
        outdbutton.setText(dateFormatter.format(calendar.getTime()));

    }

    public void btnSubmitClick(View view)
    {
        enroll = etxtEnroll.getText().toString().trim();
        if(enroll.equals("")) {
            etxtEnroll.setError("Please enter Enrollment No");
            etxtEnroll.requestFocus();
            return;
        }
        name = etxtName.getText().toString().trim();
        if(name.equals("")) {
            etxtName.setError("Please enter Name");
            etxtName.requestFocus();
            return;
        }
        reason= etxtReason.getText().toString().trim();
        if(reason.equals("")) {
            etxtReason.setError("Please enter Reason for Holiday");
            etxtReason.requestFocus();
            return;
        }
        pmob= etxtpmob.getText().toString().trim();
        if(pmob.equals("")) {
            etxtpmob.setError("Please enter valid parent mobile no");
            etxtpmob.requestFocus();
            return;
        }
        outdate = outdbutton.getText().toString().trim(); // Use getText from the button
        if (outdate.equals("")) {
            outdbutton.setError("Please select outgoing date");
            outdbutton.requestFocus();
            return;
        }

        outtime = outtbutton.getText().toString().trim(); // Use getText from the button
        if (outtime.equals("")) {
            outtbutton.setError("Please select outgoing time");
            outtbutton.requestFocus();
            return;
        }

        address = etxtAddress.getText().toString().trim();
        if (address.equals("")) {
            etxtAddress.setError("Please enter Address");
            etxtAddress.requestFocus();
            return;
        }

        indate = indbutton.getText().toString().trim(); // Use getText from the button
        if (indate.equals("")) {
            indbutton.setError("Please select incoming date");
            indbutton.requestFocus();
            return;
        }

        intime = intbutton.getText().toString().trim(); // Use getText from the button
        if (intime.equals("")) {
            intbutton.setError("Please select incoming time");
            intbutton.requestFocus();
            return;
        }
        remark = etxtRemark.getText().toString().trim(); // Use getText from the button
        if (remark.equals("")) {
            etxtRemark.setError("Please enter remark");
            etxtRemark.requestFocus();
            return;
        }

        pDialog = new ProgressDialog(OfficerOutpassActivity.this);
        pDialog.setMessage("validating your details, please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DBClass.urlAddOutpass,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        Log.d("Response", ">> "+response);
                        pDialog.dismiss();
                        try
                        {
                            jsonObject = new JSONObject(response);

                            Log.e( "onResponse: ", jsonObject.getString("status") );

                            if(jsonObject.getString("status").equals("success"))
                            {

                                Intent intent = new Intent(OfficerOutpassActivity.this, Officer_OutpassesActivity.class);
                                startActivity(intent);
                                finish();

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Registration Failed... \nPlease Check Parent Mobile Number correctly!!\nRecheck It!!", Toast.LENGTH_LONG).show();
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
                params.put("enroll", enroll);
                params.put("name", name);
                params.put("pmob", pmob);
                params.put("reason", reason);
                params.put("address", address);
                params.put("outdate", outdate);
                params.put("outtime", outtime);
                params.put("indate", indate);
                params.put("intime", intime);
                params.put("communication", communication);
                params.put("remark", remark);


                Log.e("Params", params.toString());
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(OfficerOutpassActivity.this);
        requestQueue.add(stringRequest);


    }


}