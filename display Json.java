package com.myapp.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class display extends AppCompatActivity {

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        TableLayout t1 = (TableLayout) findViewById(R.id.table_layout);
        Button close = (Button)findViewById(R.id.cl);
        t1.setColumnStretchable(0, true);
        t1.setColumnStretchable(1, true);
        t1.setColumnStretchable(2, true);

            sql s = new sql(this);
            display d = new display();
            String Date = "", des = "";
            int Amt = 0;
            SQLiteDatabase db = s.getReadableDatabase();
            Intent in = getIntent();
            String sdate = in.getStringExtra("start");
            String edate = in.getStringExtra("end");
            System.out.println("Date:-" + sdate);
            System.out.println("Date:-" + edate);
            final String ip = "http://192.168.1.3:80/Display.php";
            JsonArrayRequest jrr = new JsonArrayRequest(Request.Method.POST, ip,null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    TableRow row = null;
                    for(int k = 0;k<response.length();k++) {
                        JSONObject j = null;
                        try {
                            j = response.getJSONObject(k);
                            String date = j.getString("Date");
                            String Des = j.getString("Des");
                            Integer Amt = j.getInt("Amount");
                            String col_name[] = {date, Des, Amt + ""};
                            for (String i : col_name) {
                                row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                        TableLayout.LayoutParams.WRAP_CONTENT));
                                TextView tv = new TextView(display.this);
                                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                        TableRow.LayoutParams.WRAP_CONTENT));
                                tv.setGravity(Gravity.CENTER);
                                tv.setText(i);
                                tv.setTextSize(15);
                                tv.setPadding(5, 5, 5, 5);
                                row.addView(tv);
                            }
                            t1.addView(row);
                        }
                        catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                    t1.addView(row);
                }
            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("str_date",sdate);
                    params.put("End_date",edate);
                    return params;
                }
            };




        close.setOnClickListener((v)-> {
            if(v.getId()==R.id.cl) {
                Intent intent = new Intent(getApplicationContext(), Database.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onBackPressed()
    {
        startActivity(new Intent(this,Database.class));
        finish();
    }
}
