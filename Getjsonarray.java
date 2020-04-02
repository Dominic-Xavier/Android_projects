package com.myapp.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class Getjsonarray extends AsyncTask<String,String,String> {

    Context context;

    public Getjsonarray(Context context){
        this.context = context;
    }

    String res;
    JSONArray jrr;
    @Override
    protected String doInBackground(String... strings) {
        String sdate,edate;
        sdate = strings[0];
        edate = strings[1];
        String User_id = strings[2];
        try{
        String u = "http://192.168.1.3/Display.php";
        URL url = new URL(u);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoInput(true);
        http.setDoOutput(true);

        OutputStream ops = http.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
        String data = URLEncoder.encode("sdate","UTF-8")+"="+URLEncoder.encode(sdate,"UTF-8")
                +"&&"+URLEncoder.encode("edate","UTF-8")+"="+URLEncoder.encode(edate,"UTF-8")
                +"&&"+URLEncoder.encode("User_id","UTF-8")+"="+URLEncoder.encode(User_id,"UTF-8");
        writer.write(data);
        writer.flush();
        writer.close();


            InputStream ips = http.getInputStream();
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(ips, "UTF-8"));
            StringBuffer responseStrBuilder = new StringBuffer();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            res = String.valueOf(responseStrBuilder.toString());

            jrr = new JSONArray(responseStrBuilder.toString());

            System.out.println("Json array:"+jrr);

        ips.close();
        ops.close();
        http.disconnect();
        return res;
    } catch (
    MalformedURLException e) {
            res = e.getMessage();
    } catch (IOException e) {
            res = e.getMessage();
    } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }
    @Override
    protected void onPostExecute(String s) {
            System.out.println("String JSon:"+s);
            Intent intent_name = new Intent();
            intent_name.putExtra("Jsondata",s);
            intent_name.setClass(context.getApplicationContext(),display.class);
            context.startActivity(intent_name);
        }
}
