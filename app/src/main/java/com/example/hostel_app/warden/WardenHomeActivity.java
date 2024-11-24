package com.example.hostel_app.warden;

import static com.example.hostel_app.DBClass.sms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hostel_app.DBClass;
import com.example.hostel_app.MainActivity;
import com.example.hostel_app.R;
import com.example.hostel_app.admin.AdminHomeActivity;
import com.example.hostel_app.admin.AdminStudentsActivity;
import com.example.hostel_app.admin.OfficersActivity;
import com.example.hostel_app.admin.WardensActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WardenHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warden_home);
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Exit");
        builder.setMessage("Are you sure want to exit? ");//For Exiting From App Alert
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

        public void btnLogoutClick(View view) {
            // Build an AlertDialog to confirm logout
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Logout");
            builder.setMessage("Are you sure you want to logout?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Perform logout actions
                    logout();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }

        private void logout() {

                // Clear the saved configuration
                String query = "DELETE FROM Configuration WHERE CName IN ('email', 'pass', 'usertype')";
                DBClass.execNonQuery(query);

                // Start the LoginActivity or any other desired activity
                Intent intent = new Intent(WardenHomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finish the current activity


        }


    public void btnOutpassClick(View view) {
        Intent intent = new Intent(WardenHomeActivity.this, OutpassesActivity.class);
        startActivity(intent);

        // Finish the current activity to prevent the user from coming back to it using the back button
        finish();
    }

    public void btnstudentClick(View view) {
        Intent intent = new Intent(WardenHomeActivity.this,WardenStudentsActivity.class);
        startActivity(intent);

        // Finish the current activity to prevent the user from coming back to it using the back button
        finish();
    }

    public void btnSmsClick(View view) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, sms,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            // Check if response contains data
                            if (!jsonObject.has("error")) {
                                String total = jsonObject.getString("totalCount");
                                String present = jsonObject.getString("totalPresent");
                                String absent = jsonObject.getString("totalAbsent");

                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String currentDate = dateFormat.format(new Date());

                                // Construct message with today's date and send SMS immediately
                                String message = "Date: " + currentDate + "\nTotal: " + total + "\n Present: " + present + "\n Absent: " + absent;
                                sendSMS("8208949690", message); // Replace "8668823418" with the desired phone number
                            } else {
                                // Handle error
                                Toast.makeText(getApplicationContext(), "Error: " + jsonObject.getString("error"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Error parsing JSON", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Error fetching data", Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(WardenHomeActivity.this);
        requestQueue.add(stringRequest);
    }

    private void sendSMS(String phoneNumber, String message) {
        try {
            if (phoneNumber != null && message != null && !phoneNumber.isEmpty() && !message.isEmpty()) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNumber, null, message, null, null);
                Toast.makeText(getApplicationContext(), "SMS sent successfully.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Invalid phone number or message.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Failed to send SMS.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


}
