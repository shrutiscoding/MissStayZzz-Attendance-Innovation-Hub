package com.example.hostel_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;

import com.example.hostel_app.admin.AdminHomeActivity;
import com.example.hostel_app.officer.OfficerHomeActivity;
import com.example.hostel_app.warden.WardenHomeActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        videoView = findViewById(R.id.videoView);

        // Set the path of the video file from assets
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.miss; // Replace 'video' with the name of your video file in the assets folder without extension
        videoView.setVideoPath(videoPath);

        createDatabase();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                String query = "SELECT * FROM Configuration";
                if (DBClass.checkIfRecordExist(query)) {

                    query = "SELECT CValue FROM Configuration WHERE CName = 'usertype'";
                    String usertype = DBClass.getSingleValue(query);

                    if (usertype.equals("admin"))
                    {
                        intent = new Intent(getApplicationContext(), AdminHomeActivity.class);
                    }
                    else if (usertype.equals("warden"))
                    {
                        intent = new Intent(getApplicationContext(), WardenHomeActivity.class);
                    }
                    else if (usertype.equals("officer"))
                    {
                        intent = new Intent(getApplicationContext(), OfficerHomeActivity.class);
                    }

                    else
                    {
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                    }
                }
                // Your existing code for checking user type

                startActivity(intent);
                finish();
            }
        }, 2000);

        // Start video playback
        videoView.start();
    }

    public void createDatabase() {
        String query;
        DBClass.database = openOrCreateDatabase(DBClass.dbname, MODE_PRIVATE, null);
        query = "CREATE TABLE IF NOT EXISTS Configuration(CName VARCHAR, CValue VARCHAR);";
        DBClass.execNonQuery(query);
    }
}
