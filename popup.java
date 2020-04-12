package com.myapp.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

public class popup extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int wid = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(wid*.8),(int)(height*.7));

    }
    public void save(View v)
    {
        String user = sql.getData("u_id",this);
        Boolean b = null;
        EditText et = (EditText)findViewById(R.id.start);
        EditText et1 = (EditText)findViewById(R.id.end);
        try {
            String sdate = et.getText().toString();
            String edate = et1.getText().toString();
            sql s = new sql(this);
            if(sdate.equals("") || sdate.equals(null)|| edate.equals("") || edate.equals(null)){
                new sql(this).show("Error","Starting date or Ending is blank","Ok");
            }
            if(s.validformat(sdate) && s.validformat(edate) && sdate!=null && edate!=null)
                b=true;
            else
                b=false;
            if(b==true) {
                if (v.getId() == R.id.save) {
                    new Getjsonarray(this).execute(sdate,edate,user);
                    finish();
                } else {
                    new sql(this).show("Incorrect Date format","Please enter date format in DD-MM-YYYY","Ok");
                }
            }

        }
        catch (NullPointerException n){
            new sql(this).show("Sorry","Please Enter some data","Ok");
        }
        catch (Exception e) {
           new sql(this).show("Error",e.getMessage(),"OK");
        }
    }

    public void onBackPressed(){
        startActivity(new Intent(popup.this,Database.class));
        finish();
    }
}