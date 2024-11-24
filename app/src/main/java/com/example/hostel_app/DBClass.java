package com.example.hostel_app;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBClass extends SQLiteOpenHelper
{

    public static String dbname = "cpp";
    public static String url = "http:/192.168.222.236/cpp_project/";
    public static String wardenLogin = url + "warden_login.php";

    public static String sms=url+"sms.php";
    public static String urlUpdateOutpass = url + "update_outpass.php";
    public static String urlUpdateStudent = url + "update_student.php";
    public static String urlUpdateWarden = url + "update_warden.php";
    public static String urlUpdateOfficer = url + "update_officer.php";

    public static String urlStudentDelete = url + "delete_student.php";
    public static String urlWardenDelete = url + "delete_warden.php";
    public static String urlOfficerDelete = url + "delete_officer.php";
    public static String urlOutpassDelete = url + "delete_outpass.php";
    public static String reportpdf = url + "pdf.php";
    public static String reportpdfAll = url + "allhostel.php";
    public static String chandrapdf = url + "chandrabhaga.php";
    public static String krishnapdf = url + "krishna.php";
    public static String kaveripdf = url + "kaveri.php";
    public static String yeralapdf = url + "yerala.php";
    public static String varanapdf = url + "varana.php";
    public static String godavaripdf = url + "godavari.php";


    public static String absentsud = url + "absent_student.php";
    public static String presentsud = url + "present_student.php";
    public static String officerLogin = url + "officer_login.php";
    public static String adminLogin = url + "admin_login.php";
    public static String urlAddWarden = url + "add_warden.php";
    public static String urlOutpasses = url + "outpasses.php";
    public static String urlAddOutpass = url + "add_outpass.php";

    public static String urlAddOfficer= url + "add_officer.php";
    public static String urlAddStudent= url + "add_student.php";
    public static String urlWardens = url + "wardens.php";
    public static String urlOfficers = url + "officers.php";
    public static String urlStudents= url + "students.php";

    public static SQLiteDatabase database;


    public DBClass(Context context){
            super(context, DBClass.dbname, null, 1);
    }

    public void onCreate(SQLiteDatabase arg) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    public static void execNonQuery(String query){
        //Execute Insert, Update, Delete, Create table queries
        //Log.e("Query", query);
        database.execSQL(query);
    }

    public static Cursor getCursorData(String query){
        //Log.d("SQuery", query);
        Cursor res =  database.rawQuery(query, null);
        return res;
    }

    public static String getSingleValue(String query) {
        try {
            Cursor res = getCursorData(query);
            String value = "";
            if (res.moveToNext()) {
                return res.getString(0);
            }
            return value;
        }
        catch (Exception ex)
        {
            return "";
        }
    }

    public static int getNoOfRows(String query){
        try {
            Cursor res = database.rawQuery(query, null);
            return res.getCount();
        }catch (Exception ex)
        {
            return 0;
        }
    }

    public static boolean checkIfRecordExist(String query){
        //Log.e("CheckQuery", query);
        Cursor res =  database.rawQuery(query, null );
        if(res.getCount() > 0)
            return true;
        else
            return false;
    }


    public static boolean doesTableExists(String tableName)
    {
        try{
            Cursor cursor = getCursorData("SELECT * FROM " + tableName);
            return true;
        }
        catch (Exception ex)
        {
            return  false;
        }
    }

    public static boolean doesFieldExist(String tableName, String fieldName)
    {
        try {
            String query = "SELECT " + fieldName + " FROM " + tableName;
            Cursor cursor = getCursorData(query);
            return  true;
        }
        catch (Exception ex)
        {
            return  false;
        }
    }


}
