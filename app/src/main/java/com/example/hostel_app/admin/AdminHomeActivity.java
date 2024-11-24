package com.example.hostel_app.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.example.hostel_app.DBClass;
import com.example.hostel_app.MainActivity;
import com.example.hostel_app.R;

public class AdminHomeActivity extends AppCompatActivity {

    private com.example.hostel_app.DBClass DBClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
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

    public void btnStudentsClick(View view) {
        Intent intent = new Intent(AdminHomeActivity.this, AdminStudentsActivity.class);
        startActivity(intent);
    }

    public void btnWardensClick(View view) {
        Intent intent = new Intent(AdminHomeActivity.this, WardensActivity.class);
        startActivity(intent);
    }

    public void btnOfficersClick(View view) {
        Intent intent = new Intent(AdminHomeActivity.this, OfficersActivity.class);
        startActivity(intent);
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
        Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity
    }


    public void btnReportClick(View view) {
        Intent intent = new Intent(AdminHomeActivity.this, AdminReportActivity.class);
        startActivity(intent);
    }
}