package com.myapp.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import org.json.JSONArray;
import org.json.JSONObject;

public class display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Intent in = getIntent();
        String data = in.getStringExtra("Jsondata");

        TableLayout t1 = findViewById(R.id.tbl);
        Button close = findViewById(R.id.cl);
        t1.setColumnStretchable(0, true);
        t1.setColumnStretchable(1, true);
        t1.setColumnStretchable(2, true);

        try {
            JSONArray getResponse = new JSONArray(data);
            for (int k = 0; k < getResponse.length(); k++) {
                TableRow row = new TableRow(display.this);
                JSONObject jobj = getResponse.getJSONObject(k);
                String date = jobj.getString("Date");
                String Des = jobj.getString("Description");
                Integer Amot = jobj.getInt("Amount");
                String col_name[] = {date, Des, Amot + ""};
                for (String i : col_name) {
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    TextView tv = new TextView(this);
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
        } catch (Exception e) {
            new sql(getApplicationContext()).show("Error", e.toString(), "OK");
        }



        /*Volley.newRequestQueue(this).add(new JsonArrayRequest(Request.Method.POST, ip, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int k = 0; k < response.length(); k++) {
                        TableRow row = new TableRow(display.this);
                        JSONObject jobj = response.getJSONObject(k);
                        String date = jobj.getString("Date");
                        String Des = jobj.getString("Description");
                        Integer Amot = jobj.getInt("Amount");
                        String col_name[] = {date, Des, Amot + ""};
                        for (String i : col_name) {
                            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                    TableLayout.LayoutParams.WRAP_CONTENT));
                            TextView tv = new TextView(getApplicationContext());
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
                } catch (Exception e) {
                    new sql(getApplicationContext()).show("Error", e.toString(), "OK");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new sql(getApplicationContext()).show("Error", error.toString(), "OK");
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("sdate","01-11-2019");
                params.put("edate","30-11-2019");
                return params;
            }

            @Override
            protected Response<JSONArray> parseNetworkResponse(
                    NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser
                                    .parseCharset(response.headers));
                    return Response.success(new JSONArray(jsonString),
                            HttpHeaderParser
                                    .parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        });*/

        /*final String ip = "http://192.168.1.8/Display.php";
        Map<String,String> params = new HashMap<>();
        params.put("sdate","01-11-2019");
        params.put("edate","30-11-2019");
        CustomRequest req = new CustomRequest(Request.Method.GET,ip,params,this.createRequestSuccessListener(),this.createRequestErrorListener());
        System.out.println("Datas are:"+req);
        MySingleTon.getInstance(getApplicationContext()).addToRequestQue(req);*/


        close.setOnClickListener((v) -> {
            if (v.getId() == R.id.cl) {
                startActivity(new Intent(display.this,Database.class));
                finish();
            }
        });
    }

    public void onBackPressed() {
        startActivity(new Intent(this, Database.class));
        finish();
    }

    private Response.Listener<JSONArray> createRequestSuccessListener(){
        return new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                TableLayout t1 = (TableLayout) findViewById(R.id.tbl);
                Button close = (Button) findViewById(R.id.cl);
                    try {
                        for (int k = 0; k < response.length(); k++) {
                            TableRow row = new TableRow(display.this);
                            JSONObject jobj = response.getJSONObject(k);
                            String date = jobj.getString("Date");
                            String Des = jobj.getString("Description");
                            Integer Amot = jobj.getInt("Amount");
                            String col_name[] = {date, Des, Amot + ""};
                            for (String i : col_name) {
                                row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                                        TableLayout.LayoutParams.WRAP_CONTENT));
                                TextView tv = new TextView(getApplicationContext());
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
                    } catch (Exception e) {
                        new sql(getApplicationContext()).show("Error", e.toString(), "OK");
                    }

            }
        };
    }

    private Response.ErrorListener createRequestErrorListener(){
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                new sql(getApplicationContext()).show("Error",error.toString(),"ok");
            }
        };
    }
}
