package com.example.hostel_app.warden;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.Calendar;

public class SmsScheduler extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // Retrieve message and phone number from intent extras
        String message = intent.getStringExtra("message");
        String phoneNumber = intent.getStringExtra("phoneNumber");

        // Send SMS
        sendSMS(context, phoneNumber, message);
    }

    public void setAlarm(Context context, String phoneNumber, String message) {
       Intent intent = new Intent(context, SmsScheduler.class);
        // Pass message and phone number as extras to the intent
        intent.putExtra("message", message);
        intent.putExtra("phoneNumber", phoneNumber);

    }

    private void sendSMS(Context context, String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(context, "SMS sent successfully.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "Failed to send SMS.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
