package com.myapp.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.DialogPreference;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class login extends AppCompatActivity {

    Button b1,b2,b3;
    TextView t1,t2;
    Boolean bool = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.login);
        b2 = (Button) findViewById(R.id.can);
        b3 = (Button) findViewById(R.id.register);
        t1 = (TextView) findViewById(R.id.user);
        t2 = (TextView) findViewById(R.id.pass);
        AlertDialog.Builder b = new AlertDialog.Builder(login.this);


        b1.setOnClickListener((v) -> {
            String user = t1.getText().toString();
            String pass = t2.getText().toString();
            if(user.equals("") || pass.equals("")) {
                new sql(this).show("Error","Username or password is empty","Ok");
            }
            else
            {
                Background bg = new Background(this);
                String url = "http://192.168.1.8/login.php";
                bg.execute(user, pass, url);
                bg.setUser_name(user);
            }
        });

        b2.setOnClickListener((v) -> {
            AlertDialog.Builder alert = new AlertDialog.Builder(login.this)
            .setMessage("Are you sure want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No",null);
            AlertDialog al = alert.create();
            al.show();
        }
        );

        b3.setOnClickListener((v)->{
            startActivity(new Intent(login.this,Register.class));
            finish();
        });
    }
    public void onBackPressed()
    {
        AlertDialog.Builder b = new AlertDialog.Builder(login.this);
        AlertDialog.Builder alert = new AlertDialog.Builder(login.this);
        alert.setMessage("Are you sure want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("No",null);
        AlertDialog al = alert.create();
        al.show();
    }
}
