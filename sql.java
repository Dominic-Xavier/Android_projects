package com.myapp.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class sql extends SQLiteOpenHelper {

    Context context;

    public static void setData(String Key,String value,Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(Key,value);
        edit.commit();
    }

    public static String getData(String key,Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(key.equals("User_name"))
            return preferences.getString("User_name",null);
        else
            return preferences.getString("u_id",null);
    }

    public static void delete_data(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear();
        edit.commit();
    }

    public sql(Context context) {
        super(context, "database.db", null, 1);
        this.context = context;
    }

    AlertDialog dialog;

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table log (username text primary key,Password text NOT NULL)");
        db.execSQL("create table data (Slno INTEGER primary key autoincrement,Date date NOT NULL,Description text NOT NULL,Amount INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists log");
        db.execSQL("drop table if exists data");
        onCreate(db);
    }

    public Boolean insert(String date, List srr, List amt) {
        Boolean b=false;
        SQLiteDatabase s = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        ListIterator l1 = srr.listIterator();
        ListIterator l2 = amt.listIterator();
        long row = 0;
        while(l1.hasNext() && l2.hasNext()){
            cv.put("Date", date);
            cv.put("Description", String.valueOf(l1.next()));
            cv.put("Amount", String.valueOf(l2.next()));

            row = s.insert("data", null, cv);

        }
        if (row == -1)
            return false;
        else
            return true;
    }

    public String date(){
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        return date;
    }

    public Boolean validformat(String date) {
        Boolean b = null;
        try {
            Date d = null;
            final String Dateformat = "dd-mm-yyyy";
            DateFormat df = new SimpleDateFormat(Dateformat);
            df.setLenient(false);
            d = df.parse(date);
            String split[] = date.split("-");
            int dte = Integer.parseInt(split[0]);
            int month = Integer.parseInt(split[1]);
            int year = Integer.parseInt(split[2]);
            if (date.equals(df.format(d)) && dte<=31 && month<=12 && year<9999)
                b = true;
            else
                b = false;
        } catch (ParseException e) {
            b = false;
            e.printStackTrace();
        }
        return b;
    }

    public AlertDialog show(String title,String Message,String buttonname){
        AlertDialog.Builder b = new AlertDialog.Builder(context).setTitle(title).setMessage(Message).setPositiveButton(buttonname,null);
        dialog = b.create();
        dialog.show();
        return dialog;
    }

    public TextView S_date(){
        TextView t = new TextView(context);
        t.setText("Start Date");
        t.setTextSize(20);
        t.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        return t;
    }
    public TextView E_date(){
        TextView t = new TextView(context);
        t.setText("End Date");
        t.setTextSize(20);
        t.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        return t;
    }

}
