package com.myapp.finance;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import androidx.annotation.RequiresApi;

public class sql extends SQLiteOpenHelper {

    Context context;
    static SharedPreferences preferences;

    public static void setData(String Key,String value,Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(Key,value);
        edit.commit();
    }

    public static String getData(String key,Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(key.equals("User_name"))
            return preferences.getString("User_name",null);
        else
            return preferences.getString("u_id",null);
    }

    public static void delete_data(Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor edit = preferences.edit();
        edit.remove("User_name");
        edit.remove("u_id");
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
        Boolean b = false;
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

    public String getMonthForInt(int num) {
        return new DateFormatSymbols().getMonths()[num-1];
    }


    public CheckBox iNcome(){
        CheckBox income = new CheckBox(context);
        income.setText("Income");
        income.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        return income;
    }


    public CheckBox eXpense(){
        CheckBox expense = new CheckBox(context);
        expense.setText("Expense");
        expense.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        return expense;
    }

    public String onCheckboxClicked(CheckBox Expense, CheckBox Income){

        CheckBox expense = Expense;
        CheckBox income = Income;

        String url="-",keyword="-";
        if(expense.isChecked() && income.isChecked()){
            url="http://192.168.1.9/Total_exp_inc.php";
            keyword = "Both";
            return keyword+";;"+url;
        }

        if(expense.isChecked()){
            url="http://192.168.1.9/Display.php";
            keyword = "expense";
            Toast.makeText(context,"eXpense is selected",Toast.LENGTH_SHORT).show();
            return keyword+";;"+url;
        }

        if(income.isChecked()){
            url="http://192.168.1.9/Display.php";
            keyword = "income";
            Toast.makeText(context,"iNcome is selected",Toast.LENGTH_SHORT).show();
            return keyword+";;"+url;
        }
        else{
            Toast.makeText(context,"Please Select an option",Toast.LENGTH_SHORT).show();
            return keyword+";;"+url;
        }
    }
}
