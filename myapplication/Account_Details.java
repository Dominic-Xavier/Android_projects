package com.myapp.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@RequiresApi(api = Build.VERSION_CODES.P)
public class Account_Details extends AppCompatActivity {

    Button quit;
    TextView name,id,mobile_number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_details);
        String u_id="",Name="",mob_num="";

        Intent in = getIntent();
        String data = in.getStringExtra("Jsondata");

        name = findViewById(R.id.Name);
        id = findViewById(R.id.U_id);
        mobile_number = findViewById(R.id.Mobile_number);
        quit = findViewById(R.id.quit);

        try {
            JSONArray jrr = new JSONArray(data);
            for(int i=0;i<jrr.length();i++){
                JSONObject arr = jrr.getJSONObject(i);
                u_id = arr.getString("id");
                Name = arr.getString("Username");
                mob_num = arr.getString("Mobile_Number");
                String user = sql.getData("User_name",this);
                id.setText(u_id);
                name.setText(Name);
                mobile_number.setText(mob_num);
            }
        } catch (JSONException e) {
            new sql(this).show("Error",e.toString(),"Ok");
        }

        quit.setOnClickListener((v)->{
            finish();
           startActivity(new Intent(Account_Details.this,Database.class));
        });
    }

    public void onBackPressed(){
        startActivity(new Intent(this,Database.class));
        finish();
    }
}
