package com.example.hostel_app.admin;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.hostel_app.ByteArrayRequest;
import com.example.hostel_app.DBClass;
import com.example.hostel_app.R;
import com.example.hostel_app.officer.OfficerHomeActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdminReportActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 1;
    private  String currentHostel=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_report);

        Button btnRequestPDF = findViewById(R.id.btnRequestPDF);
        Button btnKrishna = findViewById(R.id.btnKrishna);
        Button btnChandrabhaga = findViewById(R.id.btnChandrabhaga);
        Button btnKaveri = findViewById(R.id.btnKaveri);
        Button btnYerala = findViewById(R.id.btnYerala);
        Button btnGodavari = findViewById(R.id.btnGodavari);
        Button btnVarana = findViewById(R.id.btnVarana);
        Button btnAllhostel=findViewById(R.id.btnAllhostel);

        btnRequestPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentHostel = "Indrayani";
                // Handle PDF request
                handlePdfRequest();
            }
        });
        btnAllhostel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentHostel = "All";
                // Handle PDF request
                handlePdfRequest();
            }
        });
        btnVarana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentHostel = "Varana";
                // Handle PDF request
                handlePdfRequest();
            }
        });
        btnYerala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentHostel = "Yerala";
                // Handle PDF request
                handlePdfRequest();
            }
        });
        btnGodavari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentHostel = "Godavari";
                // Handle PDF request
                handlePdfRequest();
            }
        });
        btnKaveri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentHostel = "Kaveri";
                // Handle PDF request
                handlePdfRequest();
            }
        });
        btnKrishna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentHostel = "Krishna";
                // Handle PDF request
                handlePdfRequest();
            }
        });
        btnChandrabhaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentHostel = "Chandrabhaga";
                // Handle PDF request
                handlePdfRequest();
            }
        });

    }
    private void handlePdfRequest() {
        if (checkStoragePermission()) {
            // Check if a PDF request is already in progress
            if (currentHostel != null) {
                // Call the appropriate makePdfRequest method based on the currentHostel
                switch (currentHostel) {
                    case "Indrayani":
                        makePdfRequest("Indrayani");
                        break;
                    case "Chandrabhaga":makePdfRequest("Chandrabhaga");
                        break;
                    case "Yerala":
                        makePdfRequest("Yerala");
                        break;
                    case "Krishna":
                        makePdfRequest("Krishna");
                        break;
                    case "Kaveri":makePdfRequest("Kaveri");
                        break;
                    case "Varana":makePdfRequest("Varana");
                        break;
                    case "Godavari":makePdfRequest("Godavari");
                        break;
                    case "All":makePdfRequest("All");
                        break;
                    // Add cases for other hostels
                }
                // Reset the currentHostel after making the request
                currentHostel = null;
            }
        } else {
            // Request storage permission if not granted
            requestStoragePermission();
        }
    }
    @Override
    public void onBackPressed() {
        // Check if you have a previous activity to navigate to
        // If you have a previous activity, use Intent to navigate to it
        Intent intent = new Intent(AdminReportActivity.this, AdminHomeActivity.class);
        startActivity(intent);
        finish(); // Finish the current activity
    }

    private boolean checkStoragePermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    private void makePdfRequest(String hostelName) {
        RequestQueue queue = Volley.newRequestQueue(AdminReportActivity.this);
        if (hostelName.equals("Chandrabhaga")) {
            ByteArrayRequest byteArrayRequest = new ByteArrayRequest(Request.Method.GET, DBClass.chandrapdf,
                    new Response.Listener<byte[]>() {
                        @Override
                        public void onResponse(byte[] response) {
                            Log.d("PDF_RESPONSE", "Length: " + response.length);

                            savePdf(hostelName, response);
                            Toast.makeText(AdminReportActivity.this, "PDF saved", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdminReportActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(byteArrayRequest);
        }
        else if (hostelName.equals("Indrayani")) {
            ByteArrayRequest byteArrayRequest = new ByteArrayRequest(Request.Method.GET, DBClass.reportpdf,
                    new Response.Listener<byte[]>() {
                        @Override
                        public void onResponse(byte[] response) {
                            Log.d("PDF_RESPONSE", "Length: " + response.length);

                            savePdf(hostelName, response);
                            Toast.makeText(AdminReportActivity.this, "PDF saved", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdminReportActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(byteArrayRequest);
        }
        else if (hostelName.equals("Kaveri")) {
            ByteArrayRequest byteArrayRequest = new ByteArrayRequest(Request.Method.GET, DBClass.kaveripdf,
                    new Response.Listener<byte[]>() {
                        @Override
                        public void onResponse(byte[] response) {
                            Log.d("PDF_RESPONSE", "Length: " + response.length);

                            savePdf(hostelName, response);
                            Toast.makeText(AdminReportActivity.this, "PDF saved", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdminReportActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(byteArrayRequest);
        }
        else if (hostelName.equals("Krishna")) {
            ByteArrayRequest byteArrayRequest = new ByteArrayRequest(Request.Method.GET, DBClass.krishnapdf,
                    new Response.Listener<byte[]>() {
                        @Override
                        public void onResponse(byte[] response) {
                            Log.d("PDF_RESPONSE", "Length: " + response.length);

                            savePdf(hostelName, response);
                            Toast.makeText(AdminReportActivity.this, "PDF saved", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdminReportActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(byteArrayRequest);
        }
        else if (hostelName.equals("Godavari")) {
            ByteArrayRequest byteArrayRequest = new ByteArrayRequest(Request.Method.GET, DBClass.godavaripdf,
                    new Response.Listener<byte[]>() {
                        @Override
                        public void onResponse(byte[] response) {
                            Log.d("PDF_RESPONSE", "Length: " + response.length);

                            savePdf(hostelName, response);
                            Toast.makeText(AdminReportActivity.this, "PDF saved", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdminReportActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(byteArrayRequest);
        }
        else if (hostelName.equals("Yerala")) {
            ByteArrayRequest byteArrayRequest = new ByteArrayRequest(Request.Method.GET, DBClass.yeralapdf,
                    new Response.Listener<byte[]>() {
                        @Override
                        public void onResponse(byte[] response) {
                            Log.d("PDF_RESPONSE", "Length: " + response.length);

                            savePdf(hostelName, response);
                            Toast.makeText(AdminReportActivity.this, "PDF saved", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdminReportActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(byteArrayRequest);
        }
        else if (hostelName.equals("Varana")) {
            ByteArrayRequest byteArrayRequest = new ByteArrayRequest(Request.Method.GET, DBClass.varanapdf,
                    new Response.Listener<byte[]>() {
                        @Override
                        public void onResponse(byte[] response) {
                            Log.d("PDF_RESPONSE", "Length: " + response.length);

                            savePdf(hostelName, response);
                            Toast.makeText(AdminReportActivity.this, "PDF saved", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdminReportActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(byteArrayRequest);
        }
        else if (hostelName.equals("All")) {
            ByteArrayRequest byteArrayRequest = new ByteArrayRequest(Request.Method.GET, DBClass.reportpdfAll,
                    new Response.Listener<byte[]>() {
                        @Override
                        public void onResponse(byte[] response) {
                            Log.d("PDF_RESPONSE", "Length: " + response.length);

                            savePdf(hostelName, response);
                            Toast.makeText(AdminReportActivity.this, "PDF saved", Toast.LENGTH_SHORT).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(AdminReportActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            queue.add(byteArrayRequest);
        }
    }

    private void savePdf(String hostelName,byte[] pdfData) {
        String currentDate = getCurrentDate();
        String fileName = hostelName.toLowerCase() + "_" + currentDate + ".pdf";

        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if (!path.exists()) {
            path.mkdirs();
        }

        File file = new File(path, fileName);

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(pdfData);
            outputStream.close();
            Log.d("PDF_RESPONSE", "PDF saved at: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCurrentDate() {
        // You may need to adjust the date format based on your requirements
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Handle the PDF request after permission is granted
                handlePdfRequest();
            } else {
                Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
            }


        }
    }}
