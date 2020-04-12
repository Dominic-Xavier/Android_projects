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
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.view.Display;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
            if (date.equals(df.format(d)))
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


    public android.support.v7.app.AlertDialog edit_texts(){
        String user = getData("u_id",context);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        String st_date = start_date().getText().toString();
        String e_date = end_date().getText().toString();
        layout.addView(s_Date());
        layout.addView(start_date());
        layout.addView(e_Date());
        layout.addView(end_date());
        android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(context);
        alert.setTitle("Enter the Date")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("Star Date:"+st_date);
                        System.out.println("Star Date:"+e_date);
                        new Getjsonarray(context).execute(st_date,e_date,user);
                        ((Activity)context).finish();
                    }
                })
                .setNegativeButton("Cancel", null);
        android.support.v7.app.AlertDialog al = alert.create();
        al.setView(layout);
        al.show();
        return al;
    }

    public EditText start_date(){
        EditText st = new EditText(context);
        st.setGravity(Gravity.CENTER);
        st.setTextSize(20);
        st.setInputType(InputType.TYPE_CLASS_DATETIME);
        st.setHint("DD-MM-YYYY");
        return st;
    }

    public EditText end_date(){
        EditText ed = new EditText(context);
        ed.setGravity(Gravity.CENTER);
        ed.setTextSize(20);
        ed.setInputType(InputType.TYPE_CLASS_DATETIME);
        ed.setHint("DD-MM-YYYY");
        return ed;
    }

    public TextView s_Date(){
        TextView t = new TextView(context);
        t.setText("Start Date");
        t.setTextSize(20);
        t.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        return t;
    }

    public TextView e_Date(){
        TextView t1 = new TextView(context);
        t1.setText("End Date");
        t1.setTextSize(20);
        t1.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        return t1;
    }
}
