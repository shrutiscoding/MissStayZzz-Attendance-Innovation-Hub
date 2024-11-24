package com.example.hostel_app.warden;

import androidx.appcompat.app.AppCompatActivity;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UpdateOutpassActivity extends AppCompatActivity {

    Button indbutton, intbutton, outdbutton, outtbutton;
    private Calendar calendar;
    Spinner spncomm;
    ProgressDialog pDialog;
    String[] comm = {"SMS","MSG","Call"};
    EditText etxtName, etxtReason, etxtAddress, etxtEnroll, etxtRemark,etxtpmob;
    String enroll, name, reason, address, outdate, outtime, indate, intime, remark,pmob;
    private SimpleDateFormat dateFormatter, timeFormatter;
    String communication=" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_outpass);
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
        spncomm.setAdapter(commAdapter);


        Intent intent = getIntent();
        if (intent != null) {
            enroll = intent.getStringExtra("enroll");
            name = intent.getStringExtra("name");
            pmob = intent.getStringExtra("pmob");
            reason = intent.getStringExtra("reason");
            address = intent.getStringExtra("address");
            outdate = intent.getStringExtra("outdate");
            outtime = intent.getStringExtra("outtime");
            indate = intent.getStringExtra("indate");
            intime = intent.getStringExtra("intime");
            communication = intent.getStringExtra("communication");

            // Log statements for debugging
            Log.e("UpdateOutpassActivity", "pmob from intent: " + pmob);
            Log.e("UpdateOutpassActivity", "communication from intent: " + communication);
            remark = intent.getStringExtra("remark");



            etxtEnroll.setText(enroll);
            etxtName.setText(name);
            etxtpmob.setText(pmob);
            etxtReason.setText(reason);
            etxtAddress.setText(address);
            outdbutton.setText(outdate);
            outtbutton.setText(outtime);
            indbutton.setText(indate);
            intbutton.setText(intime);
            etxtRemark.setText(remark);

            int pos = Arrays.asList(comm).indexOf(communication);
            spncomm.setSelection(pos);

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


            dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            timeFormatter = new SimpleDateFormat("HH:mm", Locale.US);

            outdbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePicker(outdbutton);
                }
            });

            outtbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTimePicker(outtbutton);
                }
            });

            indbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePicker(indbutton);
                }
            });

            intbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTimePicker(intbutton);
                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        // Check if you have a previous activity to navigate to
        // If you have a previous activity, use Intent to navigate to it
        Intent intent = new Intent(com.example.hostel_app.warden.UpdateOutpassActivity.this,OutpassesActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity
    }

    private void showDatePicker(final Button button) {
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(com.example.hostel_app.warden.UpdateOutpassActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year, monthOfYear, dayOfMonth);
                        String date = dateFormatter.format(calendar.getTime());
                        button.setText(date);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePicker(final Button button) {
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(com.example.hostel_app.warden.UpdateOutpassActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        String time = timeFormatter.format(calendar.getTime());
                        button.setText(time);
                    }
                }, hour, minute, true);
        timePickerDialog.show();
    }

    public void btnUpdateClick(View view) {
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
        outdate = outdbutton.getText().toString().trim(); // Use getText from the button
        if (outdate.equals("")) {
            outdbutton.setError("Please select outgoing date");
            outdbutton.requestFocus();
            return;
        }
        pmob= etxtpmob.getText().toString().trim();
        if(pmob.equals("")) {
            etxtpmob.setError("Please enter valid parent mobile no");
            etxtpmob.requestFocus();
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
        pDialog = new ProgressDialog(com.example.hostel_app.warden.UpdateOutpassActivity.this);
        pDialog.setMessage("validating your details, please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, DBClass.urlUpdateOutpass,
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

                                Intent intent = new Intent(com.example.hostel_app.warden.UpdateOutpassActivity.this, OutpassesActivity.class);
                                startActivity(intent);
                                finish();

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Update Detail Failed...", Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(com.example.hostel_app.warden.UpdateOutpassActivity.this);
        requestQueue.add(stringRequest);


    }

}