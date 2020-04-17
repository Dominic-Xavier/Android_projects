package com.myapp.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.ActionMode;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
    Intent i;
    ImageView profile;
    private static final int IMAGE_CODE = 1;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        Button insert =  findViewById(R.id.ins);
        Button ret =  findViewById(R.id.ret);
        add_row =  findViewById(R.id.add);
        del_row = findViewById(R.id.delete);
        t1 =  findViewById(R.id.tbl);
        t2 =  findViewById(R.id.table_lay);
        TextView name =  findViewById(R.id.user_name);
        TextView text =  findViewById(R.id.date) ;
        //profile = findViewById(R.id.profile_pic);
        EditText str = new EditText(this);
        EditText etr = new EditText(this);
        str.setInputType(InputType.TYPE_CLASS_DATETIME);
        etr.setInputType(InputType.TYPE_CLASS_DATETIME);
        str.setGravity(Gravity.CENTER);
        etr.setGravity(Gravity.CENTER);
        str.setHint("DD/MM/YYYY");
        etr.setHint("DD/MM/YYYY");
        str.setHintTextColor(Color.BLACK);
        etr.setHintTextColor(Color.BLACK);


        final String datas = sql.getData("User_name",this);
        final String u_id = sql.getData("u_id",this);
        name.setText("User:"+datas);

        //Used for redirecting user to login page
        Handler handler=new Handler();
        handler.postAtFrontOfQueue(new Runnable() {
            @Override
            public void run() {
                if(u_id == null || u_id == "") {
                    finish();
                    startActivity(new Intent(Database.this, login.class));

                }
            }
        });


        sql s = new sql(this);
        String date = s.date();
        text.setText("Date:" + date);

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
                    jobj.put("User_id",u_id);
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
                String user = sql.getData("u_id",this);
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(s.S_date());
                layout.addView(str);
                layout.addView(s.E_date());
                layout.addView(etr);
                android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(this);
                alert.setTitle("Enter the Date")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String st_date = str.getText().toString();
                                String e_date = etr.getText().toString();
                                if(s.validformat(st_date) && s.validformat(e_date)){
                                    new Getjsonarray(Database.this).execute(st_date, e_date, user);
                                    finish();
                                }
                                else {
                                    s.show("Error","Invalid format","Ok");
                                    str.setText("");
                                    etr.setText("");
                                }
                                layout.removeAllViews();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                layout.removeAllViews();
                                str.setText("");
                                etr.setText("");
                            }
                        });
                android.support.v7.app.AlertDialog al = alert.create();
                al.setView(layout);
                al.setCancelable(false);
                al.setCanceledOnTouchOutside(false);
                al.show();
                /*startActivity(new Intent(Database.this, popup.class));
                finish();*/
            } catch (Exception e) {
                new sql(this).show("Error",e.toString(),"Ok");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_pic,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                logout();
                break;
            case R.id.choose_pic: {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, IMAGE_CODE);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            profile.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            // String picturePath contains the path of selected Image
        }
    }*/

    public AlertDialog logout(){
        AlertDialog.Builder bl = new AlertDialog.Builder(this)
                .setMessage("Are you sure want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sql.delete_data(getApplicationContext());
                        finish();
                        startActivity(new Intent(Database.this, login.class));
                    }
                })
                .setNegativeButton("No", null);
        al = bl.create();
        al.show();
        return al;
    }

    public void onBackPressed() {
        logout();
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
        switch (v.getId()) {
            case R.id.add: {
                row.addView(des());
                row.addView(amt());
                t2.addView(row);
                break;
            }
            case R.id.delete: {
                row.removeView(des());
                row.removeView(amt());
                t2.removeAllViews();
                list.removeAll(list);
                list1.removeAll(list1);
                desc.removeAll(desc);
                amts.removeAll(amts);
                if(jobj!=null){
                    jobj.remove("Des");
                    jobj.remove("Amount");
                }
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
