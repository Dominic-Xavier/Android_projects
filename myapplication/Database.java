package com.myapp.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Database extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

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
    ActionBarDrawerToggle action;
    DrawerLayout drawerLayout;
    Toolbar mTool;
    String u_id,option_selected;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        mTool = findViewById(R.id.m_tool);
        setSupportActionBar(mTool);

        drawerLayout = findViewById(R.id.drawer_layout);
        action = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(action);
        action.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        NavigationView mNavigationView = findViewById(R.id.navigation_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Select,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);


        Button insert = findViewById(R.id.ins);
        Button ret = findViewById(R.id.ret);
        add_row = findViewById(R.id.add);
        del_row = findViewById(R.id.delete);
        t1 = findViewById(R.id.tbl);
        t2 = findViewById(R.id.table_lay);
        TextView name = findViewById(R.id.user_name);
        TextView text = findViewById(R.id.date);
        EditText str = new EditText(this);
        EditText etr = new EditText(this);
        str.setInputType(InputType.TYPE_CLASS_DATETIME);
        etr.setInputType(InputType.TYPE_CLASS_DATETIME);
        str.setGravity(Gravity.CENTER);
        etr.setGravity(Gravity.CENTER);
        str.setHint("DD-MM-YYYY");
        etr.setHint("DD-MM-YYYY");
        str.setHintTextColor(Color.GRAY);
        etr.setHintTextColor(Color.GRAY);


        final String datas = sql.getData("User_name", this);
        u_id = sql.getData("u_id", this);
        name.setText("User:" + datas);

        //Used for redirecting user to login page
        Handler handler = new Handler();
        handler.postAtFrontOfQueue(new Runnable() {
            @Override
            public void run() {
                if (u_id == null || u_id == "") {
                    finish();
                    startActivity(new Intent(Database.this, login.class));
                }
            }
        });


        sql s = new sql(this);
        String date = s.date();
        text.setText("Date:" + date);

        add_row.setOnClickListener(this);
        del_row.setOnClickListener(this);


        insert.setOnClickListener((v) -> {

            try {
                if(option_selected.equals("Select One")){
                    new sql(this).show("Error","Please select one option","ok");
                }
                else{
                    String keyword;
                    if(option_selected.equals("Expense")){
                        keyword = option_selected;
                    }
                    else {
                        keyword = "Income";
                    }

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

                    while (l.hasNext() && l2.hasNext()) {
                        jobj = new JSONObject();
                        jobj.put("User_id", u_id);
                        jobj.put("option",keyword);
                        jobj.put("Des", l.next());
                        jobj.put("Amount", l2.next());
                        json.add(jobj);
                    }

                    String add = json.toString();
                    System.out.println("Datas of json" + json);

                    if (isNetworkAvailable()) {
                        Data d = new Data(this);
                        d.setAdd(add);
                        d.execute(keyword);
                    } else {
                        new sql(Database.this).show("Network Error", "Please connect to my network", "Ok");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ret.setOnClickListener((v) -> {
            try {
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
                                if (s.validformat(st_date) && s.validformat(e_date)) {
                                    String url = "http://192.168.1.9/Display.php";
                                    new Getjsonarray(this).execute(url,u_id,st_date, e_date);
                                    finish();
                                } else {
                                    s.show("Error", "Invalid format", "Ok");
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
                al.setCancelable(false);//from touching back button
                al.setCanceledOnTouchOutside(false);//from touching apart from Alert
                al.show();
            } catch (Exception e) {
                new sql(this).show("Error", e.toString(), "Ok");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        if (action.onOptionsItemSelected(item))
            return true;
        return super.onOptionsItemSelected(item);
    }

    public AlertDialog logout() {
        AlertDialog.Builder bl = new AlertDialog.Builder(this)
                .setTitle("Logout")
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
        et1.setMaxWidth(et1.getWidth());
        list.add(et1);
        return et1;
    }

    public EditText amt() {
        EditText et2 = new EditText(Database.this);
        et2.setTextSize(15);
        et2.setGravity(Gravity.CENTER);
        et2.setInputType(InputType.TYPE_CLASS_NUMBER);
        et2.setMaxWidth(et2.getWidth());
        list1.add(et2);
        return et2;
    }

    @Override
    public void onClick(View v) {
        TableRow row = new TableRow(this);
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
                if (jobj != null) {
                    jobj.remove("Des");
                    jobj.remove("Amount");
                }
                break;
            }
        }
    }

    private Response.Listener<JSONObject> createRequestSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                new sql(Database.this).show("Success", response.toString(), "Ok");
            }
        };
    }

    private Response.ErrorListener createRequestErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new sql(Database.this).show("Error", error.toString(), "ok");
            }
        };
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting())
            return true;
        else
            return false;
    }

    private boolean checkWifiOnAndConnected() {
        WifiManager wifiMgr = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if (wifiMgr.isWifiEnabled()) { // Wi-Fi adapter is ON

            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();

            if (wifiInfo.getNetworkId() == -1) {
                return false; // Not connected to an access point
            }
            return true; // Connected to an access point
        } else {
            return false; // Wi-Fi adapter is OFF
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        TableRow row = new TableRow(this);
        int id = menuItem.getItemId();
        switch (id) {
            case R.id.logout:
                logout();
                break;
            case R.id.my_account: {
                String url = "http://192.168.1.9/User_details.php";
                new Getjsonarray(this).execute(url,u_id);
                finish();
                break;
            }
            case R.id.expense:
                new sql(this).show("Success", "You have selected Expense", "ok");
                break;
        }
        return true;
    }

    /**
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     * <p>
     * Implementers can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent   The AdapterView where the selection happened
     * @param view     The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id       The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        option_selected = parent.getItemAtPosition(position).toString();
    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        new sql(this).show("Error","Please select one option","Ok");
    }
}
