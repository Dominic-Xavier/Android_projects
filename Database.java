package com.myapp.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Display;
import android.view.Gravity;
import android.view.View;;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Database extends AppCompatActivity implements View.OnClickListener {

    List<EditText> list = new ArrayList<EditText>();
    List<EditText> list1 = new ArrayList<EditText>();
    List<String> desc = new ArrayList<String>();
    List<Integer> amts = new ArrayList<Integer>();
    TableLayout t1;
    TableLayout t2;
    Button add_row;
    Button del_row;
    AlertDialog al;
    JSONObject jobj;

    public String getUser() {
        return user;
    }

    static String user;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        Button logout = (Button) findViewById(R.id.log);
        Button insert = (Button) findViewById(R.id.ins);
        Button ret = (Button) findViewById(R.id.ret);
        add_row = (Button) findViewById(R.id.add);
        del_row = (Button)findViewById(R.id.delete);
        t1 = (TableLayout) findViewById(R.id.tbl);
        t2 = (TableLayout) findViewById(R.id.table_lay);
        TextView name = (TextView) findViewById(R.id.user_name);
        TextView text = (TextView) findViewById(R.id.date) ;

        Intent in = getIntent();
        String data = in.getStringExtra("User");
        user = in.getStringExtra("user_id");
        name.setText("User:"+data);

        sql s = new sql(this);
        String date = s.date().toString();
        text.setText("Date:" + date);


        logout.setOnClickListener((v) -> {
            AlertDialog.Builder bl = new AlertDialog.Builder(this);
            bl.setMessage("Are you sure want to logout")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                            startActivity(new Intent(Database.this, login.class));
                        }
                    })
                    .setNegativeButton("No", null);
            al = bl.create();
            al.show();
        });

        add_row.setOnClickListener(this);
        del_row.setOnClickListener(this );


        insert.setOnClickListener((v) -> {

            try {
            String des = "";
            int amt = 0;

            for (EditText text2 : list) {
            des = text2.getText().toString();
            desc.add(des);
            }

            for (EditText text1 : list1) {
                amt = Integer.parseInt(text1.getText().toString());
                amts.add(amt);
                }

                List json = new ArrayList();
                ListIterator l = desc.listIterator();
                ListIterator l2 = amts.listIterator();

                while(l.hasNext() && l2.hasNext()){
                    jobj = new JSONObject();
                    jobj.put("User_id",user);
                    jobj.put("Des",l.next());
                    jobj.put("Amount",l2.next());
                    json.add(jobj);
                }

                String add = json.toString();
                System.out.println("Datas of json"+json);

                if(isNetworkAvailable()) {
                    Data d = new Data(this);
                    d.setAdd(add);
                    d.execute();
                }
                else{
                    new sql(Database.this).show("Network Error","Please connect to my network","Ok");
                }
            }
            catch(Exception e){
             e.printStackTrace();
            }
        });

        ret.setOnClickListener((v) -> {
            try {
                startActivity(new Intent(Database.this, popup.class));
            } catch (Exception e) {
                AlertDialog.Builder bl = new AlertDialog.Builder(this)
                        .setTitle("Error Occured")
                        .setMessage(e.toString())
                        .setPositiveButton("Ok", null);
                al = bl.create();
                al.show();
            }
        });
    }

    public void onBackPressed() {
        AlertDialog.Builder bl = new AlertDialog.Builder(this)
                .setMessage("Are you sure want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(new Intent(Database.this, login.class));
                    }
                })
                .setNegativeButton("No", null);
        al = bl.create();
        al.show();
    }

    public EditText des() {
        EditText et1 = new EditText(Database.this);
        et1.setTextSize(15);
        et1.setGravity(Gravity.CENTER);
        list.add(et1);
        return et1;
    }

    public EditText amt() {
        EditText et2 = new EditText(Database.this);
        et2.setTextSize(15);
        et2.setGravity(Gravity.CENTER);
        et2.setInputType(InputType.TYPE_CLASS_NUMBER);
        list1.add(et2);
        return et2;
    }

    @Override
    public void onClick(View v) {
        TableRow row  = new TableRow(this);
        switch(v.getId()){
            case R.id.add:{
                row.addView(des());
                row.addView(amt());
                t2.addView(row);
                break;
            }
            case R.id.delete:{
                row.removeView(des());
                row.removeView(amt());
                t2.removeAllViews();
                list.removeAll(list);
                list1.removeAll(list1);
                desc.removeAll(desc);
                amts.removeAll(amts);
                jobj.remove("Des");
                jobj.remove("Amount");
                break;
            }
        }
    }

    private Response.Listener<JSONObject> createRequestSuccessListener(){
        return new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject response) {
                new sql(Database.this).show("Success",response.toString(),"Ok");
            }
        };
    }

    private Response.ErrorListener createRequestErrorListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new sql(Database.this).show("Error",error.toString(),"ok");
            }
        };
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo!=null && activeNetworkInfo.isConnectedOrConnecting())
            return true;
        else
            return false;
    }

    private boolean checkWifiOnAndConnected() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (wifiMgr.isWifiEnabled()) { // Wi-Fi adapter is ON

            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();

            if( wifiInfo.getNetworkId() == -1 ){
                return false; // Not connected to an access point
            }
            return true; // Connected to an access point
        }
        else {
            return false; // Wi-Fi adapter is OFF
        }
    }
}
