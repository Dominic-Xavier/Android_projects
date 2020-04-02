package com.myapp.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import org.json.JSONObject;
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

public class Data extends AsyncTask<String,Void,String> {
    String result;
    Context context;
    public Data(Context context) throws IOException {
        this.context = context;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    String add;

    @Override
    protected String doInBackground(String... strings) {
        try{
            final String ip = "http://192.168.1.3:80/data.php";

            System.out.println("Datas:"+add);

        URL url = new URL(ip);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoInput(true);
        http.setDoOutput(true);
        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        http.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        http.connect();

        OutputStream ops = http.getOutputStream();
        BufferedWriter br = new BufferedWriter(new OutputStreamWriter(ops,"UTF-8"));
        br.write(getAdd());
        br.flush();

        InputStream ips = http.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
        String line ="";
        while ((line = reader.readLine()) != null)
        {
            result += line;
        }
            reader.close();
            ips.close();
            ops.close();
            br.close();
            http.disconnect();
        }

        catch (MalformedURLException e) {
            result = e.getMessage();
        }

        catch (IOException e) {
            result = e.getMessage();
        }

        catch (Exception e) {
            result = e.getMessage();
        }

        return result;
    }


    @Override
    protected void onPostExecute(String s){
        if(s.contains("values inserted")) {
            new sql(context).show("Success","Values inserted Successfully","Ok");
        }
        else{
            new sql(context).show("Sorry",s,"Ok");
        }
    }
}
