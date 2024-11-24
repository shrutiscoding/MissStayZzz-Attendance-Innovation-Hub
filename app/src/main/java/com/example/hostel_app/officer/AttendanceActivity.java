package com.example.hostel_app.officer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hostel_app.R;
import com.example.hostel_app.warden.OutpassActivity;
import com.example.hostel_app.warden.OutpassesActivity;
import com.example.hostel_app.warden.WardenHomeActivity;

public class AttendanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
    }

    public void btnabsentClick(View view) {
        Intent intent = new Intent(AttendanceActivity.this, AbsentStudentActivity.class);
        startActivity(intent);

    }

    public void btnpresentClick(View view) {
        Intent intent = new Intent(AttendanceActivity.this, PresentStudentActivity.class);
        startActivity(intent);

    }

    public void onBackPressed() {
        // Check if you have a previous activity to navigate to
        // If you have a previous activity, use Intent to navigate to it
        Intent intent = new Intent(AttendanceActivity.this, OfficerHomeActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity
    }
}